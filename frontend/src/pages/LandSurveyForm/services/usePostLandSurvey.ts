import { useEffect, useState } from "react";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import { AxiosResponse } from "axios";
import RawLandSurvey from "../../../models/RawLandSurvey"

export const usePostLandSurvey = (rawLandsurvey: RawLandSurvey) => {
    const axiosPrivate = useAxiosPrivate();
    const [data, setData] = useState({});

    useEffect(() => {
        let isMounted: boolean = true;
        const controller: AbortController = new AbortController();

        const postLandSurvey = async () => {
            try {
                const response: AxiosResponse = await axiosPrivate.post(
                    "/landsurveys/create",
                    rawLandsurvey,
                    { signal: controller.signal }
                );

                if (isMounted && response.data) {
                    console.log(response);
                    setData(response.data);
                }
            } catch (error) {
                console.error(error);
            }
        };

        postLandSurvey();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, [axiosPrivate, rawLandsurvey]);

    return data;
};
