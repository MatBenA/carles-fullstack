import { useEffect, useState } from "react";
import useAxiosPrivate from "../../../hooks/useAxiosPrivate";
import { AxiosError } from "axios";

type ErrorType = Error | null;
type Number = number | null;

interface Props {
  usdExchangeRate: Number;
  repricing: Number;
  maxDeviation: Number;
  loading: boolean;
  error: ErrorType;
}

export const useSettingsData = (): Props => {
  const axiosPrivate = useAxiosPrivate();
  const [usdExchangeRate, setUsdExchangeRate] = useState<Number>(null);
  const [repricing, setRepricing] = useState<Number>(null);
  const [maxDeviation, setMaxDeviation] = useState<Number>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<ErrorType>(null);

  useEffect(() => {
    const controller = new AbortController();
    setLoading(true);
    let isMounted = true;

    const getUsdExchangeRate = async () => {
      try {
        const response = await axiosPrivate.get("/currencies/USD", {
          signal: controller.signal,
        });

        if (response.data.error) {
          throw new Error("Error en la peticion");
        }

        if (isMounted) {
          setUsdExchangeRate(response.data.exchangeReference);
        }
      } catch (err) {
        setError(err as AxiosError);
      } finally {
        setLoading(false);
      }
    };

    const getRepricing = async () => {
      try {
        const response = await axiosPrivate.get(
          "/settings/rePricingPercentaje",
          {
            signal: controller.signal,
          }
        );

        if (response.data.error) {
          throw new Error("Error en la petición.");
        }

        if (isMounted) {
          setRepricing(response.data);
        }
      } catch (err: unknown) {
        setError(err as AxiosError);
      } finally {
        setLoading(false);
      }
    };

    const getMaxDeviation = async () => {
      try {
        const response = await axiosPrivate.get("/settings/maxDeviation", {
          signal: controller.signal,
        });

        if (response.data.error) {
          throw new Error("Error en la petición.");
        }

        if (isMounted) {
          setMaxDeviation(response.data);
        }
        return response.data;
      } catch (err: unknown) {
        setError(err as AxiosError);
      } finally {
        setLoading(true);
      }
    };

    getUsdExchangeRate();
    getRepricing();
    getMaxDeviation();

    return () => {
      controller.abort();
      isMounted = false;
    };
  }, [axiosPrivate]);

  return { usdExchangeRate, repricing, maxDeviation, loading, error };
};
