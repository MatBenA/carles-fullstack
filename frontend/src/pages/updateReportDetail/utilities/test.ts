import { UpdateReportGroup } from "../../updateReportTable/model/UpdateReport";

const updateReportGroupTest: UpdateReportGroup = {
    date: new Date().toLocaleDateString("es-AR"),
    reportList: [
        {
            user: { value: "jonathan@carles.com", label: "Jonathan" },
            totalOwners: 6,
            totalAgencies: 16,
            totalReds: 3,
            total: 25,
        },
        {
            user: { value: "jonathan@carles.com", label: "Luis" },
            totalOwners: 6,
            totalAgencies: 16,
            totalReds: 3,
            total: 25,
        },
        {
            user: { value: "jonathan@carles.com", label: "Victor" },
            totalOwners: 6,
            totalAgencies: 16,
            totalReds: 3,
            total: 25,
        },
        {
            user: { value: "jonathan@carles.com", label: "Emanuel" },
            totalOwners: 6,
            totalAgencies: 16,
            totalReds: 3,
            total: 25,
        },
        {
            user: { value: "jonathan@carles.com", label: "Guillermo" },
            totalOwners: 6,
            totalAgencies: 16,
            totalReds: 3,
            total: 25,
        },
        {
            user: { value: "jonathan@carles.com", label: "Juanjo" },
            totalOwners: 6,
            totalAgencies: 16,
            totalReds: 3,
            total: 25,
        },
        {
            user: { value: "jonathan@carles.com", label: "Agustin" },
            totalOwners: 6,
            totalAgencies: 16,
            totalReds: 3,
            total: 25,
        },
    ],
};

export default updateReportGroupTest;