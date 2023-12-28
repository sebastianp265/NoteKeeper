import {StrictMode} from 'react'
import ReactDOM from 'react-dom/client'
import './index.css'
import {BrowserRouter, Route, Routes} from "react-router-dom";
import App from "./pages/notekeeper/App.tsx";
import {Provider} from "react-redux";
import {store} from "./pages/notekeeper/store.ts";

ReactDOM.createRoot(document.getElementById('root')!).render(
    <StrictMode>
        <Provider store={store}>
            <BrowserRouter>
                <Routes>
                    <Route index element={<App/>}/>
                    <Route path="/by-label-name/:labelName" element={<App/>}/>
                </Routes>
            </BrowserRouter>
        </Provider>
    </StrictMode>,
)
