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
import UpdateReportTable from "./pages/updateReportTable/UpdateReportTable";

const ROLES = {
  USER: "USER",
  EDITOR: "EDITOR",
  ADMIN: "ADMIN",
};

function App() {
  return (
    <Routes>
      <Route path="/" element={<Layout />}>
        {/* Public */}
        <Route path="login" element={<Login />} />
        <Route path="unauthorized" element={<Unauthorized />} />

        {/* USER + ADMIN */}
        <Route
          element={<RequireAuth allowedRoles={[ROLES.USER, ROLES.ADMIN]} />}
        >
          <Route index element={<LandSurveys />} />

          <Route path="land-surveys">
            <Route index element={<LandSurveys />} />
            <Route path="detail" element={<LandSurveyDetails />} />
            <Route path="create" element={<LandSurveyCreate />} />
          </Route>

          <Route path="statistics">
            <Route index element={<StatisticTable />} />
            <Route path="report" element={<StatisticReport />} />
          </Route>

          <Route path="notifications" element={<Notifications />} />

          <Route path="update-reports">
            <Route index element={<UpdateReportTable />} />
            <Route path="details" element={<UpdateReportDetail />} />
          </Route>
        </Route>

        {/* ADMIN */}
        <Route element={<RequireAuth allowedRoles={[ROLES.ADMIN]} />}>
          <Route path="users">
            <Route index element={<Users />} />
            <Route path="detail" element={<UserDetails />} />
            <Route path="create" element={<UserCreate />} />
          </Route>
          <Route path="settings" element={<Settings />} />
        </Route>

        {/* Fallback */}
        <Route path="*" element={<Missing />} />
      </Route>
    </Routes>
  );
}

export default App;
