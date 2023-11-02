package com.github.sebastianp265.notekeeper.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Entity
@Table
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Label extends Auditable {

    @Id
    private String name;

    @OneToMany
    private Collection<Note> labels;

}
