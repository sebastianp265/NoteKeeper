import {useAppSelector} from "../../hooks.ts";

interface IProps {
    labelId: number
}

export const LabelBadge = ({labelId}: Readonly<IProps>) => {
    const labelName = useAppSelector(state => state.labels.entities[labelId].name)

    return (
        <div className="badge">{labelName}</div>
    )
}