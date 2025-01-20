import { useEffect, useState } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import {InputOption} from "../models/InputOption";
import axios from "axios";

const useFetchOptions = (url: string): InputOption[] => {
    const axiosPrivate = useAxiosPrivate();
    const [data, setData] = useState<InputOption[]>([]);

    useEffect(() => {
        let isMounted = true;
        const controller = new AbortController();

        const fetchData = async () => {
            try {
                const response = await axiosPrivate.get(url, {
                    signal: controller.signal,
                });
                if (isMounted && response.data) {
                    setData(response.data);
                }
            } catch (err) {
                if (axios.isCancel(err)) {
                    return
                } else {
                    console.error(err);
                }
            }
        };

        fetchData();

        return () => {
            isMounted = false;
            controller.abort();
        };
    }, [axiosPrivate, url]);

    return data;
};

export default useFetchOptions;
