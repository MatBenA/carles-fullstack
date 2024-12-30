import { Navigate, Outlet, useLocation } from "react-router-dom";
import useAuth from "../hooks/useAuth";
import Role from "../models/Role";

type RequireAuthProp = {allowedRoles: string[]}

const RequireAuth = ({allowedRoles}: RequireAuthProp) => {
    const { auth } = useAuth();
    const location = useLocation();

    return (
        auth?.roles?.find((role: Role) => allowedRoles?.includes(role.name))
            ? <Outlet />
            : auth?.email
                ? <Navigate to="/unauthorized" state={{ from: location }} replace />
                : <Navigate to="/login" state={{ from: location }} replace />
    )
}

export default RequireAuth;