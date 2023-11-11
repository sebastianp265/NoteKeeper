package com.github.sebastianp265.notekeeper.mappings;

import com.github.sebastianp265.notekeeper.dto.NoteDto;
import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.entities.Note;
import org.junit.jupiter.api.Test;

import java.util.Set;

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
                .labels(Set.of(Label.builder().name("label").build()
                        , Label.builder().name("label2").build()))
                .build();

        // when
        NoteDto noteDto = noteMapper.toDto(note);

        // then
        NoteDto expectedNoteDto = NoteDto.builder()
                .id(1L)
                .title("title")
                .content("content")
                .labelNames(Set.of("label", "label2"))
                .build();

        assertThat(noteDto).usingRecursiveComparison().isEqualTo(expectedNoteDto);
    }

    @Test
    void toNote_shouldMapNoteDtoToNote() {
        // given
        NoteDto noteDto = NoteDto.builder()
                .id(1L)
                .title("title")
                .content("content")
                .labelNames(Set.of("label", "label2"))
                .build();

        // when
        Note note = noteMapper.toEntity(noteDto);

        // then
        Note expectedNote = Note.builder()
                .id(1L)
                .title("title")
                .content("content")
                .labels(Set.of(Label.builder().name("label").build()
                        , Label.builder().name("label2").build()))
                .build();
        assertThat(note).usingRecursiveComparison().isEqualTo(expectedNote);
    }

}