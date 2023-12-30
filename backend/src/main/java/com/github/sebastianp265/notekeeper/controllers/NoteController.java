package com.github.sebastianp265.notekeeper.controllers;

import com.github.sebastianp265.notekeeper.dtos.NoteGetDTO;
import com.github.sebastianp265.notekeeper.dtos.NotePostDTO;
import com.github.sebastianp265.notekeeper.dtos.NotePutDTO;
import com.github.sebastianp265.notekeeper.services.NoteService;
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
@Tag(name = "Notes", description = "Notes management APIs")
@SecurityRequirement(name = "bearer-key")
@RequestMapping(value = "notes")
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @Operation(summary = "Find all Notes", description = "Get all Note objects.", tags = {"GET"})
    @GetMapping
    public List<NoteGetDTO> findAll() {
        log.debug("Finding all notes");
        return noteService.findAll();
    }

    @Operation(summary = "Find all Notes by Label", description = "Get all Note objects by providing Label name.", tags = {"GET"})
    @GetMapping("/by-label-name/{labelName}")
    public List<NoteGetDTO> findAllByLabel(@PathVariable String labelName) {
        log.debug("Finding all notes by label name = {}", labelName);
        return noteService.findAllByLabel(labelName);
    }

    @Operation(summary = "Find Note by id", description = "Get a Note object by providing it's id.", tags = {"GET"})
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", description = "Given id doesn't match any existing Note id", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    public NoteGetDTO findById(@PathVariable Long id) {
        log.debug("Finding note by id = {}", id);
        return noteService.findById(id);
    }


    @PostMapping
    @Operation(summary = "Create Note", description = "Create Note object by providing Note body", tags = {"POST"})
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "400", description = "Given note body has provided id", content = {@Content(schema = @Schema())})
    @ResponseStatus(HttpStatus.CREATED)
    public NoteGetDTO create(@RequestBody NotePostDTO notePostDTO) {
        log.debug("Creating note with body = {}", notePostDTO);
        return noteService.create(notePostDTO);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update Note", description = "Update note object by providing it's id and Note body", tags = {"PUT"})
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", description = "Provided id from mapping doesn't match note id", content = {@Content(schema = @Schema())})
    @ApiResponse(responseCode = "404", description = "Note with given id not found", content = {@Content(schema = @Schema())})
    public NoteGetDTO update(@PathVariable Long id, @RequestBody NotePutDTO notePutDTO) {
        log.debug("Updating note with id = {} and body = {}", id, notePutDTO);
        return noteService.update(id, notePutDTO);
    }


    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", description = "Note with provided id doesn't exist")
    @Operation(summary = "Delete Note", description = "Delete Note object by providing it's id", tags = {"DELETE"})
    public void delete(@PathVariable Long id) {
        log.debug("Updating note with id = {}", id);
        noteService.deleteById(id);
    }

    @PutMapping("/{noteId}/add-label/{labelId}")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", description = "Label with provided id or label with provided name doesn't exist in DB", content = {@Content(schema = @Schema)})
    @Operation(summary = "Attach Label to Note", description = "Attach Label to Note by providing Note id and Label name")
    public NoteGetDTO addLabel(@PathVariable Long noteId, @PathVariable Long labelId) {
        log.debug("Attaching label to note");
        return noteService.addLabel(noteId, labelId);
    }

    @PutMapping("/{noteId}/remove-label/{labelId}")
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", description = "Label with provided id or label with provided name doesn't exist in DB", content = {@Content(schema = @Schema)})
    @Operation(summary = "Detach Label from Note", description = "Detach Label from Note by providing Note id and Label name")
    public NoteGetDTO removeLabel(@PathVariable Long noteId, @PathVariable Long labelId) {
        log.debug("Detaching label from note");
        return noteService.removeLabel(noteId, labelId);
    }
}
