import Select, { ActionMeta, SingleValue } from "react-select";
import select2Styles from "../styled-components/select2style";
import { InputOption } from "../models/InputOption";
import Assessment from "../models/Assessment";

type AssessmentListProps = {
    userOptions: InputOption[];
    assessmentList: Assessment[];
    setAssessmentList: React.Dispatch<React.SetStateAction<Assessment[]>>;
}

//TODO REFACTOR SEPARATE THIS FUNCTION IN THREE DIFFERENT FUNCTIONS, FOR PRICE, ASSESSOR AND CURRENCY
const AssessmentList = ({ userOptions, assessmentList, setAssessmentList }: AssessmentListProps) => {
    const handleAssessmentChange = (
        email: string | undefined,
        field: string,
        value: InputOption | number | string | null
    ) => {
        setAssessmentList((prevList: Assessment[]) =>
            prevList.map((assessment: Assessment) =>
                assessment.assessor.value === email
                    ? { ...assessment, [field]: value }
                    : assessment
            )
        );
    };

    const addAssessment = (): void =>
        setAssessmentList([
            ...assessmentList,
            {
                assessor: { value: crypto.randomUUID(), label: "" },
                price: 0,
                currency: "USD",
            },
        ]);

    const removeAssessment = (id: string | undefined) => {
        const auxList = [...assessmentList];
        setAssessmentList(
            auxList.filter((assessment) => assessment.assessor.value !== id)
        );
    };

    return (
        <div className="assessment">
            {/* ASSESSMENTS = CURRENCY -> SELECT */}
            <div>Tasaciones</div>{" "}
            <div>
                <button
                    className="transparent-green-btn"
                    type="button"
                    onClick={addAssessment}
                >
                    Añadir Tasación
                </button>
            </div>
            {assessmentList.map((assessment) => (
                <div className="dflex gap-30" key={assessment.assessor.value}>
                    <div className="f-stretch">
                        <label
                            htmlFor={assessment.assessor.value + "-assessor"}
                        >
                            Asesor
                        </label>
                        <Select
                            id={assessment.assessor.value + "-assessor"}
                            styles={select2Styles}
                            options={userOptions}
                            name="assessor"
                            value={assessment.assessor}
                            onChange={(newValue: SingleValue<InputOption>, actionMeta: ActionMeta<InputOption>) =>
                                handleAssessmentChange(
                                    assessment.assessor.value,
                                    "assessor",
                                    newValue
                                )
                            }
                            required={true}
                        />
                    </div>
                    <div className="f-stretch">
                        <label htmlFor={assessment.assessor.value + "-price"}>
                        Tasación
                        </label>
                        <input
                            id={assessment.assessor.value + "-price"}
                            type="number"
                            name="assessment-price"
                            value={assessment.price}
                            onChange={(e) =>
                                handleAssessmentChange(
                                    assessment.assessor.value,
                                    "price",
                                    parseInt(e.target.value)
                                )
                            }
                            required={true}
                        />
                    </div>
                    <div className="f-stretch">
                        <label
                            htmlFor={assessment.assessor.value + "-currency"}
                        >
                            Divisa
                        </label>
                        <select
                            id={assessment.assessor.value + "-currency"}
                            name="assessment-currency"
                            value={assessment.currency}
                            onChange={(e) =>
                                handleAssessmentChange(
                                    assessment.assessor.value,
                                    "currency",
                                    e.target.value
                                )
                            }
                            required={true}
                        >
                            <option value="USD">USD</option>
                            <option value="ARS">ARS</option>
                        </select>
                    </div>
                    {assessmentList.length > 2 && (
                        <button
                            className="transparent-red-btn remove-btn"
                            type="button"
                            onClick={() =>
                                removeAssessment(assessment.assessor.value)
                            }
                        >
                            Remover
                        </button>
                    )}
                </div>
            ))}
        </div>
    );
};

export default AssessmentList;
