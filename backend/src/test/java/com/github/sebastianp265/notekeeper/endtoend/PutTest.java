package com.github.sebastianp265.notekeeper.endtoend;

import com.github.sebastianp265.notekeeper.entities.Label;
import com.github.sebastianp265.notekeeper.entities.Note;
import com.github.sebastianp265.notekeeper.repositories.LabelRepository;
import com.github.sebastianp265.notekeeper.repositories.NoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PutTest {

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
    void updateNote_whenNoteExists_thenNoteIsUpdated() throws Exception {
        // given
        Note note = Note.builder()
                .title("title")
                .content("content")
                .labels(Collections.emptyList())
                .build();
        noteRepository.save(note);

        Note noteInDBBeforeUpdating = noteRepository.findById(note.getId()).orElseThrow();
        Long id = noteInDBBeforeUpdating.getId();
        Instant noteInDBCreationDate = noteInDBBeforeUpdating.getCreatedDate();
        Instant noteInDBLastModifiedDateBeforeUpdating = noteInDBBeforeUpdating.getLastModifiedDate();

        String updatedTitle = "updated title";
        String updatedContent = "updated content";

        String updatedNote = "{\"id\": " + id + ", \"title\": \"" + updatedTitle + "\", \"content\": \"" + updatedContent + "\"}";

        // when
        mockMvc.perform(put("/notes/" + id)
                        .contentType("application/json")
                        .content(updatedNote))
                .andExpect(status().isOk());

        // then
        Note updatedNoteInDB = noteRepository.findById(id).orElseThrow();

        assertThat(updatedNoteInDB.getId()).isEqualTo(id);
        assertThat(updatedNoteInDB.getTitle()).isEqualTo(updatedTitle);
        assertThat(updatedNoteInDB.getContent()).isEqualTo(updatedContent);
        assertThat(updatedNoteInDB.getLabels()).isEmpty();
        assertThat(updatedNoteInDB.getCreatedDate()).isEqualTo(noteInDBCreationDate);
        assertThat(updatedNoteInDB.getLastModifiedDate()).isAfter(noteInDBLastModifiedDateBeforeUpdating);
    }

    @Test
    void updateLabel_whenLabelExists_thenLabelIsUpdated() throws Exception {
        // given
        Label savedLabel = labelRepository.save(Label.builder()
                .name("Simple name before update")
                .build());

        Label labelInDBBeforeUpdating = labelRepository.findById(savedLabel.getId()).orElseThrow();

        Instant labelInDBCreationDate = labelInDBBeforeUpdating.getCreatedDate();
        Instant labelInDBLastModifiedDateBeforeUpdating = labelInDBBeforeUpdating.getLastModifiedDate();

        String updatedName = "Simple name after update";

        String updatedLabel = "{\"id\": \"" + savedLabel.getId() + "\", \"name\": \"" + updatedName + "\"}";

        // when
        mockMvc.perform(put("/labels/" + savedLabel.getId())
                        .contentType("application/json")
                        .content(updatedLabel))
                .andExpect(status().isOk());

        // then
        Label updatedLabelInDB = labelRepository.findById(savedLabel.getId()).orElseThrow();

        assertThat(updatedLabelInDB.getId()).isEqualTo(savedLabel.getId());
        assertThat(updatedLabelInDB.getName()).isEqualTo(updatedName);
        assertThat(updatedLabelInDB.getCreatedDate()).isEqualTo(labelInDBCreationDate);
        assertThat(updatedLabelInDB.getLastModifiedDate()).isAfter(labelInDBLastModifiedDateBeforeUpdating);
    }

    @Test
    void updateNote_whenNoteDoesNotExist_thenNotFoundIsReturned() throws Exception {
        // given
        long id = 1003L;

        String updatedNote = "{\"id\": " + id + ", \"title\": \"updated title\", \"content\": \"updated content\"}";

        // when
        mockMvc.perform(put("/notes/" + id)
                        .contentType("application/json")
                        .content(updatedNote))
                .andExpect(status().isNotFound());
    }

     @Test
    void updateNote_whenNoteIdIsNotValid_thenBadRequestIsReturned() throws Exception {
        // given
        String id = "notValidId";

        String updatedNote = "{\"id\": " + id + ", \"title\": \"updated title\", \"content\": \"updated content\"}";

        // when
        mockMvc.perform(put("/notes/" + id)
                        .contentType("application/json")
                        .content(updatedNote))
                .andExpect(status().isBadRequest());
    }

    @Test
    void attachLabel_whenNoteWithGivenNoteIdDoesNotExist_thenNotFoundIsReturned() throws Exception {
        // given
        long id = 1001L;
        String labelName = "labelName";

        Label label = Label.builder()
                .name(labelName)
                .build();
        Label savedLabel = labelRepository.save(label);

        // when
        mockMvc.perform(put("/notes/" + id + "/add-label/" + savedLabel.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void attachLabel_whenLabelWithGivenLabelNameDoesNotExist_thenNotFoundIsReturned() throws Exception {
        // given
        Note note = Note.builder()
                .title("title")
                .content("content")
                .build();
        noteRepository.save(note);

        long labelId = 1001L;

        // when
        mockMvc.perform(put("/notes/" + note.getId() + "/add-label/" + labelId))
                .andExpect(status().isNotFound());
    }

    @Test
    void attachLabel_whenLabelAndNoteExist_thenLabelIsAttachedToNote() throws Exception {
        // given
        Note note = Note.builder()
                .title("title")
                .content("content")
                .build();
        Note savedNote = noteRepository.save(note);

        Label label = Label.builder()
                .name("labelName")
                .build();
        Label savedLabel = labelRepository.save(label);

        // when
        mockMvc.perform(put("/notes/" + savedNote.getId() + "/add-label/" + savedLabel.getId()))
                .andExpect(status().isOk());

        // then

        Label expectedLabel = labelRepository.findById(label.getId()).orElseThrow();
        Note noteInDB = noteRepository.findById(note.getId()).orElseThrow();

        assertThat(noteInDB.getLabels()).hasSize(1);
        Label actualLabel = noteInDB.getLabels().iterator().next();
        assertThat(actualLabel).usingRecursiveComparison().isEqualTo(expectedLabel);
    }

    @Test
    void detachLabel_whenLabelWasAttachedToNoteFirst_thenLabelAndNoteExistInDBButTheyAreNotAttached() throws Exception {
        // given
        Note note = Note.builder()
                .title("title")
                .content("content")
                .build();
        note = noteRepository.save(note);

        Label label = Label.builder()
                .name("labelName")
                .build();
        label = labelRepository.save(label);

        note = noteRepository.findById(note.getId()).orElseThrow();
        label = labelRepository.findById(label.getId()).orElseThrow();

        note.getLabels().add(label);
        noteRepository.save(note);

        // when
        mockMvc.perform(put("/notes/" + note.getId() + "/remove-label/" + label.getId()))
                .andExpect(status().isOk());

        // then
        Note noteInDB = noteRepository.findById(note.getId()).orElseThrow();
        assertThat(noteInDB.getLabels()).isEmpty();
    }
}
