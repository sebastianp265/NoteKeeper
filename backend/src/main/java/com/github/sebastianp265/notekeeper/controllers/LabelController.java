package com.github.sebastianp265.notekeeper.controllers;

import com.github.sebastianp265.notekeeper.dtos.LabelGetDTO;
import com.github.sebastianp265.notekeeper.dtos.LabelPostDTO;
import com.github.sebastianp265.notekeeper.dtos.LabelPutDTO;
import com.github.sebastianp265.notekeeper.services.LabelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@ControllerAdvice
@Tag(name = "Labels", description = "Labels management APIs")
@SecurityRequirement(name = "bearer-key")
@RequestMapping("labels")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    @Operation(summary = "Find all Labels", description = "Get all Label objects.", tags = {"GET"})
    @GetMapping
    public List<LabelGetDTO> findAll() {
        log.debug("Finding all labels");
        return labelService.findAll();
    }

    @Operation(summary = "Find Label", description = "Get Label object by providing it's id.", tags = {"GET"})
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", description = "Given id doesn't match any existing label id", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    public LabelGetDTO findById(@PathVariable Long id) {
        log.debug("Finding label by name = " + id);
        return labelService.findById(id);
    }


    @Operation(summary = "Create Label", description = "Create Label object by providing it's body", tags = {"POST"})
    @PostMapping
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "400", description = "Given label body has provided id", content = {@Content(schema = @Schema())})
    @ResponseStatus(HttpStatus.CREATED)
    public LabelGetDTO create(@RequestBody LabelPostDTO labelPostDTO) {
        log.debug("Creating label with body = " + labelPostDTO);
        return labelService.create(labelPostDTO);
    }


    @Operation(summary = "Update Label", description = "Update Label by providing it's id and body", tags = {"PUT"})
    @PutMapping("/{id}")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", description = "Provided id from mapping doesn't match label id", content = {@Content(schema = @Schema())})
    public LabelGetDTO update(@PathVariable Long id, @RequestBody LabelPutDTO labelPutDTO) {
        log.debug("Updating label with id = {} and body = {}", id, labelPutDTO);
        return labelService.update(id, labelPutDTO);
    }

    @Operation(summary = "Delete Label", description = "Delete label by providing it's id", tags = {"DELETE"})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.debug("Updating label with name = {}", id);
        labelService.delete(id);
    }
}
