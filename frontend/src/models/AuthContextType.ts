import Auth from "./Auth";

type AuthContextType = {
    auth: Auth,
    setAuth: React.Dispatch<React.SetStateAction<Auth>>;
}

export default AuthContextType;