import ResponsiveTextarea from "./ResponsiveTextarea.tsx";
import {useRef} from "react";
import {NotePut} from "../../../../api/notesApi.ts";
import {useAppDispatch, useAppSelector} from "../../hooks.ts";
import {deleteNote, selectNoteById, updateNote} from "./notesSlice.ts";
import {LabelBadge} from "../labels/LabelBadge.tsx";
import {LabelDropdown} from "../labels/LabelDropdown.tsx";

interface IProps {
    noteModalId: string;
    noteId: number
}

function NoteEditModal({noteModalId, noteId}: Readonly<IProps>) {
    const note = useAppSelector(state => selectNoteById(state, noteId))
    const {title, content, labelIds} = note

    const dispatch = useAppDispatch()

    const noteTitleInputRef = useRef<HTMLInputElement>(null)
    const noteContentInputRef = useRef<HTMLTextAreaElement>(null)

    const onSaveAndCloseButtonClicked = () => {
        const noteToUpdate: NotePut = {
            id: noteId,
            title: noteTitleInputRef.current?.value ?? "",
            content: noteContentInputRef.current?.value ?? "",
        }
        dispatch(updateNote({noteId, note: noteToUpdate}))
        const noteModalDialog = document.getElementById(noteModalId) as HTMLDialogElement
        noteModalDialog.close()
    }

    return (
        <dialog id={noteModalId} className="modal">
            <div className="modal-box p-1 overflow-visible">
                <div className="card-compact card-bordered rounded-xl">
                    <div className="card-body p-0">
                        <input
                            ref={noteTitleInputRef}
                            className="input card-title"
                            defaultValue={title}
                        />
                        <ResponsiveTextarea
                            textareaRef={noteContentInputRef}
                            initialContent={content}
                        />
                        {
                            labelIds.length > 0 &&
                            labelIds.map(
                                (labelId) => <LabelBadge key={labelId} labelId={labelId}/>
                            )
                        }
                    </div>
                </div>
                <div className="modal-action m-0">
                    <LabelDropdown noteId={noteId}/>
                    <svg onClick={() => dispatch(deleteNote(noteId))} className="w-10 fill-neutral cursor-pointer"
                         viewBox="0 0 24 24"
                         xmlns="http://www.w3.org/2000/svg">
                        <path d="M10 11V17" stroke="#000000" strokeWidth="2" strokeLinecap="round"
                              strokeLinejoin="round"/>
                        <path d="M14 11V17" stroke="#000000" strokeWidth="2" strokeLinecap="round"
                              strokeLinejoin="round"/>
                        <path d="M4 7H20" stroke="#000000" strokeWidth="2" strokeLinecap="round"
                              strokeLinejoin="round"/>
                        <path d="M6 7H12H18V18C18 19.6569 16.6569 21 15 21H9C7.34315 21 6 19.6569 6 18V7Z"
                              stroke="#000000" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                        <path d="M9 5C9 3.89543 9.89543 3 11 3H13C14.1046 3 15 3.89543 15 5V7H9V5Z" stroke="#000000"
                              strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"/>
                    </svg>
                    <form method="dialog">
                        <button className="btn m-2" onClick={onSaveAndCloseButtonClicked}>
                            Save and close
                        </button>
                    </form>
                </div>

            </div>
            <form method="dialog" className="modal-backdrop">
                <button>close</button>
            </form>
        </dialog>
    );
}

export default NoteEditModal;