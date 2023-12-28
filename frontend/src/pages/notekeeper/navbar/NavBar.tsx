import {useNavigate} from "react-router-dom";

function NavBar() {
    const navigate = useNavigate()

    return (
        <div className="flex navbar sticky top-0 bg-base-100 space-x-2">
            <button onClick={() => navigate("/")} className="btn btn-ghost text-xl">NoteKeeper</button>
            <input type="text" placeholder="Search" className="input input-bordered flex-grow"/>
            <button className="btn btn-ghost">Log out</button>
        </div>
    );
}

export default NavBar;