import axios, { AxiosInstance } from "axios";
import { SearchRequest } from "../models/SearchRequest";
import { searchRequestAdapter } from "../adapters/searchRequestAdapter";
import LandSurvey from "../../../models/LandSurvey";
import adaptLandSurvey from "../adapters/landSurveysAdapter";

const fetchLandSurveys = async (
    axiosPrivate: AxiosInstance,
    controller: AbortController,
    searchRequest: SearchRequest,
) => {
    try {
        const cleanSearchRequest = searchRequestAdapter(searchRequest)
        const response = await axiosPrivate.get("/land-surveys", {
            signal: controller.signal,
            params: cleanSearchRequest
        });
        console.log(response.data);
        const landSurveys: Array<LandSurvey> = adaptLandSurvey(
            response.data.landSurveys
        );
        return landSurveys;
    } catch (err) {
        if (axios.isCancel(err)) {
            return
        } else {
            console.error(err);
        }

        return [];
    } finally {
        controller.abort();
    }
};

export default fetchLandSurveys;