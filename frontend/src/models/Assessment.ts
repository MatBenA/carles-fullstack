import { InputOption } from "../models/InputOption";
import { v4 as uuidv4 } from "uuid";

export default interface Assessment {
    assessor: InputOption;
    price: number;
    currency: string;
}

export const emptyAssessment = (): Assessment => ({
    assessor: {value: uuidv4(), label: ""},
    price: 0,
    currency: "USD",
});
