import { useContext, useState } from "react";
import AuthContext from "../../../context/AuthProvider";
import "../styles/land-surveys.css";
import LandSurveyFilters from "./LandSurveyFilters";
import LandSurvey, { landSurveyEmpty } from "../../../models/LandSurvey.ts";
import LandSurveyTable from "../../../components/Table.tsx";

const LandSurveys = () => {
    useContext(AuthContext);
    const [landSurveys, setLandSurveys] = useState<LandSurvey[]>([
        landSurveyEmpty(),
    ]);
    
    

    return (
        <section className="surveys">
            <LandSurveyFilters setLandSurveys={setLandSurveys} />
            <LandSurveyTable landSurveys={landSurveys} />
        </section>
    );
};

export default LandSurveys;
