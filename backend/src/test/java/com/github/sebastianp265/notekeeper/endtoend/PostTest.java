package com.github.sebastianp265.notekeeper.endtoend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sebastianp265.notekeeper.dtos.LabelGetDTO;
import com.github.sebastianp265.notekeeper.dtos.NoteGetDTO;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class PostTest {

    ObjectMapper objectMapper = new ObjectMapper();

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
    void createNote_whenNoteIsPosted_thenNoteIsInDB() throws Exception {
        // given
        String note = "{\"title\": \"title\", \"content\": \"content\"}";

        // when
        String response = mockMvc.perform(post("/notes")
                        .contentType("application/json")
                        .content(note))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // then
        NoteGetDTO responseNote = objectMapper.readValue(response, NoteGetDTO.class);

        Note noteInDB = noteRepository.findById(responseNote.getId()).orElseThrow();

        assertThat(noteInDB.getId()).isNotNull();
        assertThat(noteInDB.getTitle()).isEqualTo("title");
        assertThat(noteInDB.getContent()).isEqualTo("content");
        assertThat(noteInDB.getLabels()).isEmpty();
        assertThat(noteInDB.getCreatedDate()).isNotNull();
        assertThat(noteInDB.getLastModifiedDate()).isNotNull();
    }

    @Test
    void createLabel_whenLabelIsPosted_thenLabelIsInDB() throws Exception {
        // given
        String label = "{\"name\": \"label\"}";

        // when
        String response = mockMvc.perform(post("/labels")
                        .contentType("application/json")
                        .content(label))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // then
        LabelGetDTO labelDto = objectMapper.readValue(response, LabelGetDTO.class);

        Label labelInDB = labelRepository.findById(labelDto.getId()).orElseThrow();

        assertThat(labelInDB.getName()).isEqualTo("label");
    }

}
