import { axiosPrivate } from "../api/axios";
import refreshToken from "./refreshToken";

const axiosPrivateX = () => {
    const requestIntercept = axiosPrivate.interceptors.request.use(
        (config) => {
            if (!config.headers["Authorization"]) {
                config.headers[
                    "Authorization"
                ] = `Bearer ${localStorage.getItem("accessToken")}`;
            }
            console.log(localStorage.getItem("accessToken"))
            return config;
        },
        (error) => Promise.reject(error)
    );

    const responseIntercept = axiosPrivate.interceptors.response.use(
        (response) => response,

        async (error) => {
            const prevRequest = error?.config;
            if (error.response?.status === 403 && !prevRequest?.sent) {
                prevRequest.sent = true;
                const accessToken = await refreshToken();

                prevRequest.headers["Authorization"] = `Bearer ${accessToken}`;
                return prevRequest;
            }

            return Promise.reject(error);
        }
    );

    axiosPrivate.interceptors.request.eject(requestIntercept);
    axiosPrivate.interceptors.response.eject(responseIntercept);

    return axiosPrivate;
};

export default axiosPrivateX;
