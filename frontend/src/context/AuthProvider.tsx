import { createContext, useState } from "react";
import AuthContextType from "../models/AuthContextType";
import Auth from "../models/Auth";

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
    const [auth, setAuth] = useState<Auth>({
        email: "",
        roles: [],
        accessToken: "",
    });

    const contextValue: AuthContextType = { auth, setAuth };

    return (
        <AuthContext.Provider value={contextValue}>
            {children}
        </AuthContext.Provider>
    );
};

export default AuthContext;
