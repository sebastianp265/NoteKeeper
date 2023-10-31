package com.github.sebastianp265.notekeeper.entities;

import jakarta.persistence.GeneratedValue;
import org.springframework.data.annotation.Id;

public class Note {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String text;

}
