package com.github.sebastianp265.notekeeper.services;

import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.repositories.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelRepository labelRepository;

    public Collection<Label> findAll() {
        return labelRepository.findAll();
    }

    public Label findById(String name) {
        return labelRepository.findById(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found"));
    }

    public Label create(Label label) {
        if(label.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Label name has to be provided");
        }

        return labelRepository.save(label);
    }

    public Label update(String name, Label label) {
        if(!Objects.equals(label.getName(), name) ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Name from mapping doesn't match label name");
        }

        label.setName(name);
        return labelRepository.save(label);
    }

    public void deleteById(String name) {
        labelRepository.deleteById(name);
    }
}
