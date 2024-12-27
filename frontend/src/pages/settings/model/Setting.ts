export interface Setting {
    name: string;
    value: string;
}

export const emptySetting = (): Setting => ({
    name: "",
    value: "",
});
