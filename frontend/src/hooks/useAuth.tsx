import { useContext } from "react";
import AuthContext from "../context/AuthProvider";
import AuthContextType from "../models/AuthContextType";

const useAuth = (): AuthContextType => {
    const context = useContext(AuthContext);
    if (!context) {
        throw new Error("useAuth must be used within an AuthProvider");
    }
    return context
};

export default useAuth;
