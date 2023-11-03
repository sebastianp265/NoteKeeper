package com.github.sebastianp265.notekeeper.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;


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

    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "label", cascade = CascadeType.ALL)
    private Set<Note> notes;

}
