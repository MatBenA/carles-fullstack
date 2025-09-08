import { StrictMode } from "react";
import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import App from "./App.tsx";
import "./assets/styles/normalize.css";
import "./assets/styles/index.css";
import "./assets/styles/global.css";
import "./assets/styles/utils.css";
import { AuthProvider } from "./context/AuthProvider.tsx";
import { Toaster } from "sonner";

// 👇 Definís las rutas en un array
const router = createBrowserRouter(
  [
    {
      path: "/*",
      element: (
        <AuthProvider>
          <App />
        </AuthProvider>
      ),
    },
  ],
  {
    future: {
      v7_relativeSplatPath: true
    },
  }
);

createRoot(document.getElementById("root")!).render(
  <StrictMode>
    <Toaster richColors />
    <RouterProvider router={router} />
  </StrictMode>
);
