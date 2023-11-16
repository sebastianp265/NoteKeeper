import SideBar from "./SideBar.tsx";

function App() {
    return (
        <>
            <div className="flex space-x-4">
                <SideBar/>
                <h1 className="text-2xl font-bold underline">
                    Hello world!
                </h1>
            </div>

        </>
    )
}

export default App
