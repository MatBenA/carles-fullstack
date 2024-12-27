import { emptyUpdateReport, UpdateReport } from "../model/UpdateReport";

const updateReportCalcTotal = (reportList: UpdateReport[]): UpdateReport => {
    return reportList.reduce((prevReport, currentReport) => {
        return {
            user: { value: "Total", label: "Total" },
            totalOwners: prevReport.totalOwners + currentReport.totalOwners,
            totalAgencies:
                prevReport.totalAgencies + currentReport.totalAgencies,
            totalReds: prevReport.totalReds + currentReport.totalReds,
            total: prevReport.total + currentReport.total,
        };
    }, emptyUpdateReport());
};

export default updateReportCalcTotal;
