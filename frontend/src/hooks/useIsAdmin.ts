import useAuth from "./useAuth";

const useIsAdmin = (): boolean => {
    const {auth} = useAuth();
    return !!auth?.roles?.find((role) => role.name === "ADMIN");
}

export default useIsAdmin;