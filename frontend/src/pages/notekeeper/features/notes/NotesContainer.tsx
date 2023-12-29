import {useEffect} from 'react';
import NoteView from "./NoteView.tsx";
import {useParams} from "react-router-dom";
import {useAppDispatch, useAppSelector} from "../../hooks.ts";
import {fetchNotes, fetchNotesByLabelName, selectAllNoteIds} from "./notesSlice.ts";

function NotesContainer() {
    const noteIds = useAppSelector(selectAllNoteIds)

    const dispatch = useAppDispatch()

    const {labelName} = useParams()

    useEffect(() => {
        if (labelName === undefined) {
            dispatch(fetchNotes())
        } else {
            dispatch(fetchNotesByLabelName(labelName))
        }
    }, [dispatch, labelName])

    return (
        <div className="flex flex-wrap [&>*]:m-2 ml-2 h-fit w-full">
            {
                noteIds.map(
                    (noteId) => <NoteView key={noteId} noteId={noteId}></NoteView>
                )
            }
        </div>
    );
}

export default NotesContainer