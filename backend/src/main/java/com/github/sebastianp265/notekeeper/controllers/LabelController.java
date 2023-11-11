package com.github.sebastianp265.notekeeper.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.sebastianp265.notekeeper.dto.LabelDto;
import com.github.sebastianp265.notekeeper.dto.Views;
import com.github.sebastianp265.notekeeper.services.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@Tag(name = "Labels", description = "Labels management APIs")
@RequestMapping("labels")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    @Operation(summary = "Find all Label", description = "Get all Label objects.", tags = {"GET"})
    @GetMapping
    @JsonView({Views.Get.class})
    public Collection<LabelDto> findAll() {
        log.debug("Finding all labels");
        return labelService.findAll();
    }


    @Operation(summary = "Find Label", description = "Get Label object by providing it's id.", tags = {"GET"})
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", description = "Given name doesn't match any existing label name", content = {@Content(schema = @Schema())})
    @GetMapping("/{labelName}")
    @JsonView({Views.Get.class})
    public LabelDto findByLabelName(@PathVariable String labelName) {
        log.debug("Finding label by name = " + labelName);
        return labelService.findByLabelName(labelName);
    }


    @Operation(summary = "Create Label", description = "Create Label object by providing it's body", tags = {"POST"})
    @PostMapping
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "400", description = "Given label body didn't provide label name", content = {@Content(schema = @Schema())})
    @ResponseStatus(HttpStatus.CREATED)
    @JsonView({Views.Get.class})
    public LabelDto create(@RequestBody @JsonView({Views.Post.class}) LabelDto labelDto) {
        log.debug("Creating label with body = " + labelDto);
        return labelService.create(labelDto);
    }


    @Operation(summary = "Update Label", description = "Update Label by providing it's name and body", tags = {"PUT"})
    @PutMapping("/{name}")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", description = "Provided name from mapping doesn't match label name", content = {@Content(schema = @Schema())})
    @JsonView({Views.Get.class})
    public LabelDto update(@PathVariable String name, @RequestBody @JsonView({Views.Put.class}) LabelDto labelDto) {
        log.debug("Updating label with id = {}\nand body = {}", name, labelDto);
        return labelService.update(name, labelDto);
    }

    @Operation(summary = "Delete Label", description = "Delete label by providing it's name", tags = {"DELETE"})
    @DeleteMapping("/{labelName}")
    public void delete(@PathVariable String labelName) {
        log.debug("Updating label with name = {}", labelName);
        labelService.delete(labelName);
    }
}
