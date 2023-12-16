import ResponsiveTextarea from "./ResponsiveTextarea.tsx";
import {useState} from "react";

interface IProps {
    noteModalId: string;
    saveHandle: (title: string, content: string) => void;
    deleteHandle?: () => void;
    initialTitle: string;
    initialContent: string;
    labelNames: string[];
    isCreateNote?: boolean
}

function NoteEditModal({
                           noteModalId,
                           saveHandle,
                           deleteHandle,
                           initialTitle,
                           initialContent,
                           labelNames,
                           isCreateNote = false
                       }: Readonly<IProps>) {
    const [modifiedTitle, setModifiedTitle] = useState(initialTitle)
    const [modifiedContent, setModifiedContent] = useState(initialContent)

    const onSaveAndCloseButtonClicked = () => {
        saveHandle(modifiedTitle, modifiedContent)
        if (isCreateNote) {
            revertNoteToInitialState()
        }
    }

    const revertNoteToInitialState = () => {
        setModifiedTitle(initialTitle)
        setModifiedContent(initialContent)
    }

    return (
        <dialog id={noteModalId} className="modal">
            <div className="modal-box p-1">
                <div className="card-compact card-bordered rounded-xl">
                    <div className="card-body p-0">
                        <input
                            className="input card-title"
                            value={modifiedTitle}
                            onChange={e => setModifiedTitle(e.target.value)}
                        />
                        <ResponsiveTextarea
                            initialContent={modifiedContent}
                            onContentChange={setModifiedContent}
                        />
                        {
                            labelNames.length > 0 &&
                            <div className="flex pt-2 justify-end">
                                {
                                    labelNames.map(
                                        (labelName) => <div key={labelName} className="badge">{labelName}</div>)
                                }
                            </div>
                        }
                    </div>
                </div>
                <div className="modal-action m-0">
                    {
                        deleteHandle !== undefined &&
                        <svg onClick={deleteHandle} className="w-10 fill-neutral cursor-pointer" viewBox="0 0 24 24"
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
                    }
                    <form method="dialog">
                        <button className="btn m-2" onClick={onSaveAndCloseButtonClicked}>
                            Save and close
                        </button>
                    </form>
                </div>

            </div>
            <form method="dialog" className="modal-backdrop">
                <button onClick={revertNoteToInitialState}>close</button>
            </form>
        </dialog>
    );
}

export default NoteEditModal;