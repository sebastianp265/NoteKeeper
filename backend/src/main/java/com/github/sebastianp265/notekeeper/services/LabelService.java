package com.github.sebastianp265.notekeeper.services;

import com.github.sebastianp265.notekeeper.dto.LabelDto;
import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.mappings.LabelMapper;
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

    private final LabelMapper labelMapper;
    private final LabelRepository labelRepository;

    public Collection<LabelDto> findAll() {
        return labelRepository
                .findAll()
                .stream()
                .map(labelMapper::toDto)
                .toList();
    }

    public LabelDto findById(Long id) {
        return labelRepository
                .findById(id)
                .map(labelMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found"));
    }

    public LabelDto create(LabelDto labelDto) {
        if (labelDto.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provide label without id");
        }

        return save(labelDto);
    }

    public LabelDto update(Long id, LabelDto labelDto) {
        if (!Objects.equals(id, labelDto.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name from mapping doesn't match label name");
        }
        if (labelRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Label with given id not found");
        }

        return save(labelDto);
    }

    private LabelDto save(LabelDto labelDto) {
        Label label = labelMapper.toEntity(labelDto);
        Label savedLabel = labelRepository.save(label);
        return labelMapper.toDto(savedLabel);
    }

    public void delete(Long id) {
        labelRepository.deleteById(id);
    }
}
