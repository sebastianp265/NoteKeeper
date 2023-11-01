package com.github.sebastianp265.notekeeper.services;

import com.github.sebastianp265.notekeeper.entities.Note;
import com.github.sebastianp265.notekeeper.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public Collection<Note> findAll() {
        return noteRepository.findAll();
    }

    public Note findById(Long id) {
        return noteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }

    public Note create(Note note) {
        if (note.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide note without id");
        }

        return noteRepository.save(note);
    }

    public Note update(Long id, Note note) {
        if(!Objects.equals(note.getId(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided id from mapping doesn't match note id");
        }

        note.setId(id);
        return noteRepository.save(note);
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }
}
