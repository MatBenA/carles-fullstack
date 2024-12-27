export interface InputOption {
    value: string;
    label: string;
    __isNew__?: boolean | undefined;
}

export const emptyInputOption = (): InputOption => ({
    value: "",
    label: "",
});
