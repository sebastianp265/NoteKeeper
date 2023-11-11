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

    public LabelDto findByLabelName(String labelName) {
        return labelRepository
                .findById(labelName)
                .map(labelMapper::toDto)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found"));
    }

    public LabelDto create(LabelDto labelDto) {
        if(labelDto.getName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Label name has to be provided");
        }

        return save(labelDto);
    }

    public LabelDto update(String name, LabelDto labelDto) {
        if(!Objects.equals(labelDto.getName(), name) ) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Name from mapping doesn't match label name");
        }

        return save(labelDto);
    }

    private LabelDto save(LabelDto labelDto) {
        Label label = labelMapper.toEntity(labelDto);
        return labelMapper.toDto(labelRepository.save(label));
    }

    public void delete(String labelName) {
        labelRepository.deleteById(labelName);
    }
}
