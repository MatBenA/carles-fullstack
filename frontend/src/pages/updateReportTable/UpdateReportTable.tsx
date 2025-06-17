import { useEffect, useState } from "react";
import {
    UpdateReportGroup
} from "./model/UpdateReport";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import updateReportCalcTotal from "./utilities/updateReportCalcTotal";
import { useNavigate } from "react-router-dom";
import { toast } from "sonner";
import updateReportGroupTest from "../updateReportDetail/utilities/test";
import useIsAdmin from "../../hooks/useIsAdmin";

const UpdateReports = () => {
    const [updateReportGroups, setUpdateReportGroups] = useState<
        UpdateReportGroup[]
    >([updateReportGroupTest]);
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const isAdmin: boolean = useIsAdmin();

    useEffect(() => {
        const controller = new AbortController();
        let isMounted = true;

        const getUpdateReportGroups = async () => {
            try {
                const response = await axiosPrivate.get("/update-reports", {
                    signal: controller.signal,
                });
                if (isMounted) {
                    console.log(response)
                    setUpdateReportGroups(response.data);
                }
            } catch (error) {
                console.error(error);
            }
        };

        getUpdateReportGroups();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, [axiosPrivate]);

    const handleGenerateReport = async () => {
        const controller = new AbortController();
        const generateReport = axiosPrivate.post("/update-reports/create", null, {
            signal: controller.signal,
        });

        toast.promise(generateReport, {
            loading: "Generando nuevo reporte...",
            success: "Exito! Nuevo reporte creado",
            error: "Error! ay no...",
        });

        try {
            const response = await generateReport;
            navigate("/update-reports/details", { state: response.data });
        } catch (error) {
            console.error(error);
        }
    };
    

    return (
        <section className="container">
            <div>
            <h3>Actualizaciones</h3>
                {isAdmin ? (
                    <button className="green-btn" onClick={handleGenerateReport}>
                        Generar Nuevo Reporte
                    </button>
                ) : (
                    <></>
                )}
            </div>
            <table>
                <thead>
                    <tr>
                        <th></th>
                        <th>Fecha</th>
                        <th>Total Propietarios Vencidos</th>
                        <th>Total inmobiliaria e intermediarios Vencidos</th>
                        <th>Total inmuebles en rojo</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    {updateReportGroups.map((reportGroup) => (
                        <tr>
                            <td>
                                <button
                                    onClick={() =>
                                        navigate("/update-reports/details", {
                                            state: reportGroup,
                                        })
                                    }
                                >
                                    Detalles
                                </button>
                            </td>
                            <td>{reportGroup.date}</td>
                            <td>
                                {
                                    updateReportCalcTotal(
                                        reportGroup.reportList
                                    ).totalOwners
                                }
                            </td>
                            <td>
                                {
                                    updateReportCalcTotal(
                                        reportGroup.reportList
                                    ).totalAgencies
                                }
                            </td>
                            <td>
                                {
                                    updateReportCalcTotal(
                                        reportGroup.reportList
                                    ).totalReds
                                }
                            </td>
                            <td>
                                {
                                    updateReportCalcTotal(
                                        reportGroup.reportList
                                    ).total
                                }
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </section>
    );
};

export default UpdateReports;
