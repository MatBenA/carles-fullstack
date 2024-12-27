import { useLocation, useNavigate } from "react-router-dom";
import { UpdateReportGroup } from "../updateReportTable/model/UpdateReport";
import "./styles/update-report.css";
import updateReportCalcTotal from "../updateReportTable/utilities/updateReportCalcTotal";

const UpdateReportDetail = () => {
    const updateReport: UpdateReportGroup = useLocation().state;
    const updateReportTotal = updateReportCalcTotal(updateReport.reportList);
    const navigate = useNavigate();

    return (
        <section className="container update-report">
            <div>
                <button className="gold-btn" onClick={() => navigate("/update-reports")}>Volver</button>
            </div>
            <table>
                <thead>
                    <tr>
                        <th className="main-cell">
                            Fecha: {updateReport.date}
                        </th>
                        {updateReport.reportList.map((report) => (
                            <th>{report.user.label}</th>
                        ))}
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th className="main-cell">
                            <b>Propietarios</b> con mas Dias
                            <b> (Comprar o Autorizar)</b>
                        </th>
                        {updateReport.reportList.map((report) => (
                            <td>{report.totalOwners}</td>
                        ))}
                        <th>{updateReportTotal.totalOwners}</th>
                    </tr>
                    <tr>
                        <th className="main-cell">
                            <b>Inmobiliarias e intermediarios</b> con más de 120
                            días <b>(Comprar o conseguir cliente)</b>
                        </th>
                        {updateReport.reportList.map((report) => (
                            <td>{report.totalAgencies}</td>
                        ))}
                        <th>{updateReportTotal.totalAgencies}</th>
                    </tr>
                    <tr>
                        <th className="main-cell">
                            <b>Inmuebles en rojo</b> con problemas de papeles o
                            de actitud con más de un año{" "}
                            <b>
                                (Mejoraron la situación de papeles o su
                                actitud?)
                            </b>
                        </th>
                        {updateReport.reportList.map((report) => (
                            <td>{report.totalReds}</td>
                        ))}
                        <th>{updateReportTotal.totalReds}</th>
                    </tr>
                    <tr>
                        <th className="main-cell">
                            <b>Totales</b>
                        </th>
                        {updateReport.reportList.map((report) => (
                            <th>{report.total}</th>
                        ))}
                        <th>{updateReportTotal.total}</th>
                    </tr>
                </tbody>
            </table>
        </section>
    );
};

export default UpdateReportDetail;
