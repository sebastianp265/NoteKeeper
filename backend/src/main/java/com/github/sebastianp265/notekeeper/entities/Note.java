package com.github.sebastianp265.notekeeper.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table
@SuperBuilder
@Setter
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Note extends Auditable {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String text;

    @ManyToOne
    private Label label;
}
