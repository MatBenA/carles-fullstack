import logo from "../assets/terrenos-logo.svg";
import "../assets/styles/header.css";
import NavBar from "./NavBar";
import useAuth from "../hooks/useAuth";

const Header = () => {
    const { auth } = useAuth();
    return (
        <header className="top-bar">
            <img className="icon" src={logo} alt="#" />
            <h1>Carles Terrenos</h1>
            {auth?.accessToken ? <NavBar /> : <></>}
        </header>
    );
};

export default Header;
