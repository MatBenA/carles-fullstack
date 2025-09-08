import useAuthStore from "./useAuthStore";

const useIsAdmin = (): boolean => {
  const { roleSet } = useAuthStore((state) => state);
  return !!roleSet?.find((role) => role.name === "ADMIN");
};

export default useIsAdmin;
