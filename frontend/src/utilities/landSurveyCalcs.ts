import Assessment from "../models/Assessment";

export function getDaysSincePrice(priceVerificationDate: Date): number {
    const currentDate = new Date().getTime();
    const verficationDate = priceVerificationDate.getTime();
    const diffTime = currentDate - verficationDate;
    const dias = Math.floor(diffTime / (1000 * 60 * 60 * 24));
    return dias;
}

//TODO FIX THIS FUNCTION
export function averageAssessment(assessmentList: Assessment[], rePricing: number, dollarRate: number): number {
    return (
        assessmentList.reduce(
            (total: number, assessment) => 
                total + (assessment.currency == "ARS" ? assessment.price / dollarRate : assessment.price),
            0
        ) / assessmentList.length * (1 + rePricing / 100)
    );
}

export function maxPrice(assessmentList: Assessment[], dollarRate: number): number {
    return Math.max(...assessmentList.map((assessment) => assessment.currency == "ARS" ? (assessment.price / dollarRate) : assessment.price));
}

export function minPrice(assessmentList: Assessment[], dollarRate: number): number {
    return Math.min(...assessmentList.map((assessment) => assessment.currency == "ARS" ? (assessment.price / dollarRate) : assessment.price));
}

export function assessmentDeviation(assessmentList: Assessment[], dollarRate: number): number {
    return Math.round(
        (maxPrice(assessmentList, dollarRate) / minPrice(assessmentList, dollarRate) - 1) * 100
    );
}

export function businessEvaluation(
    pretendedPrice: number,
    priceCurrency: string,
    assessmentAveragePrice: number, 
    dollarRate: number
): number {
    return (
        ((priceCurrency === "ARS" ? pretendedPrice / dollarRate : pretendedPrice)
        / assessmentAveragePrice - 1) * 100
    );
}

export function priceXMeterSquared(
    price: number, 
    currency:string, 
    surface: number, 
    dollarRate: number) {
    return Math.round(
        (currency === "ARS" ? price / dollarRate : price) 
    / surface);
}
