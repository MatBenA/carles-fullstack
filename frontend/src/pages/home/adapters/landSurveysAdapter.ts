import LandSurvey from "../../../models/LandSurvey";
import Assessment from "../../../models/Assessment";
import RawLandSurvey from "../../../models/RawLandSurvey"
import {
    getDaysSincePrice,
    averageAssessment,
    maxPrice,
    minPrice,
    assessmentDeviation,
    businessEvaluation,
} from "../../../utilities/landSurveyCalcs";

export default function adaptLandSurvey(rawLandSurveys: RawLandSurvey[]): LandSurvey[] {
    return rawLandSurveys.map((survey) => {
        const assessmentList: Array<Assessment> = survey.assessmentList;

        const daysSincePriceVerification = getDaysSincePrice(
            new Date(survey.priceVerificationDate)
        );

        const averageAssessmentUsd = averageAssessment(assessmentList);

        const pricePerSquareMeter = Math.round(survey.price / survey.surface);

        const maxAssessment = maxPrice(assessmentList);

        const minAssessment = minPrice(assessmentList);

        const deviation = assessmentDeviation(assessmentList);

        const evaluation = businessEvaluation(
            survey.price,
            averageAssessmentUsd
        );

        const assessmentsPerSquareMeterUsd = Math.round(
            averageAssessmentUsd / survey.surface
        );

        survey.date = new Date(survey.date).toLocaleDateString("es-AR");

        survey.priceVerificationDate = new Date(
            survey.priceVerificationDate
        ).toLocaleDateString("es-AR");

        survey.reassessmentDate = new Date(
            survey.reassessmentDate
        ).toLocaleDateString("es-AR");

        const landSurvey: LandSurvey = {
            ...survey,
            daysSincePriceVerification,
            averageAssessmentUsd,
            pricePerSquareMeter,
            maxPrice: maxAssessment,
            minPrice: minAssessment,
            deviation,
            businessEvaluation: evaluation,
            assessmentsPerSquareMeterUsd,
        };

        return landSurvey;
    });
}
