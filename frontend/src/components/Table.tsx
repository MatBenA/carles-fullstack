import React, { useState } from "react";
import { Link } from "react-router-dom";
import LandSurvey from "../models/LandSurvey";
import Select from "react-select";
import { InputOption } from "../models/InputOption";
import makeAnimated from "react-select/animated";
import columnOptions from "../utilities/columns";
import "../assets/styles/landsurvey-table.css"

interface Props {
    landSurveys: LandSurvey[] | undefined;
    children?: React.ReactNode;
}

const LandSurveyTable = ({ landSurveys }: Props) => {
    

    const [columnList, setColumnList] = useState<InputOption[]>();
    const animatedComponents = makeAnimated();
    const isSelected = (columnName: string) => columnList?.some(column => column.value === columnName)
    
    return (
        <div>
            <div>
                <label htmlFor="remove-col">Remover columnas:</label>
                <Select
                    id="remove-col"
                    isMulti={true}
                    closeMenuOnSelect={false}
                    components={animatedComponents}
                    options={columnOptions}
                    onChange={option => setColumnList([...option])}
                    value={columnList}
                    placeholder="Seleccione columna a remover"
                />
            </div>
            <table>
                <thead>
                    <tr>
                        <th>Total: {landSurveys?.length}</th>
                        <th hidden={isSelected("Carpeta")}>Carpeta</th>
                        <th hidden={isSelected("Fecha")} className="date">Fecha</th>
                        <th hidden={isSelected("Relevó")} className="surveyor">Relevó</th>
                        <th hidden={isSelected("Encargado")} className="manager">Encargado</th>
                        <th hidden={isSelected("Tipo de archivo")}>Tipo de archivo</th>
                        <th hidden={isSelected("Ubicacion")} className="address">Ubicación</th>
                        <th hidden={isSelected("Camino")}>Camino</th>
                        <th hidden={isSelected("Esquina")}>Esquina</th>
                        <th hidden={isSelected("Seccion")}>Sección</th>
                        <th hidden={isSelected("Zona")}>Zona</th>
                        <th hidden={isSelected("Proveedor")} className="agency">Proveedor</th>
                        <th hidden={isSelected("Particular")} className="particular">Particular</th>
                        <th hidden={isSelected("Contacto")}>Contacto</th>
                        <th hidden={isSelected("Fuente")}>Fuente</th>
                        <th hidden={isSelected("Titulo")}>Título</th>
                        <th hidden={isSelected("Medidas")}>Medidas</th>
                        <th hidden={isSelected("Superficie")}>Superficie</th>
                        <th hidden={isSelected("Verificación precio")}>Ultima verificación del precio</th>
                        <th hidden={isSelected("Dias ultima Verificación")}>Días de Ultima Verificación de Precio</th>
                        <th hidden={isSelected("Re-Tasación fecha")}>Fecha de Re-Tasación</th>
                        <th hidden={isSelected("Pretendido")} className="pretended">Precio Pretendido</th>
                        <th hidden={isSelected("pretendido m2 USD")}>Precio pretendido m2</th>
                        <th hidden={isSelected("Tasacion promedio ajustada")}>Tasación promedio ajustada</th>
                        <th hidden={isSelected("Promedio asesores m2")}>M2 opinión de asesores USD</th>
                        <th hidden={isSelected("Evaluación")}>Evaluación del negocio</th>
                        <th hidden={isSelected("Clasificacion")} className="classification">Clasificación</th>
                        <th hidden={isSelected("Valor maximo")}>Valor máximo</th>
                        <th hidden={isSelected("Valor mínimmo")}>Valor mínimmo</th>
                        <th hidden={isSelected("% Desvío")}>% Desvío</th>
                        <th hidden={isSelected("Observaciones")} className="observations">Observaciones</th>
                    </tr>
                </thead>
                {landSurveys ? (
                    <tbody>
                        {landSurveys.map((landSurvey, i) => (
                            <tr key={i} className={
                                landSurvey.classification == "NO TRABAJABLE" ? "red-font" 
                                : landSurvey.classification == "IDENTIFICADOS" ? "blue-font bold" 
                                : ""}>
                                <td>
                                    <Link
                                        to="/land-surveys/detail"
                                        state={landSurvey.id}
                                        onClick={() =>
                                            window.scrollTo({ top: 0 })
                                        }
                                    >
                                        <button>Detalles</button>
                                    </Link>
                                </td>
                                <td hidden={isSelected("Carpeta")}>V{landSurvey.folder}</td>
                                <td hidden={isSelected("Fecha")}>{landSurvey.date}{/* new Date(landSurvey.date).toLocaleDateString("es-AR") */}</td>
                                <td hidden={isSelected("Relevó")}>{landSurvey.surveyor.label}</td>
                                <td hidden={isSelected("Encargado")}>{landSurvey.manager.label}</td>
                                <td hidden={isSelected("Tipo de archivo")}>{landSurvey.fileType}</td>
                                <td hidden={isSelected("Ubicacion")}>{landSurvey.address}</td>
                                <td hidden={isSelected("Camino")}>{landSurvey.roadType}</td>
                                <td hidden={isSelected("Esquina")}>{landSurvey.corner ? "Si" : "No"}</td>
                                <td hidden={isSelected("Seccion")}>{landSurvey.section}</td>
                                <td hidden={isSelected("Zona")}>{landSurvey.zone}</td>
                                <td hidden={isSelected("Proveedor")}>{landSurvey.agency}</td>
                                <td hidden={isSelected("Particular")}>{landSurvey.particular}</td>
                                <td hidden={isSelected("Contacto")}>{landSurvey.contact}</td>
                                <td hidden={isSelected("Fuente")}>{landSurvey.source}</td>
                                <td hidden={isSelected("Titulo")}>{landSurvey.title}</td>
                                <td hidden={isSelected("Medidas")}>{landSurvey.measurements}</td>
                                <td hidden={isSelected("Superficie")}>{landSurvey.surface}</td>
                                <td hidden={isSelected("Verificación precio")}>{landSurvey.priceVerificationDate}</td>
                                <td hidden={isSelected("Dias ultima Verificación")}>{landSurvey.daysSincePriceVerification}</td>
                                <td hidden={isSelected("Re-Tasación fecha")}>{landSurvey.reassessmentDate}</td>
                                <td hidden={isSelected("Pretendido")}>{`${landSurvey.currency} ${landSurvey.price}`}</td>
                                <td hidden={isSelected("pretendido m2")}>{landSurvey.pricePerSquareMeter}</td>
                                <td hidden={isSelected("Tasacion promedio ajustada")}>{Math.round(landSurvey.averageAssessmentUsd)}</td>
                                <td hidden={isSelected("Promedio asesores m2")}>{Math.round(landSurvey.assessmentsPerSquareMeterUsd)}</td>
                                <td hidden={isSelected("Evaluación")} 
                                    className={landSurvey.businessEvaluation < -10 ? "green-font" : landSurvey.businessEvaluation > 10 ? "red-font" : ""}>
                                        {Math.round(landSurvey.businessEvaluation)}
                                </td>
                                <td hidden={isSelected("Clasificacion")}>{landSurvey.classification}</td>
                                <td hidden={isSelected("Valor máximo")}>{landSurvey.maxPrice}</td>
                                <td hidden={isSelected("Valor mínimmo")}>{landSurvey.minPrice}</td>
                                <td hidden={isSelected("% Desvío")}>{landSurvey.deviation}</td>
                                <td hidden={isSelected("Observaciones")}>{landSurvey.observation}</td>
                            </tr>
                        ))}
                    </tbody>
                ) : (
                    <tbody>
                        <tr>
                            <td hidden={isSelected("Fuente")}></td>
                        </tr>
                    </tbody>
                )}
            </table>
        </div>
    );
};

export default LandSurveyTable;
