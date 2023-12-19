package com.github.sebastianp265.notekeeper.repositories;

import com.github.sebastianp265.notekeeper.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {

}
