import { useContext } from "react";
import AuthContext from "../context/AuthProvider";
import { Link, useNavigate } from "react-router-dom";

const Home = () => {
    const { setAuth } = useContext(AuthContext);
    const navigate = useNavigate();

    const logout = () => {
        setAuth({
            email: "",
            roles: [],
            accessToken: "",
        });
        navigate("/login");
    };

    return (
        <section>
            <h1>Home</h1>
            <br />

            <p>Estas Logueado!</p>
            <br />

            <Link to="/land-surveys">Relevamientos</Link>
            <br />

            <Link to="/admin">Ir a la pagina del Admin</Link>
            <br />

            <div className="flexGrow">
                <button onClick={logout}>Cerrar Sesion</button>
            </div>
        </section>
    );
};

export default Home;
