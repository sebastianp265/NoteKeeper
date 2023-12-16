import {notesApi} from "../../../../api/notesApi.ts";
import NoteEditModal from "./NoteEditModal.tsx";
import {useState} from "react";
import NoteCard from "./NoteCard.tsx";

interface IProps {
    id: number;
    initialNote: {
        title: string;
        content: string;
        labelNames: string[];
    }
    deleteNoteHandle: (noteId: number) => void;
}

function NoteView({id, initialNote, deleteNoteHandle}: Readonly<IProps>) {
    const [{title, content, labelNames}, setNote] = useState(initialNote)
    const noteModalId = `note_modal_${id}`

    const openNoteEditModal = () => {
        console.log(`opening modal with note id = ${noteModalId}`)
        const noteModalDialog = document.getElementById(noteModalId) as HTMLDialogElement
        noteModalDialog.showModal()
    }

    const modalSaveHandle = (title: string, content: string) => {
        console.log(`handling save and close for note with id = ${id}`)
        notesApi.update(id, {id, title, content})
            .then(r =>
                setNote(r.data)
            )
            .catch(err => console.error('Update note error: ',err))
    }

    return (
        <>
            <NoteCard title={title} content={content} labelNames={labelNames} onClick={openNoteEditModal}></NoteCard>
            <NoteEditModal noteModalId={noteModalId} labelNames={labelNames} initialContent={content}
                           initialTitle={title} saveHandle={modalSaveHandle} deleteHandle={() => deleteNoteHandle(id)}/>
        </>

    );
}

export default NoteView;