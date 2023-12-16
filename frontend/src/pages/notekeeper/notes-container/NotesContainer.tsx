import {forwardRef, useEffect, useImperativeHandle, useState} from 'react';
import {NoteGet, notesApi} from "../../../api/notesApi.ts";
import NoteView from "./components/NoteView.tsx";

export type AddNoteHandle = {
    addNote: (note: NoteGet) => void;
}

type Props = {};

const NotesContainer = forwardRef<AddNoteHandle, Props>((_props, ref) => {
    const [notes, setNotes] = useState<NoteGet[]>([])

    useImperativeHandle(ref, () => ({
        addNote(note: NoteGet) {
            setNotes([note, ...notes])
        }
    }))

    useEffect(() => {
        notesApi.getAll()
            .then((res) => {
                setNotes(res.data)
            })
            .catch(err => console.error('Get all notes error: ', err))
    }, [])

    const deleteNoteHandle = (noteId: number) => {
        notesApi.delete(noteId)
            .then(()=> {
                setNotes(notes.filter((note) => note.id != noteId))
            })
            .catch(err => console.error('Delete note by id error: ', err))
    }

    return (
        <div className="flex flex-wrap [&>*]:m-2 ml-2 w-full">
            {
                notes.map(
                    (note) =>
                        <NoteView key={note.id} id={note.id} initialNote={note} deleteNoteHandle={deleteNoteHandle}/>)
            }
        </div>
    );
})

export default NotesContainer