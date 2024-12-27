import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import App from "./App.tsx";
import "./assets/styles/normalize.css";
import "./assets/styles/index.css";
import "./assets/styles/global.css";
import "./assets/styles/utils.css";
import { AuthProvider } from "./context/AuthProvider.tsx";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Toaster } from "sonner";

createRoot(document.getElementById("root")!).render(
    <StrictMode>
        <Toaster richColors />
        <BrowserRouter>
            <AuthProvider>
                <Routes>
                    <Route path="/*" element={<App />} />
                </Routes>
            </AuthProvider>
        </BrowserRouter>
    </StrictMode>
);
