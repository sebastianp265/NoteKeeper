package com.github.sebastianp265.notekeeper.mappings;

import com.github.sebastianp265.notekeeper.dto.NoteDto;
import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.entities.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface NoteMapper {

    @Mapping(source = "labels", target = "labelNames", qualifiedByName = "labelsToLabelNames")
    NoteDto toDto(Note note);

    @Mapping(source = "labelNames", target = "labels", qualifiedByName = "labelNamesToLabels")
    Note toEntity(NoteDto noteDto);

    @Named("labelsToLabelNames")
    default Set<String> labelsToLabelNames(Set<Label> labels) {
        return labels.stream()
                .map(Label::getName)
                .collect(Collectors.toSet());
    }

    @Named("labelNamesToLabels")
    default Set<Label> labelNamesToLabels(Set<String> labelNames) {
        if (labelNames == null) {
            return Collections.emptySet();
        }
        return labelNames.stream()
                .map(labelName -> Label.builder().name(labelName).build())
                .collect(Collectors.toSet());
    }

}
