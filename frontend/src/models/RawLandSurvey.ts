import Assessment, { emptyAssessment } from "./Assessment"
import { emptyInputOption, InputOption } from "./InputOption";

export default interface RawLandSurvey {
    id: number;
    date: string;
    address: string;
    corner: boolean;
    unworkable: boolean;
    title: boolean;
    titleSituation: string;
    measurements: string;
    surface: number;
    priceVerificationDate: string;
    reassessmentDate: string;
    observation: string;
    isRescinded: boolean;
    isArchived: boolean;
    surveyor: InputOption;
    manager: InputOption;
    fileType: string;
    roadType: string;
    locality: string;
    section: string;
    zone: string;
    source: string;
    classification: string;
    agency: string;
    particular: string;
    contact: string;
    price: number;
    currency: string;
    folder: string;
    assessmentList: Assessment[];
}

export const emptyRawLandSurvey = (): RawLandSurvey => {
    return{
    id: 0,
    date: "",
    address: "",
    corner: false,
    unworkable: false,
    title: false,
    titleSituation: "",
    measurements: "",
    surface: 0,
    priceVerificationDate: "",
    reassessmentDate: "",
    observation: "",
    isRescinded: false,
    isArchived: false,
    surveyor: emptyInputOption(),
    manager: emptyInputOption(),
    fileType: "",
    roadType: "",
    locality: "",
    section: "",
    zone: "",
    source: "",
    classification: "",
    agency: "",
    particular: "",
    contact: "",
    price: 0,
    currency: "",
    folder: "",
    assessmentList: [emptyAssessment()],}
}