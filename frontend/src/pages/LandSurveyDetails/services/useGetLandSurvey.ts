import { useEffect, useState } from "react";
import RawLandSurvey, { emptyRawLandSurvey } from "../../../models/RawLandSurvey";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";

const useGetLandSurvey = (id: number): RawLandSurvey => {
    const axiosPrivate = useAxiosPrivate();
    const [LandSurvey, setLandsurvey] = useState<RawLandSurvey>(emptyRawLandSurvey());

    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const getLandSurvey = async () => {
            try {
                const response = await axiosPrivate.get(`/land-surveys/${id}`, {
                    signal: controller.signal,
                });

                if (isMounted && response.data != null) {
                    setLandsurvey(response.data);
                }
            } catch (error) {
                console.error(error);
            }
        };

        getLandSurvey();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, [axiosPrivate, id]);

    return LandSurvey;
};

export default useGetLandSurvey;
