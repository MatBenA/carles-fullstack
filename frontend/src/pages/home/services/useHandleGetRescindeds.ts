import { toast } from "sonner";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import adaptLandSurvey from "../adapters/landSurveysAdapter";

const useHandleGetRescindeds = (setLandSurveys) => {
    const axiosPrivate = useAxiosPrivate();
    const controller = new AbortController();
    const handleGetRescindeds = async () => {
        const getRescindeds = axiosPrivate.get("/land-surveys/rescinded-list", {
            signal: controller.signal,
        });

        toast.promise(getRescindeds, {
            loading: "Cargando...",
            success: "Exito!",
            error: "Error, Y ahora que paso?",
        })

        try {
            const result = await getRescindeds;
            console.log(result);
            setLandSurveys(adaptLandSurvey(result.data));
        } catch (error) {
            console.error(error);
        }
    };
    return handleGetRescindeds;
};

export default useHandleGetRescindeds;
