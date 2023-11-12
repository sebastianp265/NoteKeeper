package com.github.sebastianp265.notekeeper.mappings;

import com.github.sebastianp265.notekeeper.dto.LabelDto;
import com.github.sebastianp265.notekeeper.entities.Label;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface LabelMapper {

    LabelDto toDto(Label label);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    Label toEntity(LabelDto labelDto);
}
