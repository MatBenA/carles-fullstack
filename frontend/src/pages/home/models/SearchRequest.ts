export interface SearchRequest {
    minPrice: number | undefined;
    maxPrice: number | undefined;
    address: string | undefined;
    businessEvaluation?: string;
    section: string | undefined;
    zone: string | undefined;
    agency: string | undefined;
    particular: string | undefined;
    classification: string | undefined;
    managerEmail: string | undefined;
    title: boolean | undefined;
    rescinded: boolean | undefined;
}
