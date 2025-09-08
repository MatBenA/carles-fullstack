import { create } from "zustand";
import { minPrice } from "../utilities/landSurveyCalcs";

export const useFilterStore = create(state => ({
  minPrice: 0,
  maxPrice: 0,
  businessEvaluation: null,
  section: null,
  zone: null,
  agency: null,
  manager: null,
  surveyor: null,
  particular: null,
  folder: null,
  classification: null,
  title: null,
  address: null,
  rescinded: null,

  setMinPrice: (price: number) => set(state => minPrice = price)
  setMaxPrice
  setBusinessEvaluation
  setSection
  setZone
  setAgency
  setManager
  setSurveyor
  setParticular
  setFolder
  setClassification
  setTitle
  setAddress
  setRescinded
}))