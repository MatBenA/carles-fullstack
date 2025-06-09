import Assessment from "../models/Assessment";

export function getDaysSincePrice(priceVerificationDate: Date): number {
    const currentDate = new Date().getTime();
    const verficationDate = priceVerificationDate.getTime();
    const diffTime = currentDate - verficationDate;
    const dias = Math.floor(diffTime / (1000 * 60 * 60 * 24));
    return dias;
}

//TODO FIX THIS FUNCTION
export function averageAssessment(assessmentList: Assessment[]): number {
    return (
        assessmentList.reduce(
            (total: number, assessment) => 
                total + (assessment.currency == "ARS" ? assessment.price / 1165 : assessment.price),
            0
        ) / assessmentList.length
    );
}

export function maxPrice(assessmentList: Assessment[]): number {
    return Math.max(...assessmentList.map((assessment) => assessment.currency == "ARS" ? (assessment.price / 1165) : assessment.price));
}

export function minPrice(assessmentList: Assessment[]): number {
    return Math.min(...assessmentList.map((assessment) => assessment.currency == "ARS" ? (assessment.price / 1165) : assessment.price));
}

export function assessmentDeviation(assessmentList: Assessment[]): number {
    return Math.round(
        (maxPrice(assessmentList) / minPrice(assessmentList) - 1) * 100
    );
}

export function businessEvaluation(
    pretendedPrice: number,
    assessmentAveragePrice: number
): number {
    return ((pretendedPrice / assessmentAveragePrice - 1) * 100);
}

export function priceXMeterSquared(price: number, surface: number) {
    return Math.round(price / surface);
}
