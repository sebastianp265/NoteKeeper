import NavBar from "./navbar/NavBar.tsx";
import NotesContainer from "./features/notes/NotesContainer.tsx";
import SideBar from "./sidebar/SideBar.tsx";

function App() {
    return (
        <div className="flex flex-col [&>*]:p-1.5">
            <NavBar/>
            <div className="flex flex-row">
                <SideBar/>
                <NotesContainer/>
            </div>
        </div>
    )
}

export default App
