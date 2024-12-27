import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Select from "react-select";
import "../styles/filters.css";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import { InputOption } from "../../../models/InputOption";
import fetchLandSurveys from "../services/fetchLandSurveys";
import select2Styles from "../../../styled-components/select2style";
import useFetchOptions from "../../../services/useFetchOptions";
import useHandleGetRescindeds from "../services/useHandleGetRescindeds";
import evaluationOptions from "../../../utilities/businessEvaluationOptions";
import LandSurvey from "../../../models/LandSurvey";

type LandSurveyFiltersProps = {
    setLandSurveys: React.Dispatch<React.SetStateAction<LandSurvey[]>>;
} 

const LandSurveyFilters = ({ setLandSurveys }: LandSurveyFiltersProps) => {
    const handleGetRescindeds = useHandleGetRescindeds(setLandSurveys);

    const axiosPrivate = useAxiosPrivate();
    const [minPrice, setMinPrice] = useState<number>();
    const [maxPrice, setMaxPrice] = useState<number>();
    const [businessEvaluation, setBusinessEvaluation] = useState<InputOption>();
    const [section, setSection] = useState<InputOption>();
    const [zone, setZone] = useState<InputOption>();
    const [agency, setAgency] = useState<InputOption>();
    const [particular, setParticular] = useState<InputOption>();
    const [classification, setClassification] = useState<string>();
    const [title, setTitle] = useState<boolean>(true);

    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const getLandSurveys = async () => {
            const newLandSurveys = await fetchLandSurveys(
                axiosPrivate,
                controller,
                {
                    minPrice,
                    maxPrice,
                    businessEvaluation: businessEvaluation?.value,
                    section: section?.label,
                    zone: zone?.label,
                    agency: agency?.label,
                    particular: particular?.label,
                    classification,
                    title,
                }
            );
            if (isMounted && newLandSurveys) {
                setLandSurveys(newLandSurveys);
            }
        };

        getLandSurveys();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, [
        agency?.label,
        axiosPrivate,
        businessEvaluation,
        classification,
        maxPrice,
        minPrice,
        particular?.label,
        section?.label,
        setLandSurveys,
        title,
        zone?.label,
    ]);

    return (
        <section className="filters">
            <div className="price-eval">
                <div className="price-range">
                    <div>
                        <label htmlFor="min-price">Precio minimo:</label>
                        <input
                            type="number"
                            id="min-price"
                            value={minPrice}
                            onChange={(e) =>
                                setMinPrice(parseInt(e.target.value))
                            }
                            placeholder="$"
                        />
                    </div>
                    <div>
                        <label htmlFor="max-price">Precio maximo:</label>
                        <input
                            type="number"
                            id="max-price"
                            value={maxPrice}
                            onChange={(e) =>
                                setMaxPrice(parseInt(e.target.value))
                            }
                            placeholder="$"
                        />
                    </div>
                </div>

                <div className="evaluation">
                    <label htmlFor="evaluation">Evaluacion de Negocio:</label>
                    <Select
                        id="evaluation"
                        styles={select2Styles}
                        options={evaluationOptions}
                        value={businessEvaluation}
                        onChange={setBusinessEvaluation}
                        isClearable
                    />
                </div>
            </div>

            <div className="section-zone">
                <div>
                    <label htmlFor="section">Seccion: </label>
                    <Select
                        id="section"
                        styles={select2Styles}
                        options={useFetchOptions("/sections/options")}
                        value={section}
                        onChange={setSection}
                        placeholder="Seccion"
                        isClearable
                    />
                </div>
                <div>
                    <label htmlFor="zone">Zona: </label>
                    <Select
                        id="zone"
                        styles={select2Styles}
                        options={useFetchOptions("/zones/options")}
                        value={zone}
                        onChange={setZone}
                        placeholder="Zona"
                        isClearable
                    />
                </div>
            </div>

            <div className="fgrow agency-particular">
                <div>
                    <label htmlFor="agency">Proveedor: </label>
                    <Select
                        id="agency"
                        styles={select2Styles}
                        options={useFetchOptions("/agencies/options")}
                        value={agency}
                        onChange={setAgency}
                        placeholder="Proveedor"
                        isClearable
                    />
                </div>
                <div>
                    <label htmlFor="particular">Particular: </label>
                    <Select
                        id="particular"
                        styles={select2Styles}
                        options={useFetchOptions("/particulars/options")}
                        value={particular}
                        onChange={setParticular}
                        placeholder="Particular"
                        isClearable
                    />
                </div>
            </div>
            <div className="classification-title">
                <div>
                    <label htmlFor="classification">Clasificacion</label>
                    <select
                        name="classification"
                        id="classification"
                        value={classification}
                        onChange={(e) => setClassification(e.target.value)}
                    >
                        <option value="">Seleccione opcion</option>
                        <option value="Dato Completo">Dato Completo</option>
                        <option value="Identificado">Identificado</option>
                        <option value="Autorizado para publicar">
                            Autorizado para publicar
                        </option>
                        <option value="Sin exclusividad">
                            Sin exclusividad
                        </option>
                        <option value="Con exclusividad">
                            Con exclusividad
                        </option>
                        <option value="No Trabajable">No Trabajable</option>
                    </select>
                </div>
                <div className="dflex gap-10">
                    <input
                        id="title"
                        type="checkbox"
                        checked={title}
                        onChange={(e) => setTitle(e.target.checked)}
                    />
                    <label htmlFor="title">Posee Titulo</label>
                </div>
            </div>

            <div>
                <button
                    className="transparent-red-btn"
                    onClick={handleGetRescindeds}
                >
                    Ver solo Rescindidas
                </button>
                <Link to="/land-surveys/create">
                    <button className="green-btn">
                        <span>Añadir Relevamiento</span>
                    </button>
                </Link>
            </div>
        </section>
    );
};

export default LandSurveyFilters;
