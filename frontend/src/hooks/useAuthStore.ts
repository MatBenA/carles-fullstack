import { create } from "zustand";
import { persist } from "zustand/middleware";

type AuthState = {
  email: string | null;
  roleSet: Role[] | null;
  accessToken: string | null;
  setAuth: (auth: AuthPayload) => void;
  clearAuth: () => void;
};

type AuthPayload = {
  email: string;
  roleSet: Role[];
  accessToken: string;
};

type Role = {
  id: number;
  name: string;
  permissionSet: Permission[];
};

type Permission = {
  id: number;
  permission: string;
};

const useAuthStore = create(
  persist<AuthState>(
    (set) => ({
      email: "",
      roleSet: [],
      accessToken: "",
      setAuth: ({ email, roleSet, accessToken }) =>
        set({ email, roleSet, accessToken }),
      clearAuth: () => set({ email: null, roleSet: null, accessToken: null }),
    }),
    { name: "auth-storage" }
  )
);

export default useAuthStore;
