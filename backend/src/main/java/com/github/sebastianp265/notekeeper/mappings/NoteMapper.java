package com.github.sebastianp265.notekeeper.mappings;

import com.github.sebastianp265.notekeeper.dto.NoteDto;
import com.github.sebastianp265.notekeeper.entities.Note;
import org.mapstruct.Mapper;

@Mapper
public interface NoteMapper {

    NoteDto toDto(Note note);

    Note toEntity(NoteDto noteDto);

}
