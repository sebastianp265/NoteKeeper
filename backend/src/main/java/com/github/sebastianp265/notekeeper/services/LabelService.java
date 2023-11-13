package com.github.sebastianp265.notekeeper.services;

import com.github.sebastianp265.notekeeper.dto.LabelDto;
import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.mappings.LabelMapper;
import com.github.sebastianp265.notekeeper.repositories.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelMapper labelMapper;
    private final LabelRepository labelRepository;

    public Collection<LabelDto> findAll() {
        return labelRepository
                .findAll()
                .stream()
                .map(labelMapper::toDto)
                .toList();
    }

    public LabelDto findById(String labelName) {
        return labelRepository
                .findById(labelName)
                .map(labelMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found"));
    }

    public LabelDto create(LabelDto labelDto) {
        if (labelDto.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Label name has to be provided");
        }
        if (labelRepository.findById(labelDto.getName()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Label with this name already exists");
        }

        return save(labelDto);
    }

    public ResponseEntity<LabelDto> update(String name, LabelDto labelDto) {
        if (!Objects.equals(name, labelDto.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name from mapping doesn't match label name");
        }
        HttpStatus status = HttpStatus.OK;
        if (labelRepository.findById(name).isEmpty()) {
            status = HttpStatus.CREATED;
        }

        return new ResponseEntity<>(save(labelDto), status);
    }

    private LabelDto save(LabelDto labelDto) {
        Label label = labelMapper.toEntity(labelDto);
        return labelMapper.toDto(labelRepository.save(label));
    }

    public void delete(String labelName) {
        labelRepository.deleteById(labelName);
    }
}
