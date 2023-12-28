package com.github.sebastianp265.notekeeper.repositories;

import com.github.sebastianp265.notekeeper.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("SELECT n FROM Note n JOIN n.labels l WHERE l.name = :labelName")
    List<Note> getAllByLabelName(String labelName);

}
