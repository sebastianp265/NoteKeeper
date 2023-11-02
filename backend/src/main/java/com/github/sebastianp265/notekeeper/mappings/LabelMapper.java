package com.github.sebastianp265.notekeeper.mappings;

import com.github.sebastianp265.notekeeper.dto.LabelDto;
import com.github.sebastianp265.notekeeper.entities.Label;
import org.mapstruct.Mapper;

@Mapper
public interface LabelMapper {

    LabelDto toDto(Label label);

    Label toEntity(LabelDto labelDto);
}
