import { InputOption } from "../models/InputOption";

export default interface Assessment {
    assessor: InputOption;
    price: number;
    currency: string;
}

export const emptyAssessment = (): Assessment => ({
    assessor: {value: crypto.randomUUID(), label: ""},
    price: 0,
    currency: "USD",
});
