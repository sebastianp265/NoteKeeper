function NavBar() {
    return (
        <div className="flex navbar sticky top-0 bg-base-100 space-x-2">
            <button className="btn btn-ghost text-xl">NoteKeeper</button>
            <input type="text" placeholder="Search" className="input input-bordered flex-grow"/>
            <button className="btn btn-ghost">Log out</button>
        </div>
    );
}

export default NavBar;