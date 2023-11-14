import {Route, Routes} from "react-router-dom";
import LearnReact from "./pages/learn_react/LearnReact";


export default function App() {
    return (
        <>
            <Routes>
                <Route path={"/learn-react"} element={<LearnReact/>}></Route>
            </Routes>
        </>
    )
}