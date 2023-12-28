import {useNavigate} from "react-router-dom";
import NoteCreateModal from "../features/notes/NoteCreateModal.tsx";
import {useAppDispatch, useAppSelector} from "../hooks.ts";
import {fetchLabels, selectAllLabels} from "../features/labels/labelsSlice.ts";
import {useEffect} from "react";


function SideBar() {
    const noteModalId = "note_modal_create"
    const allLabels = useAppSelector(selectAllLabels)

    const dispatch = useAppDispatch()

    useEffect(() => {
        dispatch(fetchLabels())
    }, [dispatch])

    const openNoteCreateModal = () => {
        console.log(`opening modal with note id = ${noteModalId}`)
        const noteModalDialog = document.getElementById(noteModalId) as HTMLDialogElement
        noteModalDialog.showModal()
    }

    const navigate = useNavigate()

    return (
        <div className="flex flex-col space-y-4 ml-2 mt-2">
            <div>
                <button className="btn btn-outline" onClick={openNoteCreateModal}>Create note</button>
                <NoteCreateModal noteModalId={noteModalId}/>
            </div>
            {allLabels.map(
                ({id, name}) => (<button key={id}
                                         onClick={() => navigate(`/by-label-name/${name}`)}
                                         className="btn text-xs">{name}</button>)
            )}
        </div>
    );
}

export default SideBar;