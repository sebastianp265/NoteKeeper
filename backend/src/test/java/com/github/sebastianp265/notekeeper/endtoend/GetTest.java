package com.github.sebastianp265.notekeeper.endtoend;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sebastianp265.notekeeper.dto.LabelDto;
import com.github.sebastianp265.notekeeper.dto.NoteDto;
import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.entities.Note;
import com.github.sebastianp265.notekeeper.mappings.LabelMapper;
import com.github.sebastianp265.notekeeper.mappings.NoteMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetTest {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private LabelMapper labelMapper;

    @AfterEach
    void tearDown() {
        noteRepository.deleteAll();
        labelRepository.deleteAll();
    }

    @Test
    void getNoteById_whenNoteExists_thenNoteIsReturned() throws Exception {
        // given
        Long id = 11L;
        Note note = Note.builder()
                .id(id)
                .title("title")
                .content("content")
                .labels(Collections.emptySet())
                .build();

        Note savedNote = noteRepository.save(note);

        // when
        String response = mockMvc.perform(get("/notes/" + savedNote.getId()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        NoteDto responseNoteDto = objectMapper.readValue(response, NoteDto.class);

        // then
        NoteDto expectedResponseNoteDto = noteMapper.toDto(savedNote);
        assertThat(responseNoteDto).usingRecursiveComparison().isEqualTo(expectedResponseNoteDto);
    }

    @Test
    void getLabelById_whenLabelExists_thenLabelIsReturned() throws Exception {
        // given
        String name = "name11";
        Label label = Label.builder()
                .name(name)
                .description("description")
                .build();

        Label savedLabel = labelRepository.save(label);

        // when
        String response = mockMvc.perform(get("/labels/" + savedLabel.getName()))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        LabelDto responseLabel = objectMapper.readValue(response, LabelDto.class);

        // then
        LabelDto expectedResponseLabelDto = labelMapper.toDto(savedLabel);
        assertThat(responseLabel).usingRecursiveComparison().isEqualTo(expectedResponseLabelDto);
    }

    @Test
    void getNoteById_whenNoteDoesNotExist_thenNotFoundIsReturned() throws Exception {
        // given
        Long id = 10000L;

        // when
        mockMvc.perform(get("/notes/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void getNoteById_whenNoteIdIsNotValid_thenBadRequestIsReturned() throws Exception {
        // given
        String id = "notValidId";

        // when
        mockMvc.perform(get("/notes/" + id))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getLabelById_whenLabelDoesNotExist_thenNotFoundIsReturned() throws Exception {
        // given
        String name = "labelNameThatDoesNotExist";

        // when
        mockMvc.perform(get("/labels/" + name))
                .andExpect(status().isNotFound());
    }

    @Test
    void getNotes_whenNotesExist_thenNotesAreReturned() throws Exception {
        // given
        Long id = 12L;
        Note note = Note.builder()
                .id(id)
                .title("title")
                .content("content")
                .labels(Collections.emptySet())
                .build();

        Note savedNote = noteRepository.save(note);

        // when
        String response = mockMvc.perform(get("/notes"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<NoteDto> responseNotes = objectMapper.readValue(response, new TypeReference<>() {});

        // then
        NoteDto expectedResponseNoteDto = noteMapper.toDto(savedNote);
        assertThat(responseNotes).usingRecursiveComparison().isEqualTo(List.of(expectedResponseNoteDto));
    }

    @Test
    void getLabels_whenLabelsExist_thenLabelsAreReturned() throws Exception {
        // given
        String name = "name12";
        Label label = Label.builder()
                .name(name)
                .description("description")
                .build();

        Label savedLabel = labelRepository.save(label);

        // when
        String response = mockMvc.perform(get("/labels"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<LabelDto> responseLabels = objectMapper.readValue(response, new TypeReference<>() {});

        // then
        LabelDto expectedResponseLabelDto = labelMapper.toDto(savedLabel);
        assertThat(responseLabels).usingRecursiveComparison().isEqualTo(List.of(expectedResponseLabelDto));
    }

}
