import { useEffect, useState } from "react";
import LandSurvey from "../../models/LandSurvey";
import Select from "react-select";
import notifcationCategories from "./assets/notificationCategories";
import LandSurveyTable from "../../components/Table";
import adaptLandSurvey from "../home/adapters/landSurveysAdapter";
import select2Styles from "../../styled-components/select2style";
import { InputOption } from "../../models/InputOption";
import useAxiosPrivate from "../../hooks/useAxiosPrivate";

const Notifications = () => {
    const [landSurveys, setLandSurveys] = useState<LandSurvey[]>();
    const [category, setCategory] = useState<InputOption | null>(null);
    const axiosPrivate = useAxiosPrivate();

    useEffect(() => {
        const controller = new AbortController();
        let isMounted = true;

        const getNotifications = async () => {
            const response = await axiosPrivate.get(
                "/land-surveys/",
                { signal: controller.signal }
            );

            if (isMounted) {
                setLandSurveys(adaptLandSurvey(response.data, 1295, -5));
            }
        };

        getNotifications();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, [axiosPrivate, category]);

    return (
        <section className="surveys">
            <div className="dflex">
                <div className="fgrow">
                <h3>Notificaciones</h3>
                    <label htmlFor="notif-category">Categoria de notificaciones</label>
                    <Select
                        id="notif-category"
                        styles={select2Styles}
                        options={notifcationCategories}
                        onChange={setCategory}
                        placeholder="Seleccione categoria"
                    />
                </div>
                <div className="fgrow"></div>
            </div>
            <LandSurveyTable landSurveys={landSurveys} />
        </section>
    );
};

export default Notifications;
