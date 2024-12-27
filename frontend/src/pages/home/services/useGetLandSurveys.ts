import { useState, useEffect } from "react";
import { AxiosInstance } from "axios";

interface LandSurvey {
  id: number;
  date: string;
  address: string;
  corner: boolean;
  title: boolean;
  [key: string]: any;
}

interface UseGetLandSurveysParams {
  axiosPrivate: AxiosInstance;
  searchParams: Record<string, any>;
}

const useGetLandSurveys = ({ axiosPrivate, searchParams }: UseGetLandSurveysParams) => {
  const [landSurveys, setLandSurveys] = useState<LandSurvey[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const controller = new AbortController();

    const fetchLandSurveys = async () => {
      setLoading(true);
      setError(null);

      try {
        const response = await axiosPrivate.get("/land-surveys", {
          signal: controller.signal,
          params: searchParams,
        });
        setLandSurveys(response.data);
      } catch (err: any) {
        if (!controller.signal.aborted) {
          setError(err.message || "An error occurred while fetching data.");
        }
      } finally {
        setLoading(false);
      }
    };

    fetchLandSurveys();

    return () => {
      controller.abort();
    };
  }, [axiosPrivate, searchParams]);

  return { landSurveys, loading, error };
};

export default useGetLandSurveys;