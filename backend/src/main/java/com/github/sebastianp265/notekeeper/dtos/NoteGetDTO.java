package com.github.sebastianp265.notekeeper.dtos;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class NoteGetDTO {
    Long id;
    String title;
    String content;
    List<Long> labelIds;
}
