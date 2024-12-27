import { useLocation, useNavigate } from "react-router-dom";
import "./styles/statisticReport.css";
import { Statistic } from "./models/Statistic";

const StatisticReport = () => {
    const location = useLocation();
    const navigate = useNavigate();
    const statistic: Statistic = location.state;

    const percentOfTotal = (number: number): number =>
        Math.round((number / statistic.totalLandSurveys) * 100);

    return (
        <div className="container statistic-report">
            <div>
                <button
                    className="gold-btn"
                    onClick={() => navigate("/statistics")}
                >
                    Volver
                </button>
            </div>
            <div className="report">
                <div className="row">
                    <div className="cells-container"></div>
                    <div className="cells-container">
                        <div className="cell">Fecha</div>
                        <div className="cell">Base de Datos</div>
                        <div className="cell">Valor Medio</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">
                            {new Date(
                                statistic.creationDate
                            ).toLocaleDateString("es-AR")}
                        </div>
                        <div className="cell">{statistic.totalLandSurveys}</div>
                        <div className="cell">USD {statistic.averageValue}</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">⠀</div>
                        <div className="cell">⠀</div>
                        <div className="cell">⠀</div>
                    </div>
                </div>
                <div className="row">
                    <div className="cells-container">Propiedad</div>
                    <div className="cells-container">
                        <div className="cell">Carles</div>
                        <div className="cell">Inmobiliarias</div>
                        <div className="cell">Propietarios</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">{statistic.totalCarles}</div>
                        <div className="cell">{statistic.totalAgencies}</div>
                        <div className="cell">{statistic.totalOwners}</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">
                            {percentOfTotal(statistic.totalCarles)}%
                        </div>
                        <div className="cell">
                            {percentOfTotal(statistic.totalAgencies)}%
                        </div>
                        <div className="cell">
                            {percentOfTotal(statistic.totalOwners)}%
                        </div>
                    </div>
                </div>
                <div className="row">
                    <div className="cells-container">Precios</div>
                    <div className="cells-container">
                        <div className="cell">Carles Rojos</div>
                        <div className="cell">Rojos</div>
                        <div className="cell">Negros</div>
                        <div className="cell">Verdes</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">{statistic.totalCarlesRed}</div>
                        <div className="cell">{statistic.totalReds}</div>
                        <div className="cell">{statistic.totalNormals}</div>
                        <div className="cell">{statistic.totalGreens}</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">
                            {percentOfTotal(statistic.totalCarlesRed)}%
                        </div>
                        <div className="cell">
                            {percentOfTotal(statistic.totalReds)}%
                        </div>
                        <div className="cell">
                            {percentOfTotal(statistic.totalNormals)}%
                        </div>
                        <div className="cell">
                            {percentOfTotal(statistic.totalGreens)}%
                        </div>
                    </div>
                </div>
                <div className="row">
                    <div className="cells-container">Vigencia</div>
                    <div className="cells-container">
                        <div className="cell">-90</div>
                        <div className="cell">-180</div>
                        <div className="cell">Mas</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">{statistic.belowMinLimit}</div>
                        <div className="cell">{statistic.belowMaxLimit}</div>
                        <div className="cell">{statistic.overLimits}</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">
                            {percentOfTotal(statistic.belowMinLimit)}%
                        </div>
                        <div className="cell">
                            {percentOfTotal(statistic.belowMaxLimit)}%
                        </div>
                        <div className="cell">
                            {percentOfTotal(statistic.overLimits)}%
                        </div>
                    </div>
                </div>
                <div className="row">
                    <div className="cells-container">Localidad</div>
                    <div className="cells-container">
                        <div className="cell">Posadas</div>
                        <div className="cell">Garupa</div>
                        <div className="cell">Candelaria</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">{statistic.posadasTotal}</div>
                        <div className="cell">{statistic.posadasTotal}</div>
                        <div className="cell">{statistic.candelariaTotal}</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">
                            {percentOfTotal(statistic.posadasTotal)}%
                        </div>
                        <div className="cell">
                            {percentOfTotal(statistic.garupaTotal)}%
                        </div>
                        <div className="cell">
                            {percentOfTotal(statistic.posadasTotal)}%
                        </div>
                    </div>
                </div>
                <div className="row">
                    <div className="cells-container">Titulo</div>
                    <div className="cells-container">
                        <div className="cell">Si</div>
                        <div className="cell">No</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">{statistic.WithTitle}</div>
                        <div className="cell">
                            {statistic.totalLandSurveys - statistic.WithTitle}
                        </div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">
                            {percentOfTotal(statistic.WithTitle)}%
                        </div>
                        <div className="cell">
                            {percentOfTotal(
                                statistic.totalLandSurveys - statistic.WithTitle
                            )}
                            %
                        </div>
                    </div>
                </div>
                <div className="row">
                    <div className="cells-container">
                        <div className="cell">No Trabajables</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">Rojos</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">{statistic.totalUnworkable}⠀</div>
                    </div>
                    <div className="cells-container">
                        <div className="cell">
                            {percentOfTotal(statistic.totalUnworkable)}%
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default StatisticReport;
