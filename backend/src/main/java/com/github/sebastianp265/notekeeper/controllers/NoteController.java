package com.github.sebastianp265.notekeeper.controllers;

import com.github.sebastianp265.notekeeper.entities.Note;
import com.github.sebastianp265.notekeeper.services.NoteService;
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
@Tag(name = "Notes", description = "Notes management APIs")
@RequestMapping(value = "notes")
@RequiredArgsConstructor
public class NoteController {

    public final NoteService noteService;

    @Operation(summary = "Find all Notes", description = "Get all Note objects.", tags = {"GET"})
    @GetMapping
    public Collection<Note> findAll() {
        log.debug("Finding all notes");
        return noteService.findAll();
    }


    @Operation(summary = "Find Note by id", description = "Get a Note object by providing it's id.", tags = {"GET"})
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "404", description = "Given id doesn't match any existing Note id", content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    public Note findById(@PathVariable Long id) {
        log.debug("Finding note by id = {}", id);
        return noteService.findById(id);
    }


    @PostMapping
    @Operation(summary = "Create Note", description = "Create Note object by providing Note body", tags = {"POST"})
    @ApiResponse(responseCode = "201")
    @ApiResponse(responseCode = "400", description = "Given note body has provided id", content = {@Content(schema = @Schema())})
    @ResponseStatus(HttpStatus.CREATED)
    public Note create(@RequestBody Note note) {
        log.debug("Creating note with body = {}", note);
        return noteService.create(note);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update Note", description = "Update note object by providing it's id and Note body", tags = {"PUT"})
    @ApiResponse(responseCode = "200")
    @ApiResponse(responseCode = "400", description = "Provided id from mapping doesn't match note id", content = {@Content(schema = @Schema())})
    public Note update(@PathVariable Long id, @RequestBody Note note) {
        log.debug("Updating note with id = {}\nand body = {}", id, note);
        return noteService.update(id, note);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Note", description = "Delete Note object by providing it's id", tags = {"DELETE"})
    public void delete(@PathVariable Long id) {
        log.debug("Updating note with id = {}", id);
        noteService.deleteById(id);
    }
}
