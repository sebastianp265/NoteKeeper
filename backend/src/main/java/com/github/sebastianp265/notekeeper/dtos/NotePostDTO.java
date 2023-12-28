package com.github.sebastianp265.notekeeper.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class NotePostDTO {
    String title;
    String content;
}
