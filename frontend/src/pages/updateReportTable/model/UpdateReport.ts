import { emptyInputOption, InputOption } from "../../../models/InputOption";

export interface UpdateReportGroup {
    date: string;
    reportList: UpdateReport[];
}

export interface UpdateReport {
    user: InputOption;
    totalOwners: number;
    totalAgencies: number;
    totalReds: number;
    total: number;
}

export const emptyUpdateReportGroup = (): UpdateReportGroup => {
    return {
        date: new Date().toLocaleDateString("es-AR"),
        reportList: [emptyUpdateReport()],
    };
};

export const emptyUpdateReport = (): UpdateReport => {
    return {
        user: emptyInputOption(),
        totalOwners: 0,
        totalAgencies: 0,
        totalReds: 0,
        total: 0,
    };
};
