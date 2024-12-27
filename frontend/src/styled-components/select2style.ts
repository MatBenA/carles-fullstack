import { StylesConfig } from "react-select";

const select2Styles: StylesConfig = {
    control: (provided) => ({
        ...provided,
        height: "32px", // Sets the height
        minHeight: "32px", // Ensures it doesn't expand vertically
        padding: "0", // Removes internal padding
    }),
    valueContainer: (provided) => ({
        ...provided,
        padding: "0 8px", // Adjusts padding inside the control
    }),
    input: (provided) => ({
        ...provided,
        margin: "0", // Ensures no additional margin
        padding: "0", // Removes internal padding
    }),
    singleValue: (provided) => ({
        ...provided,
        lineHeight: "32px", // Vertically centers the text
    }),
    indicatorsContainer: (provided) => ({
        ...provided,
        height: "32px", // Ensures the dropdown indicator matches the control's height
    }),
};

export default select2Styles;