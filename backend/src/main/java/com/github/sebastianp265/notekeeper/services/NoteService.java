package com.github.sebastianp265.notekeeper.services;

import com.github.sebastianp265.notekeeper.dtos.NoteGetDTO;
import com.github.sebastianp265.notekeeper.dtos.NotePostDTO;
import com.github.sebastianp265.notekeeper.dtos.NotePutDTO;
import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.entities.Note;
import com.github.sebastianp265.notekeeper.mappings.NoteMapper;
import com.github.sebastianp265.notekeeper.repositories.LabelRepository;
import com.github.sebastianp265.notekeeper.repositories.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;


@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteMapper noteMapper;

    private final NoteRepository noteRepository;
    private final LabelRepository labelRepository;

    public List<NoteGetDTO> findAll() {
        return noteRepository.findAll()
                .stream()
                .map(noteMapper::toGetDTO)
                .toList();
    }

    public List<NoteGetDTO> findAllByLabel(String labelName) {
        return noteRepository.getAllByLabelName(labelName)
                .stream()
                .map(noteMapper::toGetDTO)
                .toList();
    }

    public NoteGetDTO findById(Long id) {
        return noteRepository.findById(id)
                .map(noteMapper::toGetDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }

    public NoteGetDTO create(NotePostDTO notePostDTO) {
        Note noteToSave = noteMapper.toEntity(notePostDTO);
        Note savedNote = noteRepository.save(noteToSave);
        return noteMapper.toGetDTO(savedNote);
    }

    public NoteGetDTO update(Long id, NotePutDTO notePutDTO) {
        Note noteToUpdate = noteRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with given id not found"));

        noteMapper.updateEntity(notePutDTO, noteToUpdate);
        Note savedNote = noteRepository.save(noteToUpdate);
        return noteMapper.toGetDTO(savedNote);
    }

    public void deleteById(Long id) {
        noteRepository.deleteById(id);
    }

    public NoteGetDTO addLabel(Long noteId, Long labelId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with given id not found"));
        if (note.getLabels().stream().anyMatch(label -> label.getId().equals(labelId))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Note already has label with given id");
        }

        Label label = labelRepository.findById(labelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label with given name not found"));

        note.getLabels().add(label);
        return noteMapper.toGetDTO(noteRepository.save(note));
    }

    public NoteGetDTO removeLabel(Long noteId, Long labelId) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note with given id not found"));

        Label label = labelRepository.findById(labelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label with given name not found"));
        note.getLabels().remove(label);

        return noteMapper.toGetDTO(noteRepository.save(note));
    }
}
