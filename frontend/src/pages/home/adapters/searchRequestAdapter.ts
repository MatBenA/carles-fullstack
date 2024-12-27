import { SearchRequest } from "../models/SearchRequest";

export function searchRequestAdapter({
    minPrice,
    maxPrice,
    businessEvaluation,
    section,
    zone,
    agency,
    particular,
    classification,
    title,
}: SearchRequest): SearchRequest {
    if (minPrice === undefined || isNaN(minPrice)) {
        minPrice = 0;
    }
    if (maxPrice === undefined || isNaN(maxPrice)) {
        maxPrice = 0;
    }

    return {
        minPrice,
        maxPrice,
        businessEvaluation,
        section,
        zone,
        agency,
        particular,
        classification,
        title,
    };
}
