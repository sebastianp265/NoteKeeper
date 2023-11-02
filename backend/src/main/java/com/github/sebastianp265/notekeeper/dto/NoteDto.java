package com.github.sebastianp265.notekeeper.dto;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class NoteDto {

    @Schema(description = "Note primary key")
    @JsonView({Views.Get.class, Views.Put.class})
    Long id;

    @Schema(description = "Note title", example = "Note title example")
    @JsonView({Views.Get.class, Views.Post.class, Views.Put.class})
    String title;

    @Schema(description = "Note text", example = "Note text example")
    @JsonView({Views.Get.class, Views.Post.class, Views.Put.class})
    String text;

    @Schema(description = "Label foreign key")
    @JsonView({Views.Get.class, Views.Post.class, Views.Put.class})
    LabelDto label;

}
