package com.github.sebastianp265.notekeeper.mappings;

import com.github.sebastianp265.notekeeper.dtos.NoteGetDTO;
import com.github.sebastianp265.notekeeper.dtos.NotePostDTO;
import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.entities.Note;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class NoteMapperTest {

    private final NoteMapper noteMapper = new NoteMapperImpl();

    @Test
    void toDto_shouldMapNoteToNoteDto() {
        // given
        Note note = Note.builder()
                .id(1L)
                .title("title")
                .content("content")
                .labels(List.of(
                        Label.builder().id(1L).name("label1").build(),
                        Label.builder().id(2L).name("label2").build()))
                .build();

        // when
        NoteGetDTO noteDto = noteMapper.toGetDTO(note);

        // then
        NoteGetDTO expectedNoteDto = NoteGetDTO.builder()
                .id(1L)
                .title("title")
                .content("content")
                .labelIds(List.of(1L, 2L))
                .build();

        assertThat(noteDto).usingRecursiveComparison().isEqualTo(expectedNoteDto);
    }

    @Test
    void toNote_shouldMapNoteDtoToNote() {
        // given
        NotePostDTO noteDto = NotePostDTO.builder()
                .title("title")
                .content("content")
                .build();

        // when
        Note note = noteMapper.toEntity(noteDto);

        // then
        Note expectedNote = Note.builder()
                .title("title")
                .content("content")
                .build();
        assertThat(note).usingRecursiveComparison().isEqualTo(expectedNote);
    }

}