import { Navigate, Outlet, useLocation } from "react-router-dom";
import useAuthStore from "../hooks/useAuthStore";

type RequireAuthProp = {allowedRoles: string[]}

const RequireAuth = ({allowedRoles}: RequireAuthProp) => {
    const { roleSet, email } = useAuthStore(state => state);
    const location = useLocation();

    return (
        roleSet?.find((role) => allowedRoles?.includes(role.name))
            ? <Outlet />
            : email
                ? <Navigate to="/unauthorized" state={{ from: location }} replace />
                : <Navigate to="/login" state={{ from: location }} replace />
    )
}

export default RequireAuth;