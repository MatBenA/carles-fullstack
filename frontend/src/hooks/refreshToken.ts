import axios from "../api/axios";

const refreshToken = () => {
    const refresh = async () => {
        const response = await axios.get("/refresh", {
            withCredentials: true,
        });

        localStorage.setItem("accessToken", response.data.accessToken);

        return response.data.accessToken;
    };

    return refresh;
};

export default refreshToken;
