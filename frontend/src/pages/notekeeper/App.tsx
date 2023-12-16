import NavBar from "./navbar/NavBar.tsx";
import NotesContainer, {AddNoteHandle} from "./notes-container/NotesContainer.tsx";
import SideBar from "./sidebar/SideBar.tsx";
import {useRef} from "react";
import {NoteGet} from "../../api/notesApi.ts";

function App() {
    const notesContainerRef = useRef<AddNoteHandle>(null)

    const createNoteHandle = (note: NoteGet) => {
        if (notesContainerRef.current) {
            notesContainerRef.current.addNote(note)
        }
    }


    return (
        <div className="flex flex-col [&>*]:p-1.5">
            <NavBar/>
            <div className="flex flex-row">
                <SideBar createNoteHandle={createNoteHandle}/>
                <NotesContainer ref={notesContainerRef}/>
            </div>
        </div>
    )
}

export default App
