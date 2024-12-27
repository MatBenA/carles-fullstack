import Auth from "./Auth";

type AuthContextType = {
    auth: Auth,
    setAuth: (auth: Auth | null) => void; 
}

export default AuthContextType;