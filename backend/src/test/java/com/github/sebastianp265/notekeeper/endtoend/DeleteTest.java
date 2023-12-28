package com.github.sebastianp265.notekeeper.endtoend;

import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.entities.Note;
import com.github.sebastianp265.notekeeper.repositories.LabelRepository;
import com.github.sebastianp265.notekeeper.repositories.NoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class DeleteTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private LabelRepository labelRepository;

    @AfterEach
    void tearDown() {
        noteRepository.deleteAll();
        labelRepository.deleteAll();
    }

    @Test
    void deleteNote_whenNoteExists_thenNoteIsDeleted() throws Exception {
        // given
        Long id = noteRepository.save(Note.builder()
                .title("title")
                .content("content")
                .labels(Collections.emptyList())
                .build()
        ).getId();

        // when
        mockMvc.perform(delete("/notes/" + id))
                .andExpect(status().isOk());

        // then
        assertThat(noteRepository.findAll()).isEmpty();
    }

    @Test
    void deleteLabel_whenLabelExists_thenLabelIsDeleted() throws Exception {
        // given
        Long id = labelRepository.save(Label.builder()
                .name("name")
                .build()
        ).getId();

        // when
        mockMvc.perform(delete("/labels/" + id))
                .andExpect(status().isOk());

        // then
        assertThat(labelRepository.findAll()).isEmpty();
    }

    @Test
    void deleteNote_whenNoteDoesNotExist_then200IsReturned() throws Exception {
        // given
        Long id = 101L;

        // when
        mockMvc.perform(delete("/notes/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void deleteLabel_whenLabelDoesNotExist_then200IsReturned() throws Exception {
        // given
        long id = 404L;

        // when
        mockMvc.perform(delete("/labels/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void deleteNote_whenLabelIsAttachedToNote_thenNoteIsDeletedAndLabelIsNotDeleted() throws Exception {
        // given
        Label label = labelRepository.save(Label.builder()
                .name("name")
                .build()
        );
        Note note = noteRepository.save(Note.builder()
                .title("title")
                .content("content")
                .labels(List.of(label))
                .build()
        );
        Long id = note.getId();

        // when
        mockMvc.perform(delete("/notes/" + id))
                .andExpect(status().isOk());

        // then
        assertThat(noteRepository.findAll()).isEmpty();
        assertThat(labelRepository.findAll()).hasSize(1);
    }

}
