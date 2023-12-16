import {Label, labelApi} from "../../../api/labelsApi.ts";
import {useEffect, useState} from "react";
import NoteEditModal from "../notes-container/components/NoteEditModal.tsx";
import {NoteGet, notesApi} from "../../../api/notesApi.ts";

interface IProps {
    createNoteHandle: (note: NoteGet) => void
}

function SideBar({createNoteHandle}: Readonly<IProps>) {
    const [labelNames, setLabelNames] = useState<Label[]>([])

    useEffect(() => {
        labelApi.getAll()
            .then(res => {
                setLabelNames(res.data)
            })
            .catch(err => console.error(err))
    }, [])

    const modalSaveHandle = (title: string, content: string) => {
        notesApi.create({title, content})
            .then(res => {
                createNoteHandle(res.data)
            })
            .catch(err => console.error(err))
    }

    const createModalId = "create_note_modal"

    const openNoteEditModal = () => {
        console.log(`opening modal with note id = ${createModalId}`)
        const noteModalDialog = document.getElementById(createModalId) as HTMLDialogElement
        noteModalDialog.showModal()
    }

    return (
        <div className="flex flex-col space-y-4 ml-2 mt-2">
            <div>
                <button className="btn btn-outline" onClick={openNoteEditModal}>Create note</button>
                {
                    <NoteEditModal noteModalId={createModalId} saveHandle={modalSaveHandle}
                                   initialTitle={"Note example title"} initialContent={""} labelNames={[]}
                                   isCreateNote={true}/>
                }
            </div>
            {labelNames.map(
                (label) => (<button key={label.name} className="btn text-xs">{label.name}</button>)
            )}
        </div>
    );
}

export default SideBar;