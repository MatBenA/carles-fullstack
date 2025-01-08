import { SearchRequest } from "../models/SearchRequest";

export function searchRequestAdapter({
    minPrice,
    maxPrice,
    address,
    businessEvaluation,
    section,
    zone,
    agency,
    particular,
    classification,
    managerEmail,
    title,
    rescinded,
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
        address,
        businessEvaluation,
        section,
        zone,
        agency,
        particular,
        classification,
        managerEmail,
        title,
        rescinded
    };
}
