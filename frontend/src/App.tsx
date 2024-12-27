import Layout from "./components/Layout";
import { Routes, Route } from "react-router-dom";
import Login from "./components/Login";
import Unauthorized from "./components/Unauthorized";
import Missing from "./components/Missing";
import RequireAuth from "./components/RequireAuth";
import LandSurveys from "./pages/home/components/LandSurveys";
import LandSurveyDetails from "./pages/LandSurveyDetails/LandSurveyDetails";
import Users from "./pages/UserList/Users";
import UserDetails from "./components/UserDetails";
import UserCreate from "./components/UserCreate";
import LandSurveyCreate from "./pages/LandSurveyForm/LandSurveyCreate";
import Settings from "./pages/settings/Settings";
import StatisticTable from "./pages/statisticTable/StatisticTable";
import StatisticReport from "./pages/statisticReport/StatisticReport";
import Notifications from "./pages/notifications/Notifications";
import UpdateReportDetail from "./pages/updateReportDetail/UpdateReportDetail";
import UpdateReportTable from "./pages/updateReportTable/UpdateReportTable"

const ROLES = {
    USER: "USER",
    EDITOR: "EDITOR",
    ADMIN: "ADMIN",
};

function App() {

    return (
        <Routes>
            <Route path="/" element={<Layout />}>
                {/* Rutas publicas */}
                <Route path="login" element={<Login />} />

                <Route path="unauthorized" element={<Unauthorized />} />

                <Route
                    element={
                        <RequireAuth allowedRoles={[ROLES.USER, ROLES.ADMIN]} />
                    }
                >
                    <Route path="/" element={<LandSurveys />} />
                </Route>

                <Route
                    element={
                        <RequireAuth allowedRoles={[ROLES.USER, ROLES.ADMIN]} />
                    }
                >
                    <Route path="/land-surveys" element={<LandSurveys />} />
                </Route>

                <Route
                    element={
                        <RequireAuth allowedRoles={[ROLES.USER, ROLES.ADMIN]} />
                    }
                >
                    <Route
                        path="/land-surveys/detail"
                        element={<LandSurveyDetails />}
                    />
                </Route>

                <Route
                    element={
                        <RequireAuth allowedRoles={[ROLES.USER, ROLES.ADMIN]} />
                    }
                >
                    <Route
                        path="/land-surveys/create"
                        element={<LandSurveyCreate />}
                    />
                </Route>

                <Route element={<RequireAuth allowedRoles={[ROLES.ADMIN]} />}>
                    <Route path="/users" element={<Users />} />
                </Route>

                <Route
                    element={
                        <RequireAuth allowedRoles={[ROLES.ADMIN]} />
                    }
                >
                    <Route path="/users/detail" element={<UserDetails />} />
                </Route>

                <Route element={<RequireAuth allowedRoles={[ROLES.ADMIN]} />}>
                    <Route path="/users/create" element={<UserCreate />} />
                </Route>

                <Route element={<RequireAuth allowedRoles={[ROLES.ADMIN]} />}>
                    <Route path="/settings" element={<Settings />} />
                </Route>

                <Route element={<RequireAuth allowedRoles={[ROLES.ADMIN, ROLES.USER]} />}>
                    <Route path="/statistics" element={<StatisticTable />} />
                </Route>

                <Route element={<RequireAuth allowedRoles={[ROLES.ADMIN, ROLES.USER]} />}>
                    <Route path="/statistics/report" element={<StatisticReport />} />
                </Route>
                
                <Route element={<RequireAuth allowedRoles={[ROLES.ADMIN, ROLES.USER]} />}>
                    <Route path="/notifications" element={<Notifications />} />
                </Route>
                
                <Route element={<RequireAuth allowedRoles={[ROLES.ADMIN, ROLES.USER]} />}>
                    <Route path="/update-reports" element={<UpdateReportTable />} />
                </Route>
                
                <Route element={<RequireAuth allowedRoles={[ROLES.ADMIN, ROLES.USER]} />}>
                    <Route path="/update-reports/details" element={<UpdateReportDetail />} />
                </Route>

                <Route path="*" element={<Missing />} />
            </Route>
        </Routes>
    );
}

export default App;
