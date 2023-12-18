package com.github.sebastianp265.notekeeper.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class LabelDto {

    @Schema(description = "Label name")
    @JsonView({Views.Get.class, Views.Post.class, Views.Put.class})
    String name;

}
