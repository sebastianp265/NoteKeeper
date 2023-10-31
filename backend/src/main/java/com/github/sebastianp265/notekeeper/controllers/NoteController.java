package com.github.sebastianp265.notekeeper.controllers;

import com.github.sebastianp265.notekeeper.entities.Note;
import com.github.sebastianp265.notekeeper.services.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Slf4j
@RestController
@RequestMapping(value = "notes")
@RequiredArgsConstructor
public class NoteController {

    public final NoteService noteService;

    @GetMapping
    public Collection<Note> findAll() {
        log.debug("Finding all notes");
        return noteService.findAll();
    }

    @GetMapping("/{id}")
    public Note findById(@PathVariable Long id) {
        log.debug("Finding note by id = " + id);
        return noteService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Note create(@RequestBody Note note) {
        log.debug("Creating note with body = " + note);
        return noteService.create(note);
    }

    @PutMapping("/{id}")
    public Note update(@PathVariable Long id, @RequestBody Note note) {
        log.debug("Updating note with id = {}\nand body = {}", id, note);
        return noteService.update(id, note);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.debug("Updating note with id = {}", id);
        noteService.deleteById(id);
    }
}
