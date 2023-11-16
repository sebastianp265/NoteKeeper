import {StrictMode} from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import App from "./pages/notekeeper/App.tsx";

ReactDOM.createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <BrowserRouter>
            <Routes>
                <Route index element={<App/>}/>
            </Routes>
        </BrowserRouter>
    </StrictMode>,
)
