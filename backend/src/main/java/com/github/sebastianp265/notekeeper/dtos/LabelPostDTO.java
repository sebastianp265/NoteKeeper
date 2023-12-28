package com.github.sebastianp265.notekeeper.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class LabelPostDTO {
    String name;
}
