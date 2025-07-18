import Assessment, { emptyAssessment } from "./Assessment";
import { emptyInputOption, InputOption } from "./InputOption";

export default interface LandSurvey {
    address: string;
    agency: string;
    assessmentList?: Array<Assessment>;
    assessmentsPerSquareMeterUsd: number;
    averageAssessmentUsd: number;
    businessEvaluation: number;
    classification: string;
    contact: string;
    corner: boolean;
    unworkable: boolean;
    currency: string;
    date: string;
    daysSincePriceVerification: number;
    deviation: number;
    fileType: string;
    id: number;
    isArchived: boolean;
    isRescinded: boolean;
    locality: string;
    manager: InputOption;
    maxPrice: number;
    measurements: string;
    minPrice: number;
    observation: string;
    particular: string;
    price: number;
    pricePerSquareMeter: number;
    priceVerificationDate: string;
    reassessmentDate: string;
    roadType: string;
    section: string;
    source: string;
    surface: number;
    surveyor: InputOption;
    title: boolean;
    titleSituation: string;
    zone: string;
    folder: string;
}

export const landSurveyEmpty = (): LandSurvey => {
    return {
        address: "",
        agency: "",
        assessmentList: [emptyAssessment()],
        assessmentsPerSquareMeterUsd: 0,
        averageAssessmentUsd: 0,
        businessEvaluation: 0,
        classification: "",
        contact: "",
        corner: false,
        unworkable: false,
        currency: "",
        date: "",
        daysSincePriceVerification: 0,
        deviation: 0,
        fileType: "",
        id: 0,
        isArchived: false,
        isRescinded: false,
        locality: "",
        manager: emptyInputOption(),
        maxPrice: 0,
        minPrice: 0,
        measurements: "",
        observation: "",
        particular: "",
        price: 0,
        pricePerSquareMeter: 0,
        priceVerificationDate: "",
        reassessmentDate: "",
        roadType: "",
        section: "",
        source: "",
        surface: 0,
        surveyor: emptyInputOption(),
        title: false,
        titleSituation: "",
        zone: "",
        folder: ""
    };
};
