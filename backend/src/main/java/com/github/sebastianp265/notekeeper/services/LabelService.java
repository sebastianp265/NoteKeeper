package com.github.sebastianp265.notekeeper.services;

import com.github.sebastianp265.notekeeper.dtos.LabelGetDTO;
import com.github.sebastianp265.notekeeper.dtos.LabelPostDTO;
import com.github.sebastianp265.notekeeper.dtos.LabelPutDTO;
import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.mappings.LabelMapper;
import com.github.sebastianp265.notekeeper.repositories.LabelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LabelService {

    private final LabelMapper labelMapper;
    private final LabelRepository labelRepository;

    public List<LabelGetDTO> findAll() {
        return labelRepository
                .findAll()
                .stream()
                .map(labelMapper::toGetDTO)
                .toList();
    }

    public LabelGetDTO findById(Long id) {
        return labelRepository
                .findById(id)
                .map(labelMapper::toGetDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label not found"));
    }

    public LabelGetDTO create(LabelPostDTO labelPostDTO) {
        Label labelToSave = labelMapper.toEntity(labelPostDTO);
        Label savedLabel = labelRepository.save(labelToSave);
        return labelMapper.toGetDTO(savedLabel);
    }

    public LabelGetDTO update(Long id, LabelPutDTO labelPutDTO) {
        Label labelToUpdate = labelRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Label with given id not found"));

        labelMapper.updateEntity(labelPutDTO, labelToUpdate);
        Label savedLabel = labelRepository.save(labelToUpdate);
        return labelMapper.toGetDTO(savedLabel);
    }

    public void delete(Long id) {
        labelRepository.deleteById(id);
    }
}
