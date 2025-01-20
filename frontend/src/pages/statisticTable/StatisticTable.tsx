import "../../assets/styles/utils.css";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import useAxiosPrivate from "../../hooks/useAxiosPrivate.tsx";
import { Statistic } from "../statisticReport/models/Statistic.ts";
import { toast } from "sonner";
import useIsAdmin from "../../hooks/useIsAdmin.ts";

const StatisticTable = () => {
    const axiosPrivate = useAxiosPrivate();
    const navigate = useNavigate();
    const isAdmin: boolean = useIsAdmin();

    const [statisticList, setStatisticList] = useState<Statistic[]>();

    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();
        const getStatistics = async () => {
            try {
                const result = await axiosPrivate.get("/statistics", {
                    signal: controller.signal,
                });

                if (isMounted && result.data) {
                    const statisticsResponse = result.data;
                    setStatisticList(statisticsResponse);
                }
            } catch (error) {
                console.error(error);
            }
        };

        getStatistics();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, [axiosPrivate]);

    const handleClick = async () => {
        const controller = new AbortController();

        const generateReport = axiosPrivate.post("/statistics/generate", null, {
            signal: controller.signal,
        });

        toast.promise(generateReport, {
            loading: "Generando Nuevo Reporte...",
            success: "Exito! Reporte Generado",
            error: "Error! Mati veniii",
        });

        try {
            const result = await generateReport;
            navigate("/statistics/report", { state: result.data });
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <section className="container">
            <div>
            <h3>Estadisticas</h3>
                {isAdmin ? (
                    <button className="green-btn" onClick={handleClick}>
                        Generar Nuevo Reporte
                    </button>
                ) : (
                    <></>
                )}
            </div>
            <table className="custom-table">
                <thead>
                    <tr>
                        <th>Fecha</th>
                        <th>Total Relevamientos</th>
                        <th>Valor Medio</th>
                        <th>Carles</th>
                        <th>Inmobiliarias</th>
                        <th>Propietarios</th>
                        <th>Carles Rojos</th>
                        <th>Rojos</th>
                        <th>Negros</th>
                        <th>Verdes</th>
                        <th>-90</th>
                        <th>-180</th>
                        <th>Mas</th>
                        <th>Posadas</th>
                        <th>Garupa</th>
                        <th>Calendaria</th>
                        <th>Con Titulo</th>
                        <th>Sin Titulo</th>
                        <th>No trabajable</th>
                        <th>Accion</th>
                    </tr>
                </thead>
                <tbody>
                    {statisticList?.map((statistic) => (
                        <tr key={statistic.id}>
                            <td>
                                {new Date(
                                    statistic.creationDate
                                ).toLocaleDateString("es-AR")}
                            </td>
                            <td>{statistic.totalLandSurveys}</td>
                            <td>{statistic.averageValue}</td>
                            <td>{statistic.totalCarles}</td>
                            <td>{statistic.totalAgencies}</td>
                            <td>{statistic.totalOwners}</td>
                            <td>{statistic.totalCarlesRed}</td>
                            <td>{statistic.totalReds}</td>
                            <td>{statistic.totalNormals}</td>
                            <td>{statistic.totalGreens}</td>
                            <td>{statistic.belowMinLimit}</td>
                            <td>{statistic.belowMaxLimit}</td>
                            <td>{statistic.overLimits}</td>
                            <td>{statistic.posadasTotal}</td>
                            <td>{statistic.garupaTotal}</td>
                            <td>{statistic.candelariaTotal}</td>
                            <td>{statistic.WithTitle}</td>
                            <td>
                                {statistic.totalLandSurveys -
                                    statistic.WithTitle}
                            </td>
                            <td>{statistic.totalUnworkable}</td>
                            <td>
                                <Link
                                    to="/statistics/report"
                                    state={statistic}
                                    onClick={() => window.scrollTo({ top: 0 })}
                                >
                                    <button>Detalle</button>
                                </Link>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </section>
    );
};

export default StatisticTable;
