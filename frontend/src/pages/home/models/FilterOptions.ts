import {InputOption} from "../../../models/InputOption";

interface FilterOptions {
    sectionOptions: Array<InputOption>;
    zoneOptions: Array<InputOption>;
    agencyOptions: Array<InputOption>;
    particularOptions: Array<InputOption>;
}

export default FilterOptions;
