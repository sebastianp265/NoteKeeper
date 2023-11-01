package com.github.sebastianp265.notekeeper.controllers;

import com.github.sebastianp265.notekeeper.entities.Label;
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
    public Collection<Label> findAll() {
        log.debug("Finding all labels");
        return labelService.findAll();
    }


    @Operation(summary = "Find Label", description = "Get Label object by providing it's id.", tags = {"GET"})
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", description = "Given name doesn't match any existing label name", content = {@Content(schema = @Schema())})
    @GetMapping("/{name}")
    public Label findById(@PathVariable String name) {
        log.debug("Finding label by name = " + name);
        return labelService.findById(name);
    }


    @Operation(summary = "Create Label", description = "Create Label object by providing it's body", tags = {"POST"})
    @PostMapping
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "400", description = "Given label body didn't provide label name", content = {@Content(schema = @Schema())})
    @ResponseStatus(HttpStatus.CREATED)
    public Label create(@RequestBody Label label) {
        log.debug("Creating label with body = " + label);
        return labelService.create(label);
    }


    @Operation(summary = "Update Label", description = "Update Label by providing it's name and body", tags = {"PUT"})
    @PutMapping("/{name}")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", description = "Provided name from mapping doesn't match label name", content = {@Content(schema = @Schema())})
    public Label update(@PathVariable String name, @RequestBody Label label) {
        log.debug("Updating label with id = {}\nand body = {}", name, label);
        return labelService.update(name, label);
    }

    @Operation(summary = "Delete Label", description = "Delete label by providing it's name", tags = {"DELETE"})
    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        log.debug("Updating label with name = {}", name);
        labelService.deleteById(name);
    }
}
