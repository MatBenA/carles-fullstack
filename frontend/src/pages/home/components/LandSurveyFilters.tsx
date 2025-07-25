import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import Select from "react-select";
import "../styles/filters.css";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import { InputOption } from "../../../models/InputOption";
import fetchLandSurveys from "../services/fetchLandSurveys";
import select2Styles from "../../../styled-components/select2style";
import useFetchOptions from "../../../services/useFetchOptions";
import evaluationOptions from "../../../utilities/businessEvaluationOptions";
import LandSurvey from "../../../models/LandSurvey";

type LandSurveyFiltersProps = {
    setLandSurveys: React.Dispatch<React.SetStateAction<LandSurvey[]>>;
};

const LandSurveyFilters = ({ setLandSurveys }: LandSurveyFiltersProps) => {
    //const { auth } = useAuth();
    //const isAdmin: boolean = useIsAdmin();
    //const userEmail: string | undefined = auth?.email;
    const userOptions = useFetchOptions("/users/options");

    const axiosPrivate = useAxiosPrivate();
    const [minPrice, setMinPrice] = useState<number>();
    const [maxPrice, setMaxPrice] = useState<number>();
    const [businessEvaluation, setBusinessEvaluation] =
        useState<InputOption | null>(null);
    const [section, setSection] = useState<InputOption | null>(null);
    const [zone, setZone] = useState<InputOption | null>(null);
    const [agency, setAgency] = useState<InputOption | null>(null);
    const [manager, setManager] = useState<InputOption | null>(null);
    const [surveyor, setSurveyor] = useState<InputOption | null>(null);
    const [particular, setParticular] = useState<InputOption | null>(null);
    const [folder, setFolder] = useState<InputOption | null>(null);
    const [classification, setClassification] = useState<string>();
    const [title, setTitle] = useState<InputOption | null>(null);
    //const [contact, setContact] = useState<InputOption | null>(null);
    const [address, setAddress] = useState<string>();
    const [rescinded, setRescinded] = useState<boolean>(false);

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
                    address,
                    businessEvaluation: businessEvaluation?.value,
                    section: section?.label,
                    zone: zone?.label,
                    agency: agency?.label,
                    particular: particular?.label,
                    managerEmail: manager?.value,
                    surveyorEmail: surveyor?.value,
                    folder: folder?.value,
                    classification,
                    title: title?.label,
                    rescinded,
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
    }, [maxPrice, minPrice, address, businessEvaluation, section?.label, zone?.label, agency?.label, axiosPrivate, classification, particular?.label, setLandSurveys, title?.label, manager, rescinded, folder?.value, title?.value, surveyor]);

    /*const handleSetManager = () => {
        console.log(manager);
        if (manager?.value) {
            setManager(null);
        } else {
            setManager({ value: userEmail, label: "" });
        }
    };*/

    return (
        <section className="filters">
            <div className="price-eval">
                <div className="price-range">
                    <div>
                        <label htmlFor="min-price">Precio mínimo:</label>
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
                        <label htmlFor="max-price">Precio máximo:</label>
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
                    <label htmlFor="evaluation">Evaluación de Negocio:</label>
                    <Select
                        id="evaluation"
                        styles={select2Styles}
                        options={evaluationOptions}
                        value={businessEvaluation}
                        onChange={setBusinessEvaluation}
                        isClearable
                    />
                </div>

                <div>
                    <label htmlFor="min-price">Buscar por ubicación</label>
                    <input
                        type="text"
                        id="min-price"
                        value={address}
                        onChange={(e) => setAddress(e.target.value)}
                    />
                </div>
            </div>

            <div className="section-zone">
                <div>
                    <label htmlFor="section">Sección</label>
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
                    <label htmlFor="zone">Zona</label>
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
                <div>
                    <label htmlFor="folder">Carpeta</label>
                    <Select
                        id="folder"
                        styles={select2Styles}
                        options={useFetchOptions("/folder/options")}
                        value={folder}
                        onChange={setFolder}
                        placeholder="V"
                        isClearable
                    />
                </div>
            </div>

            <div className="fgrow agency-particular">
                <div>
                    <label htmlFor="agency">Proveedor</label>
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
                    <label htmlFor="particular">Particular</label>
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
                <div>
                    <label htmlFor="title">Título</label>
                    <Select
                        id="title"
                        styles={select2Styles}
                        options={useFetchOptions("/titles/options")}
                        value={title}
                        onChange={setTitle}
                        placeholder="Título"
                        isClearable
                    />
                </div>
            </div>
            <div className="classification-title">
                <div>
                    <label htmlFor="classification">Clasificación</label>
                    <select
                        name="classification"
                        id="classification"
                        value={classification}
                        onChange={(e) => setClassification(e.target.value)}
                    >
                        <option value="">Seleccione opcion</option>
                        <option value="DATO COMPLETO">Dato Completo</option>
                        <option value="IDENTIFICADOS">Identificado</option>
                        <option value="AUTORIZACIÓN PARA PUBLICAR">
                            Autorizado para publicar
                        </option>
                        <option value="AUTORIZACIÓN DE VENTA SIN EXCLUSIVIDAD">
                            Sin exclusividad
                        </option>
                        <option value="AUTORIZACIÓN DE VENTA CON EXCLUSIVIDAD">
                            Con exclusividad
                        </option>
                        <option value="NO TRABAJABLE">No Trabajable</option>
                    </select>
                </div>
                    <div>
                        <label htmlFor="manager">Encargado</label>
                        <Select
                            id="manager"
                            styles={select2Styles}
                            options={userOptions}
                            value={manager}
                            onChange={setManager}
                            placeholder="Encargado"
                            isClearable
                        />
                    </div>
                    
                    <div>
                        <label htmlFor="surveyor">Relevador</label>
                        <Select
                            id="surveyor"
                            styles={select2Styles}
                            options={userOptions}
                            value={surveyor}
                            onChange={setSurveyor}
                            placeholder="Relevador"
                            isClearable
                        />
                    </div>
                
                {/*
                <div>
                    <label htmlFor="contact">Telefono</label>
                    <Select
                        id="contact"
                        styles={select2Styles}
                        options={useFetchOptions("/contacts/options")}
                        value={contact}
                        onChange={setContact}
                        placeholder={"+00 000 0000"}
                        isClearable/>    
                </div>*/}
            </div>

            <div>
                <div>
                    <input
                        type="checkbox"
                        id="rescinded"
                        checked={rescinded}
                        onChange={(e) => setRescinded(e.target.checked)}
                    />
                    <label htmlFor="rescinded">Ver solo rescindidas</label>
                </div>
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
