package com.github.sebastianp265.notekeeper.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;


@Entity
@Table
@Data
@SuperBuilder
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Label extends Auditable {

    @Id
    @GeneratedValue
    @Column(name = "label_id")
    private Long id;

    @Column(unique = true)
    private String name;

}
