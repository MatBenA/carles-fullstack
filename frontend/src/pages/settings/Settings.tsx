import { useEffect, useState } from "react";
import "../../assets/styles/utils.css";
import "./styles/settings.css";
import { Setting, emptySetting } from "./model/Setting.ts";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { toast } from "sonner";

const Settings = () => {
    const [maxDeviation, setMaxDeviation] = useState<Setting>(emptySetting());
    const [statisticTopLimit, setStatisticTopLimit] = useState<Setting>(
        emptySetting()
    );
    const [statisticBottomLimit, setStatisticBottomLimit] = useState<Setting>(
        emptySetting()
    );
    const [particularValidity, setParticularValidity] = useState<Setting>(
        emptySetting()
    );
    const [agencyValidity, setAgencyValidity] = useState<Setting>(
        emptySetting()
    );
    const [unworkableValidity, setUnworkableValidity] = useState<Setting>(
        emptySetting()
    );
    const [businessEvaluationCheapFlag, setBusinessEvaluationCheapFlag] =
        useState<Setting>(emptySetting());
    const [businessEvaluationExpFlag, setBusinessEvaluationExpFlag] =
        useState<Setting>(emptySetting());
    const [rePricingPercentaje, setRePricingPercentaje] = useState<Setting>(
        emptySetting()
    );

    const axiosPrivate = useAxiosPrivate();

    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();
        const getSettings = async () => {
            try {
                const result = await axiosPrivate.get("/settings", {
                    signal: controller.signal,
                });

                if (isMounted && result.data) {
                    const data: Setting[] = result.data;

                    console.log(data);
                    //TODO IMPLEMENT ENUM INSTEAD OF STRING IN FINDSETTING PARAMETER
                    const findSetting = (name: string): Setting =>
                        data.find((item) => item.name === name)!;

                    setMaxDeviation(findSetting("maxDeviation"));
                    setStatisticTopLimit(findSetting("statisticTopLimit"));
                    setStatisticBottomLimit(
                        findSetting("statisticBottomLimit")
                    );
                    setParticularValidity(findSetting("particularValidity"));
                    setAgencyValidity(findSetting("agencyValidity"));
                    setUnworkableValidity(findSetting("unworkableValidity"));
                    setBusinessEvaluationCheapFlag(
                        findSetting("businessEvaluationCheapFlag")
                    );
                    setBusinessEvaluationExpFlag(
                        findSetting("businessEvaluationExpFlag")
                    );
                    setRePricingPercentaje(findSetting("rePricingPercentaje"));
                }
            } catch (error) {
                console.error(error);
            }
        };

        getSettings();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, [axiosPrivate]);

    const handleClick = async (setting: Setting) => {
        const controller = new AbortController();
        const updateSetting = axiosPrivate.put(
            "/settings",
            { ...setting },
            { signal: controller.signal }
        );

        toast.promise(updateSetting, {
            loading: "Loading...",
            success: () => "Exito! Configuracion actualizada",
            error: "Error, Mati veni un ratito",
        });

        try {
            await updateSetting;
        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div className="container">
            <section className="form-custom">
                <div>
                <h3>Configuraciones</h3>
                    <h3>Tasaciones</h3>
                    <label htmlFor="maxDeviation">
                        Porcentaje Máximo de desvío de tasación admitido
                    </label>
                    <div className="dflex gap-30">
                        <input
                            id="maxDeviation"
                            type="number"
                            value={maxDeviation.value}
                            onChange={(e) =>
                                setMaxDeviation({
                                    name: "maxDeviation",
                                    value: e.target.value,
                                })
                            }
                        />
                        <button
                            onClick={() => handleClick(maxDeviation)}
                            className="green-btn"
                        >
                            Guardar
                        </button>
                    </div>
                </div>
                <div>
                    <h3>Reporte estadistica (Banderas de vigencia en dias)</h3>
                    <label htmlFor="statisticTopLimit">Bandera superior</label>
                    <div className="dflex gap-30">
                        <input
                            id="statisticTopLimit"
                            type="number"
                            value={statisticTopLimit.value}
                            onChange={(e) =>
                                setStatisticTopLimit({
                                    name: "statisticTopLimit",
                                    value: e.target.value,
                                })
                            }
                        />
                        <button
                            onClick={() => handleClick(statisticTopLimit)}
                            className="green-btn"
                        >
                            Guardar
                        </button>
                    </div>
                </div>
                <div>
                    <label htmlFor="statisticBottomLimit">
                        Bandera inferior
                    </label>
                    <div className="dflex gap-30">
                        <input
                            id="statisticBottomLimit"
                            type="number"
                            value={statisticBottomLimit.value}
                            onChange={(e) =>
                                setStatisticBottomLimit({
                                    name: "statisticBottomLimit",
                                    value: e.target.value,
                                })
                            }
                        />
                        <button
                            onClick={() => handleClick(statisticBottomLimit)}
                            className="green-btn"
                        >
                            Guardar
                        </button>
                    </div>
                </div>
                <div>
                    <h3>Reporte Actualizacion (Vigencias en dias)</h3>
                    <label htmlFor="particularValidity">
                        Vigencia propietarios
                    </label>
                    <div className="dflex gap-30">
                        <input
                            id="particularValidity"
                            type="number"
                            value={particularValidity.value}
                            onChange={(e) =>
                                setParticularValidity({
                                    name: "particularValidity",
                                    value: e.target.value,
                                })
                            }
                        />
                        <button
                            onClick={() => handleClick(particularValidity)}
                            className="green-btn"
                        >
                            Guardar
                        </button>
                    </div>
                </div>
                <div>
                    <label htmlFor="agencyValidity">
                        Vigencia inmobiliaria/intermediario
                    </label>
                    <div className="dflex gap-30">
                        <input
                            id="agencyValidity"
                            type="number"
                            value={agencyValidity.value}
                            onChange={(e) =>
                                setAgencyValidity({
                                    name: "agencyValidity",
                                    value: e.target.value,
                                })
                            }
                        />
                        <button
                            onClick={() => handleClick(agencyValidity)}
                            className="green-btn"
                        >
                            Guardar
                        </button>
                    </div>
                </div>
                <div>
                    <label htmlFor="unworkableValidity">
                        Vigencia rojo no trabajable
                    </label>
                    <div className="dflex gap-30">
                        <input
                            id="unworkableValidity"
                            type="number"
                            value={unworkableValidity.value}
                            onChange={(e) =>
                                setUnworkableValidity({
                                    name: "unworkableValidity",
                                    value: e.target.value,
                                })
                            }
                        />
                        <button
                            onClick={() => handleClick(unworkableValidity)}
                            className="green-btn"
                        >
                            Guardar
                        </button>
                    </div>
                </div>
                <div>
                    <h3>Evaluacion de negocio (Porcentaje)</h3>
                    <label htmlFor="businessEvaluationCheapFlag">
                        Bandera porcentaje barato de precio
                    </label>
                    <div className="dflex gap-30">
                        <input
                            id="businessEvaluationCheapFlag"
                            type="number"
                            value={businessEvaluationCheapFlag.value}
                            onChange={(e) =>
                                setBusinessEvaluationCheapFlag({
                                    name: "businessEvaluationCheapFlag",
                                    value: e.target.value,
                                })
                            }
                        />
                        <button
                            onClick={() =>
                                handleClick(businessEvaluationCheapFlag)
                            }
                            className="green-btn"
                        >
                            Guardar
                        </button>
                    </div>
                </div>
                <div>
                    <label htmlFor="businessEvaluationExpFlag">
                        Bandera porcentaje caro de precio
                    </label>
                    <div className="dflex gap-30">
                        <input
                            id="businessEvaluationExpFlag"
                            type="number"
                            value={businessEvaluationExpFlag.value}
                            onChange={(e) =>
                                setBusinessEvaluationExpFlag({
                                    name: "businessEvaluationExpFlag",
                                    value: e.target.value,
                                })
                            }
                        />
                        <button
                            onClick={() =>
                                handleClick(businessEvaluationExpFlag)
                            }
                            className="green-btn"
                        >
                            Guardar
                        </button>
                    </div>
                </div>
                <div>
                    <h3>Actualizacion</h3>
                    <label htmlFor="rePricingPercentaje">
                        Re-marcacion en precios en USD (porcentaje)
                    </label>
                    <div className="dflex gap-30">
                        <input
                            id="rePricingPercentaje"
                            type="number"
                            value={rePricingPercentaje.value}
                            onChange={(e) =>
                                setRePricingPercentaje({
                                    name: "rePricingPercentaje",
                                    value: e.target.value,
                                })
                            }
                        />
                        <button
                            onClick={() => handleClick(rePricingPercentaje)}
                            className="green-btn"
                        >
                            Guardar
                        </button>
                    </div>
                </div>
            </section>
        </div>
    );
};

export default Settings;
