import {Route, Routes} from "react-router-dom";
import Home from "./pages/home/Home";


export default function App() {
    return (
        <>
            <Routes>
                <Route index={true} element={<Home/>}></Route>
            </Routes>
        </>
    )
}