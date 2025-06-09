import "../../assets/styles/landsurvey-details.css";
import { Link } from "react-router-dom";
import { FormEvent, useEffect, useState } from "react";
import AssessmentList from "../../components/AssessmentList";
import Select from "react-select";
import CreatableSelect from "react-select/creatable";
import select2Styles from "../../styled-components/select2style";
import { InputOption } from "../../models/InputOption";
import useFetchOptions from "../../services/useFetchOptions";
import Assessment, { emptyAssessment } from "../../models/Assessment";
import {
    assessmentDeviation,
    averageAssessment,
    businessEvaluation,
    maxPrice,
    minPrice,
    priceXMeterSquared,
} from "../../utilities/landSurveyCalcs";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { toast } from "sonner";

const LandSurveyCreate = () => {
    const [manager, setManager] = useState<InputOption | null>(null);
    const [surveyor, setSurveyor] = useState<InputOption | null>(null);
    const [address, setAddress] = useState<string>("");
    const [locality, setLocality] = useState<InputOption | null>(null);
    const [section, setSection] = useState<InputOption | null>(null);
    const [zone, setZone] = useState<InputOption | null>(null);
    const [road, setRoad] = useState<string>("CALLE");
    const [corner, setCorner] = useState<boolean>(false);
    const [agency, setAgency] = useState<InputOption | null>(null);
    const [particular, setParticular] = useState<InputOption | null>(null);
    const [contact, setContact] = useState<InputOption | null>(null);
    const [folder, setFolder] = useState<InputOption | null>(null);
    const [title, setTitle] = useState<InputOption | null>(null);
    const [measurements, setMeasurements] = useState<string>("");
    const [surface, setSurface] = useState<number>(0);
    const [price, setPrice] = useState<number>(0);
    const [currency, setCurrency] = useState<string>("USD");
    const [fileType, setFileType] = useState<string>("DIGITAL");
    const [source, setSource] = useState<string>("WEB");
    const [classification, setClassification] =
        useState<string>("DATO COMPLETO");
    const [observation, setObservation] = useState<string>("");
    const [assessmentList, setAssessmentList] = useState<Assessment[]>([
        emptyAssessment(),
    ]);
    const [maxDeviation, setMaxDeviation] = useState<number>(10);
    const [rePricing, setRepricing] = useState<number>(0)

    const userOptions: Array<InputOption> = useFetchOptions("/users/options");
    const axiosPrivate = useAxiosPrivate();

    useEffect(() => {
        const controller = new AbortController();
        let isMounted = true;

        const getMaxDeviation = async () => {
            try {
                const response = await axiosPrivate.get("/settings/maxDeviation", 
                    {signal: controller.signal});

                if (isMounted) {
                    setMaxDeviation(response.data);
                }
            } catch (error) {
                console.error(error);
            }
        };

        getMaxDeviation();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, [axiosPrivate]);

    useEffect(() => {
        const controller = new AbortController();
        let isMounted = true;

        const getRepricingPercentage = async () => {
            try {
                const response = await axiosPrivate.get(
                    "/settings/rePricingPercentaje",
                    { signal: controller.signal }
                );

                if (isMounted) {
                    setRepricing(response.data);
                }
            } catch (error) {
                console.error(error);
            }
        };

        getRepricingPercentage();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, [axiosPrivate]);

    useEffect(() => {console.log(currency)}, [currency]);

    const handleSubmit = async (
        event: FormEvent<HTMLFormElement>
    ): Promise<void> => {
        event.preventDefault();
        const controller = new AbortController();

        const createLandSurvey = axiosPrivate.post(
            "/land-surveys/create",
            JSON.stringify({
                address,
                corner,
                title: title?.label,
                measurements,
                surface,
                observation,
                surveyor,
                manager,
                fileType,
                roadType: road,
                locality: locality?.label,
                section: section?.label,
                zone: zone?.label,
                source,
                classification,
                agency: agency?.label,
                particular: particular?.label,
                contact: contact?.label,
                price,
                currency,
                folder: folder?.label,
                assessmentList,
            }),
            { signal: controller.signal }
        );

        toast.promise(createLandSurvey, {
            loading: "Loading...",
            success: () => "Exito! Nuevo relevamiento creado!",
            error: "Error, Mati veni un ratito",
        });

        try {
            const response = await createLandSurvey;
            console.log(response.data);
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div className="landsurvey-details">
            <h1>Nuevo Relevamiento</h1>
            <form onSubmit={handleSubmit}>
                <div className="dflex gap-30">
                    {/* BUTTONS */}
                    <Link to="/land-surveys" className="f-stretch">
                        <button type="button" className="f-stretch gold-btn">
                            Volver
                        </button>
                    </Link>
                    <button className="f-stretch green-btn">Guardar</button>
                </div>
                <div className="dflex gap-30">
                    {/* CODE - DATE */}
                    <div>
                        <label htmlFor="folder">Carpeta</label>
                        <CreatableSelect id="folder"
                                styles={select2Styles}
                                options={useFetchOptions("/folder/options")}
                                value={folder}
                                onChange={setFolder}
                                placeholder="V"
                                required/>
                    </div>
                    <div>Fecha: {new Date().toLocaleDateString("es-AR")}</div>
                </div>
                <div className="dflex gap-30">
                    {/* MANAGER - SURVEYOR */}
                    <div className="f-stretch">
                        <label htmlFor="manager">Encargado</label>
                        <Select
                            id="manager"
                            styles={select2Styles}
                            options={userOptions}
                            value={manager}
                            onChange={setManager}
                        />
                    </div>
                    <div className="f-stretch">
                        <label htmlFor="surveyor">Relevador</label>
                        <Select
                            id="surveyor"
                            styles={select2Styles}
                            options={userOptions}
                            value={surveyor}
                            onChange={setSurveyor}
                            required
                        />
                    </div>
                </div>
                <div>
                    {/* ADdRESS */}
                    <label htmlFor="address">Ubicacion</label>
                    <input
                        id="address"
                        type="text"
                        value={address}
                        onChange={(e) => setAddress(e.target.value)}
                        required
                    />
                </div>
                <div className="dflex gap-30">
                    {/* LOCALITY - SECTION - ZONE */}
                    <div className="f-stretch">
                        <label htmlFor="locality">Localidad</label>
                        <CreatableSelect
                            id="locality"
                            styles={select2Styles}
                            options={useFetchOptions("/localities/options")}
                            value={locality}
                            onChange={setLocality}
                            isClearable
                        />
                    </div>
                    <div className="f-stretch">
                        <label htmlFor="section">Seccion</label>
                        <CreatableSelect
                            id="section"
                            styles={select2Styles}
                            options={useFetchOptions("/sections/options")}
                            value={section}
                            onChange={setSection}
                            required
                        />
                    </div>
                    <div className="f-stretch">
                        <label htmlFor="zone">Zona</label>
                        <CreatableSelect
                            id="zone"
                            styles={select2Styles}
                            options={useFetchOptions("/zones/options")}
                            value={zone}
                            onChange={setZone}
                            required
                        />
                    </div>
                </div>
                <div className="dflex gap-10">
                    <input
                        type="checkbox"
                        id="corner"
                        checked={corner}
                        onChange={(e) => setCorner(e.target.checked)}
                    />
                    <label htmlFor="corner">Esquina</label>
                </div>
                <div>
                    {/* ROAD */}
                    <fieldset>
                        <legend>Camino:</legend>
                        <div>
                            <p>
                                <input
                                    id="street"
                                    type="radio"
                                    value="CALLE"
                                    name="road"
                                    checked={road === "CALLE"}
                                    onChange={(e) => setRoad(e.target.value)}
                                    required
                                />
                                <label htmlFor="street">Calle</label>
                            </p>
                            <p>
                                <input
                                    id="avenue"
                                    type="radio"
                                    value="AVDA"
                                    name="road"
                                    checked={road === "AVDA"}
                                    onChange={(e) => setRoad(e.target.value)}
                                />
                                <label htmlFor="avenue">Avenida</label>
                            </p>
                            <p>
                                <input
                                    id="route"
                                    type="radio"
                                    value="RUTA"
                                    name="road"
                                    checked={road === "RUTA"}
                                    onChange={(e) => setRoad(e.target.value)}
                                />
                                <label htmlFor="route">Ruta</label>
                            </p>
                        </div>
                    </fieldset>
                </div>
                <div className="dflex gap-30">
                    {/* agency - PARTICULAR */}
                    <div className="f-stretch">
                        <label htmlFor="agency">Proveedor</label>
                        <CreatableSelect
                            id="agency"
                            styles={select2Styles}
                            options={useFetchOptions("/agencies/options")}
                            value={agency}
                            onChange={setAgency}
                            isClearable
                            required
                        />
                    </div>
                    <div className="f-stretch">
                        <label htmlFor="particular">Particular</label>
                        <CreatableSelect
                            id="particular"
                            styles={select2Styles}
                            options={useFetchOptions("/particulars/options")}
                            value={particular}
                            onChange={setParticular}
                            isClearable
                        />
                    </div>
                </div>
                <div className="dflex gap-30">
                    {/* CONTACT - TITLE */}
                    <div className="f-stretch">
                        <label htmlFor="contact">Telefono</label>
                        <CreatableSelect
                            id="contact"
                            styles={select2Styles}
                            options={useFetchOptions("/contacts/options")}
                            value={contact}
                            onChange={setContact}
                            isClearable
                            required
                        />
                    </div>
                </div>

                <div className="dflex gap-30">
                    <div className="f-stretch">
                        <label htmlFor="title">Título</label>
                        <CreatableSelect
                            id="title"
                            styles={select2Styles}
                            options={useFetchOptions("/titles/options")}
                            value={title}
                            onChange={setTitle}
                            isClearable
                            required
                        />
                    </div>
                </div>
                <div>
                    {/* MEASUREMENTS */}
                    <label htmlFor="measurements">Medidas</label>
                    <input
                        type="text"
                        id="measurements"
                        value={measurements}
                        onChange={(e) => setMeasurements(e.target.value)}
                        required
                    />
                </div>
                <div>
                    {/* SURFACE */}
                    <label htmlFor="surface">Superficie</label>
                    <input
                        type="number"
                        id="surface"
                        value={surface}
                        onChange={(e) => setSurface(parseFloat(e.target.value))}
                        onWheel={(e) => (e.target as HTMLInputElement).blur()}
                        required
                    />
                </div>

                <div className="dflex gap-30">
                    {/* PRETENDED PRICE - CURRENCY -> SELECT*/}
                    <div className="f-stretch">
                        <label htmlFor="price">Precio pretendido</label>
                        <input
                            type="number"
                            id="price"
                            value={price}
                            onChange={(e) => setPrice(parseInt(e.target.value))}
                            onWheel={(e) => (e.target as HTMLInputElement).blur()}
                            required
                        />
                    </div>
                    <div className="f-stretch">
                        <label htmlFor="currency">Divisa</label>
                        <select
                            name="currency"
                            id="currency"
                            value={currency}
                            onChange={(e) => setCurrency(e.target.value)}
                        >
                            <option value="USD">USD</option>
                            <option value="ARS">ARS</option>
                        </select>
                        {/*TODO add options*/}
                    </div>
                </div>
                <AssessmentList
                    userOptions={userOptions}
                    assessmentList={assessmentList}
                    setAssessmentList={setAssessmentList}
                />

                <div className="dflex gap-30">
                    <div className="f-stretch">
                        <label htmlFor="averageAssessment">
                            Tasación promedio asesores USD Actualizado
                        </label>
                        <input
                            type="text"
                            value={"$" + Math.round(averageAssessment(assessmentList) * (1 + rePricing / 100))}
                            disabled
                        />
                    </div>
                </div>

                <div>
                    <label htmlFor="deviation">Porcentaje de desvío</label>
                    <input
                        className={
                            assessmentDeviation(assessmentList) > maxDeviation
                                ? "error-input"
                                : ""
                        }
                        type="text"
                        id="deviation"
                        value= {assessmentDeviation(assessmentList) + "%"}
                        disabled
                    />
                    {/* TODO add calculated value */}
                </div>
                <div>
                    <fieldset>
                        <legend>Tipo de archivo</legend>
                        <div className="radio">
                            <p>
                                <input
                                    type="radio"
                                    id="digital"
                                    name="fileType"
                                    value="DIGITAL"
                                    checked={fileType === "DIGITAL"}
                                    onChange={(e) =>
                                        setFileType(e.target.value)
                                    }
                                />
                                <label htmlFor="digital">Digital</label>
                            </p>

                            <p>
                                <input
                                    type="radio"
                                    id="fisico"
                                    name="fileType"
                                    value="FISICO"
                                    checked={fileType === "FISICO"}
                                    onChange={(e) =>
                                        setFileType(e.target.value)
                                    }
                                />
                                <label htmlFor="fisico">Fisico</label>
                            </p>
                        </div>
                    </fieldset>
                </div>
                <div>
                    <fieldset>
                        <legend>Fuente</legend>
                        <div>
                            <p>
                                <input
                                    type="radio"
                                    name="source"
                                    id="web"
                                    value="WEB"
                                    checked={source === "WEB"}
                                    onChange={(e) => setSource(e.target.value)}
                                />
                                <label htmlFor="web">Web</label>
                            </p>
                            <p>
                                <input
                                    type="radio"
                                    name="source"
                                    id="visita"
                                    value="VISITA"
                                    checked={source === "VISITA"}
                                    onChange={(e) => setSource(e.target.value)}
                                />
                                <label htmlFor="visita">Visita</label>
                            </p>
                            <p>
                                <input
                                    type="radio"
                                    name="source"
                                    id="cartel"
                                    value="CARTEL"
                                    checked={source === "CARTEL"}
                                    onChange={(e) => setSource(e.target.value)}
                                />
                                <label htmlFor="cartel">Cartel</label>
                            </p>
                            <p>
                                <input
                                    type="radio"
                                    name="source"
                                    id="bitrix"
                                    value="BITRIX"
                                    checked={source === "BITRIX"}
                                    onChange={(e) => setSource(e.target.value)}
                                />
                                <label htmlFor="bitrix">Bitrix</label>
                            </p>
                            <p>
                                <input
                                    type="radio"
                                    name="source"
                                    id="diario"
                                    value="DIARIO"
                                    checked={source === "DIARIO"}
                                    onChange={(e) => setSource(e.target.value)}
                                />
                                <label htmlFor="diario">Diario</label>
                            </p>
                            <p>
                                <input
                                    type="radio"
                                    name="source"
                                    id="facebook"
                                    value="FACEBOOK"
                                    checked={source === "FACEBOOK"}
                                    onChange={(e) => setSource(e.target.value)}
                                />
                                <label htmlFor="facebook">Facebook</label>
                            </p>
                            <p>
                                <input
                                    type="radio"
                                    name="source"
                                    id="marketplace"
                                    value="MARKETPLACE"
                                    checked={source === "MARKETPLACE"}
                                    onChange={(e) => setSource(e.target.value)}
                                />
                                <label htmlFor="marketplace">Marketplace</label>
                            </p>
                        </div>
                    </fieldset>
                </div>
                <div className="dflex gap-30">
                    <div className="f-stretch">
                        <label htmlFor="minAssessment">Tasación Mínima</label>
                        <input
                            type="number"
                            id="minAssessment"
                            value={minPrice(assessmentList)}
                            disabled
                        />
                    </div>
                    <div className="f-stretch">
                        <label htmlFor="maxAssessment">Tasación Máxima</label>
                        <input
                            type="number"
                            id="maxAssessment"
                            value={maxPrice(assessmentList)}
                            disabled
                        />
                    </div>
                </div>
                <div>
                    <label htmlFor="pricePerSquareMeter">
                        Pretendido (m²) USD
                    </label>
                    <input
                        type="number"
                        id="pricePerSquareMeter"
                        value={currency == "ARS" ? 
                            priceXMeterSquared((price / 1165), surface) : 
                            priceXMeterSquared(price, surface)}
                        disabled
                    />
                </div>
                <div>
                    <label htmlFor="averageAssessmentUsd">
                        Promedio asesores (m²) USD
                    </label>
                    <input
                        type="number"
                        id="averageAssessmentUsd"
                        value={priceXMeterSquared(
                            Math.round(averageAssessment(assessmentList) * (1 + rePricing / 100)),
                            surface
                        )}
                        disabled
                    />
                </div>
                <div>
                    <fieldset>
                        <legend>Clasificacion</legend>
                        <div>
                            <p>
                                <input
                                    type="radio"
                                    name="classification"
                                    id="datoCompleto"
                                    value="DATO COMPLETO"
                                    checked={classification === "DATO COMPLETO"}
                                    onChange={(e) =>
                                        setClassification(e.target.value)
                                    }
                                />
                                <label htmlFor="datoCompleto">
                                    Dato Completo
                                </label>
                            </p>
                            <p>
                                <input
                                    type="radio"
                                    name="classification"
                                    id="identified"
                                    value="IDENTIFICADOS"
                                    checked={classification === "IDENTIFICADOS"}
                                    onChange={(e) =>
                                        setClassification(e.target.value)
                                    }
                                />
                                <label htmlFor="identified">
                                    Identificado
                                </label>
                            </p>
                            <p>
                                <input
                                    type="radio"
                                    name="classification"
                                    id="autorizacionParaPublicar"
                                    value="AUTORIZACIÓN PARA PUBLICAR"
                                    checked={
                                        classification ===
                                        "AUTORIZACIÓN PARA PUBLICAR"
                                    }
                                    onChange={(e) =>
                                        setClassification(e.target.value)
                                    }
                                />
                                <label htmlFor="autorizacionParaPublicar">
                                    Autorizacion para publicar
                                </label>
                            </p>
                            <p>
                                <input
                                    type="radio"
                                    name="classification"
                                    id="autorizacionDeVentaSinExclusividad"
                                    value="AUTORIZACIÓN DE VENTA SIN EXCLUSIVIDAD"
                                    checked={
                                        classification ===
                                        "AUTORIZACIÓN DE VENTA SIN EXCLUSIVIDAD"
                                    }
                                    onChange={(e) =>
                                        setClassification(e.target.value)
                                    }
                                />
                                <label htmlFor="autorizacionDeVentaSinExclusividad">
                                    Autorizacion de venta sin exclusividad
                                </label>
                            </p>
                            <p>
                                <input
                                    type="radio"
                                    name="classification"
                                    id="autorizacionConExclusividad"
                                    value="AUTORIZACIÓN DE VENTA CON EXCLUSIVIDAD"
                                    checked={
                                        classification ===
                                        "AUTORIZACIÓN DE VENTA CON EXCLUSIVIDAD"
                                    }
                                    onChange={(e) =>
                                        setClassification(e.target.value)
                                    }
                                />
                                <label htmlFor="autorizacionConExclusividad">
                                    Autorizacion con exclusividad
                                </label>
                            </p>
                            <p>
                                <input
                                    type="radio"
                                    name="classification"
                                    id="imposibilidadDeTrabajar"
                                    value="IMPOSIBILIDAD DE TRABAJAR"
                                    checked={
                                        classification ===
                                        "IMPOSIBILIDAD DE TRABAJAR"
                                    }
                                    onChange={(e) =>
                                        setClassification(e.target.value)
                                    }
                                />
                                <label htmlFor="imposibilidadDeTrabajar">
                                    Imposibilidad de trabajar
                                </label>
                            </p>
                        </div>
                    </fieldset>
                </div>
                <div>
                    <div>Estado</div>
                    <div>
                        <p>Evaluación de negocio</p>
                        {Math.round(businessEvaluation(
                            currency == "ARS" ? (price / 1165) : price,
                            averageAssessment(assessmentList) * (1 + rePricing / 100)
                        )) + "%"}
                    </div>
                </div>
                <div>
                    <label htmlFor="observations">Observaciones</label>
                    <textarea
                        name="observations"
                        id="observations"
                        rows={6}
                        value={observation}
                        onChange={(e) => setObservation(e.target.value)}
                    ></textarea>
                </div>
                <div className="dflex gap-30">
                    <Link to="/land-surveys" className="f-stretch">
                        <button type="button" className="f-stretch gold-btn">
                            Volver
                        </button>
                    </Link>
                    <button className="green-btn f-stretch">Guardar</button>
                </div>
            </form>
        </div>
    );
};

export default LandSurveyCreate;
