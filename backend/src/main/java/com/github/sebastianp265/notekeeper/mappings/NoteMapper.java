package com.github.sebastianp265.notekeeper.mappings;

import com.github.sebastianp265.notekeeper.dtos.NoteGetDTO;
import com.github.sebastianp265.notekeeper.dtos.NotePostDTO;
import com.github.sebastianp265.notekeeper.dtos.NotePutDTO;
import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.entities.Note;
import org.mapstruct.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface NoteMapper {

    @Mapping(target = "labelIds", source = "labels", qualifiedByName = "labelsToLabelIds")
    NoteGetDTO toGetDTO(Note note);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "labels", ignore = true)
    Note toEntity(NotePostDTO notePostDTO);

    @Named("labelsToLabelIds")
    default List<Long> labelsToLabelIds(List<Label> labels) {
        if (labels == null) {
            return Collections.emptyList();
        }
        return labels.stream()
                .map(Label::getId)
                .toList();
    }

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateEntity(NotePutDTO notePutDTO, @MappingTarget Note note);
}
