import logo from "../assets/terrenos-logo.svg";
import "../assets/styles/header.css";
import NavBar from "./NavBar";
import useAuthStore from "../hooks/useAuthStore";

const Header = () => {
    const accessToken = useAuthStore(state => state.accessToken);
    return (
        <header className="top-bar">
            <img className="icon" src={logo} alt="#" />
            <h1>Carlés Terrenos</h1>
            {accessToken ? <NavBar /> : <></>}
        </header>
    );
};

export default Header;
