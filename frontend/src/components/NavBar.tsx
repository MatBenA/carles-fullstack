import { Link } from "react-router-dom";
import "../assets/styles/nav-bar.css";
import useIsAdmin from "../hooks/useIsAdmin";

const NavBar = () => {
    const isAdmin: boolean = useIsAdmin();

    return (
        <nav className="navbar">
            <Link to="/land-surveys">
                <button>Relevamientos</button>
            </Link>
            <Link to="/statistics">
                <button>Estadisticas</button>
            </Link>
            <Link to="/update-reports">
                <button>Actualizaciones</button>
            </Link>
            <Link to="/notifications">
                <button>Notificaciones</button>
            </Link>
            {isAdmin ? (
                <Link to="/users">
                    <button>Usuarios</button>
                </Link>
            ) : (
                <></>
            )}
            {isAdmin ? (
                <Link to="/settings">
                    <button>Configuraciones</button>
                </Link>
            ) : (
                <></>
            )}
        </nav>
    );
};

export default NavBar;
