export interface InputOption {
    value: string;
    label: string;
}

export const emptyInputOption = (): InputOption => ({
    value: "",
    label: "",
});
