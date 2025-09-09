import { Link, useLocation, useNavigate } from "react-router-dom";
import { FormEvent, useEffect, useState } from "react";
import AssessmentList from "../../components/AssessmentList";
import Select from "react-select";
import select2Styles from "../../styled-components/select2style";
import { InputOption, emptyInputOption } from "../../models/InputOption";
import Assessment, { emptyAssessment } from "../../models/Assessment";
import useFetchOptions from "../../services/useFetchOptions";
import {
  assessmentDeviation,
  averageAssessment,
  businessEvaluation,
  maxPrice,
  minPrice,
  priceXMeterSquared,
  getDaysSincePrice,
} from "../../utilities/landSurveyCalcs";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { toast } from "sonner";
import useIsAdmin from "../../hooks/useIsAdmin";
import CreatableSelect from "react-select/creatable";
import useAuthStore from "../../hooks/useAuthStore";

const LandSurveyDetails = () => {
  const navigate = useNavigate();
  const { email } = useAuthStore((state) => state);
  const isAdmin: boolean = useIsAdmin();
  const userEmail = email;

  const [date, setDate] = useState<Date>(new Date());
  const [originalManager, setOriginalManager] = useState<InputOption>(
    emptyInputOption()
  );
  const [manager, setManager] = useState<InputOption | null>(null);
  const [surveyor, setSurveyor] = useState<InputOption | null>(null);
  const [address, setAddress] = useState<string>();
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
  const [classification, setClassification] = useState<string>("DATO COMPLETO");
  const [observation, setObservation] = useState<string>("");
  const [assessmentList, setAssessmentList] = useState<Assessment[]>([
    emptyAssessment(),
    emptyAssessment(),
  ]);
  const [priceVerificationDate, setPriceVerificationDate] = useState<Date>(
    new Date()
  );
  const [reassessmentDate, setReassessmentDate] = useState<Date>(new Date());
  const [isRescinded, setIsRescinded] = useState<boolean>(false);
  const [maxDeviation, setMaxDeviation] = useState<number>(1);
  const [rePricing, setRepricing] = useState<number>(0);
  const [usdExchangerate, setUsdExchangerate] = useState<number>(1295);
  const [toDelete, setToDelete] = useState<boolean>(false);

  const userOptions: InputOption[] = useFetchOptions("/users/options");

  const id = useLocation().state;
  const axiosPrivate = useAxiosPrivate();

  useEffect(() => {
    const controller = new AbortController();
    let isMounted = true;

    const getRepricingPercentage = async () => {
      try {
        const response = await axiosPrivate.get("/currencies/USD", {
          signal: controller.signal,
        });

        if (isMounted) {
          console.log(response.data);
          setUsdExchangerate(response.data.exchangeReference);
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

  useEffect(() => {
    let isMounted = true;
    const controller = new AbortController();

    const getLandSurvey = async () => {
      try {
        const response = await axiosPrivate.get(`/land-surveys/${id}`, {
          signal: controller.signal,
        });

        if (isMounted && response.data != null) {
          const landsurvey = response.data;
          setDate(new Date(landsurvey.date));
          setManager(landsurvey.manager);
          setOriginalManager(landsurvey.manager);
          setSurveyor(landsurvey.surveyor);
          setAddress(landsurvey.address);
          setLocality({
            value: landsurvey.locality,
            label: landsurvey.locality,
          });
          setSection({
            value: landsurvey.section,
            label: landsurvey.section,
          });
          setZone({
            value: landsurvey.zone,
            label: landsurvey.zone,
          });
          setRoad(landsurvey.roadType);
          setCorner(landsurvey.corner);
          setAgency({
            value: landsurvey.agency,
            label: landsurvey.agency,
          });
          setParticular({
            value: landsurvey.particular,
            label: landsurvey.particular,
          });
          setContact({
            value: landsurvey.contact,
            label: landsurvey.contact,
          });
          setFolder({
            value: landsurvey.folder,
            label: landsurvey.folder,
          });
          setTitle({
            value: landsurvey.title,
            label: landsurvey.title,
          });
          setMeasurements(landsurvey.measurements);
          setSurface(landsurvey.surface);
          setPrice(landsurvey.price);
          setCurrency(landsurvey.currency);
          setFileType(landsurvey.fileType);
          setSource(landsurvey.source);
          setClassification(landsurvey.classification);
          setObservation(landsurvey.observation);
          setAssessmentList(landsurvey.assessmentList);
          setPriceVerificationDate(
            new Date(landsurvey.priceVerificationDate && landsurvey.date)
          );
          setReassessmentDate(
            new Date(landsurvey.reassessmentDate && landsurvey.date)
          );
          setIsRescinded(landsurvey.isRescinded);
        }
      } catch (error) {
        console.error(error);
      }
    };

    getLandSurvey();

    return () => {
      isMounted = false;
      controller.abort();
    };
  }, [axiosPrivate, id]);

  useEffect(() => {
    const controller = new AbortController();
    let isMounted = true;

    const getMaxDeviation = async () => {
      try {
        const response = await axiosPrivate.get("/settings/maxDeviation", {
          signal: controller.signal,
        });
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

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const controller = new AbortController();

    const updateLandSurvey = axiosPrivate.put(
      `/land-surveys/update/${id}`,
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
        priceVerificationDate,
        reassessmentDate,
        currency: currency,
        folder: folder?.label,
        assessmentList,
      }),
      { signal: controller.signal }
    );

    toast.promise(updateLandSurvey, {
      loading: "Loading...",
      success: () => "Exito! Relevamiento Actualizado",
      error: "Error, Mati veni un ratito",
    });

    try {
      const response = await updateLandSurvey;
      console.log(response.data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleRescinde = async (landsurveyId: number) => {
    const controller = new AbortController();
    const sendRescinde = axiosPrivate.patch(
      `/land-surveys/rescind/${landsurveyId}`,
      { id: landsurveyId },
      {
        signal: controller.signal,
      }
    );

    toast.promise(sendRescinde, {
      loading: "Cargando...",
      success: "Exito! Rescisión actualizada",
      error: "Error, Y ahora que paso?",
    });

    try {
      const result = await sendRescinde;
      console.log(result);
      setIsRescinded(result.data);
    } catch (error) {
      console.error(error);
    }
  };

  const handleDelete = async () => {
    const controller = new AbortController();
    const deleteLandSurvey = axiosPrivate.delete(`/land-surveys/delete/${id}`, {
      signal: controller.signal,
    });

    toast.promise(deleteLandSurvey, {
      error: "Error",
      success: "Relevamiento eliminado exitosamente",
      loading: "Cargando...",
    });

    try {
      await deleteLandSurvey;
      navigate("/land-surveys");
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="landsurvey-details">
      <h1>Detalles</h1>
      <form onSubmit={handleSubmit}>
        <div className="dflex gap-30">
          {/* BUTTONS */}
          <Link to="/land-surveys" className="f-stretch">
            <button type="button" className="f-stretch gold-btn">
              Volver
            </button>
          </Link>
          {
            /* isAdmin || userEmail === originalManager.value ? */ <button
              className="f-stretch green-btn"
              type="submit"
            >
              Guardar
            </button> /* : (
                        <></>
                    ) */
          }
        </div>
        <div className="dflex gap-30">
          {/* CODE - DATE */}
          <div>
            <label htmlFor="folder">Carpeta</label>
            <Select
              id="folder"
              styles={select2Styles}
              options={useFetchOptions("/folder/options")}
              value={folder}
              onChange={setFolder}
              placeholder="V"
              required
            />
          </div>
          <div>Fecha: {date.toLocaleDateString("es-AR")}</div>
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
              isDisabled={!(isAdmin || userEmail === originalManager?.value)}
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
          <label htmlFor="surface">Superficie</label>
          <input
            type="number"
            id="surface"
            value={surface}
            onChange={(e) => setSurface(parseFloat(e.target.value))}
            required
          />
        </div>
        <div className="dflex gap-30">
          <div>
            <label htmlFor="priceVerificationDate">
              Fecha de Ultima verificacion de precio
            </label>
            <div className="dflex gap-30">
              <input
                type="date"
                id="priceVerificationDate"
                value={priceVerificationDate?.toISOString().split("T")[0]}
                onChange={(e) =>
                  setPriceVerificationDate(new Date(e.target.value))
                }
              />
              <button
                type="button"
                onClick={() => setPriceVerificationDate(new Date())}
              >
                Hoy
              </button>
            </div>
          </div>
          <div>
            Dias desde ultima verificacion de precio:{" "}
            <output>{getDaysSincePrice(priceVerificationDate)}</output>
          </div>
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
              value={
                "$" +
                Math.round(
                  averageAssessment(assessmentList, rePricing, usdExchangerate)
                )
              }
              disabled
            />
          </div>
        </div>

        <div>
          <label htmlFor="deviation">Porcentaje de desvio</label>
          <input
            className={
              assessmentDeviation(assessmentList, usdExchangerate) >
              maxDeviation
                ? "error-input"
                : ""
            }
            type="number"
            id="deviation"
            value={assessmentDeviation(assessmentList, usdExchangerate)}
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
                  onChange={(e) => setFileType(e.target.value)}
                />
                <label htmlFor="digital">Digital</label>
              </p>

              <p>
                <input
                  type="radio"
                  id="fisico"
                  name="fileType"
                  value="FÍSICO"
                  checked={fileType === "FÍSICO"}
                  onChange={(e) => setFileType(e.target.value)}
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
        <div>
          <label htmlFor="reassessmentDate">Fecha de retasación:</label>
          <div className="dflex gap-30">
            <input
              type="date"
              id="reassessmentDate"
              value={reassessmentDate?.toISOString().split("T")[0]}
              onChange={(e) => setReassessmentDate(new Date(e.target.value))}
            />
            <button
              type="button"
              onClick={() => setReassessmentDate(new Date())}
            >
              Hoy
            </button>
          </div>
        </div>
        <div className="dflex gap-30">
          <div className="f-stretch">
            <label htmlFor="maxAssessment">Tasación Mínima</label>
            <input
              type="number"
              id="maxAssessment"
              value={minPrice(assessmentList, usdExchangerate)}
              disabled
            />
          </div>
          <div className="f-stretch">
            <label htmlFor="minAssessment">Tasación Máxima</label>
            <input
              type="number"
              id="minAssessment"
              value={maxPrice(assessmentList, usdExchangerate)}
              disabled
            />
          </div>
        </div>
        <div>
          <label htmlFor="pricePerSquareMeter">Pretendido (m²) USD</label>
          <input
            type="number"
            id="pricePerSquareMeter"
            value={priceXMeterSquared(
              price,
              currency,
              surface,
              usdExchangerate
            )}
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
              Math.round(
                averageAssessment(assessmentList, rePricing, usdExchangerate)
              ),
              "USD",
              surface,
              usdExchangerate
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
                  onChange={(e) => setClassification(e.target.value)}
                />
                <label htmlFor="datoCompleto">Dato Completo</label>
              </p>
              <p>
                <input
                  type="radio"
                  name="classification"
                  id="identified"
                  value="IDENTIFICADOS"
                  checked={classification === "IDENTIFICADOS"}
                  onChange={(e) => setClassification(e.target.value)}
                />
                <label htmlFor="identified">Identificado</label>
              </p>
              <p>
                <input
                  type="radio"
                  name="classification"
                  id="autorizacionParaPublicar"
                  value="AUTORIZACIÓN PARA PUBLICAR"
                  checked={classification === "AUTORIZACIÓN PARA PUBLICAR"}
                  onChange={(e) => setClassification(e.target.value)}
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
                    classification === "AUTORIZACIÓN DE VENTA SIN EXCLUSIVIDAD"
                  }
                  onChange={(e) => setClassification(e.target.value)}
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
                    classification === "AUTORIZACIÓN DE VENTA CON EXCLUSIVIDAD"
                  }
                  onChange={(e) => setClassification(e.target.value)}
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
                  value="NO TRABAJABLE"
                  checked={classification === "NO TRABAJABLE"}
                  onChange={(e) => setClassification(e.target.value)}
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
            {Math.round(
              businessEvaluation(
                price,
                currency,
                averageAssessment(assessmentList, rePricing, usdExchangerate),
                usdExchangerate
              )
            ) + "%"}
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

          <button
            className={`f-stretch ${toDelete ? "green-btn" : "red-btn"}`}
            onClick={() => setToDelete(!toDelete)}
            type="button"
          >
            {toDelete ? "Cancelar baja" : "Dar de baja"}
          </button>

          <button
            className={`f-stretch ${
              isRescinded ? "transparent-green-btn" : "transparent-red-btn"
            }`}
            onClick={() => handleRescinde(id)}
            type="button"
          >
            {isRescinded ? "Restablecer" : "Rescindir"}
          </button>
        </div>
        <div className="dflex gap-30">
          {
            /* isAdmin || userEmail === originalManager?.value ? */ <button
              className="f-stretch green-btn"
              type="submit"
            >
              Guardar
            </button> /* : (
                        <></>
                    ) */
          }
        </div>
        <div className="dflex gap-30">
          {toDelete ? (
            <button className="red-btn" type="button" onClick={handleDelete}>
              Confirmar dar de baja permanentemente
            </button>
          ) : (
            <></>
          )}
        </div>
      </form>
    </div>
  );
};

export default LandSurveyDetails;
