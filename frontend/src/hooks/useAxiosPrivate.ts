import { useEffect } from "react";
import { axiosPrivate } from "../api/axios";
import useAuthStore from "./useAuthStore";
import { useNavigate } from "react-router-dom";
import { AxiosError } from "axios";
import { ApiErrorResponse } from "../models/ApiErrorResponse";

const useAxiosPrivate = () => {
  const navigate = useNavigate();
  const { accessToken, clearAuth } = useAuthStore((state) => state);

  useEffect(() => {
    const requestIntercept = axiosPrivate.interceptors.request.use(
      (config) => {
        if (!config.headers["Authorization"]) {
          config.headers["Authorization"] = `Bearer ${accessToken}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );

    const responseIntercept = axiosPrivate.interceptors.response.use(
      (response) => response,
      async (error: AxiosError<ApiErrorResponse>) => {
        if (error.response?.data.trace.includes("TokenExpiredException")) {
          clearAuth();
        navigate("/login", { replace: true });
        }

        return Promise.reject(error);
      }
    );

    return () => {
      axiosPrivate.interceptors.request.eject(requestIntercept);
      axiosPrivate.interceptors.response.eject(responseIntercept);
    };
  }, [accessToken, clearAuth, navigate]);

  return axiosPrivate;
};

export default useAxiosPrivate;
