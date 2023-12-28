import NoteEditModal from "./NoteEditModal.tsx";
import NoteCard from "./NoteCard.tsx";

interface IProps {
    noteId: number
}

function NoteView({noteId}: Readonly<IProps>) {
    const noteModalId = `note_modal_${noteId}`

    const openNoteEditModal = () => {
        console.log(`opening modal with note id = ${noteModalId}`)
        const noteModalDialog = document.getElementById(noteModalId) as HTMLDialogElement
        noteModalDialog.showModal()
    }

    return (
        <>
            <NoteCard noteId={noteId} onClick={openNoteEditModal}/>
            <NoteEditModal noteModalId={noteModalId} noteId={noteId}/>
        </>

    );
}

export default NoteView;