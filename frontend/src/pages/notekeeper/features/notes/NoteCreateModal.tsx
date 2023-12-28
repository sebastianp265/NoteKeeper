import ResponsiveTextarea from "./ResponsiveTextarea.tsx";
import {useRef} from "react";
import {NotePost} from "../../../../api/notesApi.ts";
import {useAppDispatch} from "../../hooks.ts";
import {createNote} from "./notesSlice.ts";

interface IProps {
    noteModalId: string;
}

function NoteCreateModal({noteModalId}: Readonly<IProps>) {
    const note: NotePost = {
        title: "Title example",
        content: ""
    }
    const {title, content} = note

    const dispatch = useAppDispatch()

    const noteTitleInputRef = useRef<HTMLInputElement>(null)
    const noteContentInputRef = useRef<HTMLTextAreaElement>(null)

    const onSaveAndCloseButtonClicked = () => {
        const noteToSave: NotePost = {
            title: noteTitleInputRef.current?.value ?? "",
            content: noteContentInputRef.current?.value ?? "",
        }
        dispatch(createNote(noteToSave))
        const noteModalDialog = document.getElementById(noteModalId) as HTMLDialogElement
        noteModalDialog.close()
        noteTitleInputRef.current!.value = note.title
        noteContentInputRef.current!.value = note.content
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
                    </div>
                </div>
                <div className="modal-action m-0">
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

export default NoteCreateModal;