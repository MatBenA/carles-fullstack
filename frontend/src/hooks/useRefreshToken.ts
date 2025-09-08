import axios from "../api/axios";
import useAuthStore from "./useAuthStore";

const useRefreshToken = () => {
  const { setAuth } = useAuthStore((state) => state);

  const refresh = async () => {
    const response = await axios.get("/refresh", {
      withCredentials: true,
    });

    const { email, roleSet, accessToken } = response.data;

    setAuth({
      email: email,
      roleSet: roleSet,
      accessToken: accessToken,
    });

    return accessToken;
  };

  return refresh;
};

export default useRefreshToken;
