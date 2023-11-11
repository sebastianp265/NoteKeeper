package com.github.sebastianp265.notekeeper.endtoend;

import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.entities.Note;
import com.github.sebastianp265.notekeeper.repositories.LabelRepository;
import com.github.sebastianp265.notekeeper.repositories.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Test
    void whenNoteIsPosted_thenNoteIsInDB() throws Exception {
        // given
        String note = "{\"title\": \"title\", \"content\": \"content\"}";

        // when
        mockMvc.perform(post("/notes")
                .contentType("application/json")
                .content(note))
                .andExpect(status().isCreated());

        // then
        List<Note> notes = noteRepository.findAll();

        assertThat(notes).hasSize(1);

        Note noteInDB = notes.get(0);

        assertThat(noteInDB.getId()).isNotNull();
        assertThat(noteInDB.getTitle()).isEqualTo("title");
        assertThat(noteInDB.getContent()).isEqualTo("content");
        assertThat(noteInDB.getLabels()).isEmpty();
        assertThat(noteInDB.getCreatedDate()).isNotNull();
        assertThat(noteInDB.getLastModifiedDate()).isNotNull();
    }

    @Test
    void whenLabelIsPosted_thenLabelIsInDB() throws Exception {
        // given
        String label = "{\"name\": \"label\", \"description\": \"description\"}";

        // when
        mockMvc.perform(post("/labels")
                .contentType("application/json")
                .content(label))
                .andExpect(status().isCreated());

        // then
        List<Label> labels = labelRepository.findAll();

        assertThat(labels).hasSize(1);

        Label labelInDB = labels.get(0);

        assertThat(labelInDB.getName()).isEqualTo("label");
    }

    @Test
    void whenTwoLabelsWithTheSameNameArePosted_thenExpectBadRequest() throws Exception {
        // given
        String label = "{\"name\": \"label\", \"description\": \"description\"}";
        mockMvc.perform(post("/labels")
                .contentType("application/json")
                .content(label))
                .andExpect(status().isCreated());

        // when
        String labelWithTheSameName = "{\"name\": \"label\", \"description\": \"Description of the second label\"}";
        mockMvc.perform(post("/labels")
                .contentType("application/json")
                .content(labelWithTheSameName))
                .andExpect(status().isBadRequest());

        // then
        List<Label> labels = labelRepository.findAll();

        assertThat(labels).hasSize(1);

        Label labelInDB = labels.get(0);

        assertThat(labelInDB.getName()).isEqualTo("label");
        assertThat(labelInDB.getDescription()).isEqualTo("description");
    }

}
