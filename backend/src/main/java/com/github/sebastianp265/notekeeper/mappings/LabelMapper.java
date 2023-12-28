package com.github.sebastianp265.notekeeper.mappings;

import com.github.sebastianp265.notekeeper.dtos.LabelGetDTO;
import com.github.sebastianp265.notekeeper.dtos.LabelPostDTO;
import com.github.sebastianp265.notekeeper.dtos.LabelPutDTO;
import com.github.sebastianp265.notekeeper.entities.Label;
import org.mapstruct.*;

@Mapper
public interface LabelMapper {

    LabelGetDTO toGetDTO(Label label);

    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    Label toEntity(LabelPostDTO labelPostDTO);

    @BeanMapping(unmappedTargetPolicy = ReportingPolicy.IGNORE)
    void updateEntity(LabelPutDTO labelPutDTO, @MappingTarget Label label);
}
