import FilterOptions from "../models/FilterOptions";
import {InputOption} from "../../../models/InputOption";

const filterOptionsAdapter = (
    rawFilterOptions: RawFilterOptions
): FilterOptions => {
    const mapToOption = (rawOptions: Array<RawOption>): Array<InputOption> => {
        return rawOptions.map((option) => ({
            value: option.id,
            label: option.name,
        }));
    };

    return {
        agencyOptions: mapToOption(rawFilterOptions.agencyList),
        particularOptions: mapToOption(rawFilterOptions.particularList),
        sectionOptions: mapToOption(rawFilterOptions.sectionList),
        zoneOptions: mapToOption(rawFilterOptions.zoneList),
    };
};

interface RawFilterOptions {
    sectionList: Array<RawOption>;
    zoneList: Array<RawOption>;
    agencyList: Array<RawOption>;
    particularList: Array<RawOption>;
}

interface RawOption {
    id: string;
    name: string;
}

export default filterOptionsAdapter;