package com.github.sebastianp265.notekeeper.repositories;

import com.github.sebastianp265.notekeeper.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
