package com.github.sebastianp265.notekeeper.services;

import com.github.sebastianp265.notekeeper.dto.NoteDto;
import com.github.sebastianp265.notekeeper.entities.Note;
import com.github.sebastianp265.notekeeper.mappings.NoteMapper;
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

    private final NoteMapper noteMapper;
    private final NoteRepository noteRepository;

    public Collection<NoteDto> findAll() {
        return noteRepository.findAll()
                .stream()
                .map(noteMapper::toDto)
                .toList();
    }

    public NoteDto findById(Long id) {
        return noteRepository.findById(id)
                .map(noteMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }

    public NoteDto create(NoteDto noteDto) {
        if (noteDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide note without id");
        }

        return save(noteDto);
    }

    public NoteDto update(Long id, NoteDto noteDto) {
        if(!Objects.equals(noteDto.getId(), id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided id from mapping doesn't match note id");
        }

        return save(noteDto);
    }

    private NoteDto save(NoteDto noteDto) {
        Note note = noteMapper.toEntity(noteDto);
        return noteMapper.toDto(noteRepository.save(note));
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }
}
