import {useAppSelector} from "../../hooks.ts";
import {selectNoteById} from "./notesSlice.ts";

interface IProps {
    noteId: number
    onClick: () => void
}

function NoteCard({noteId, onClick}: Readonly<IProps>) {
    const {title, content, labelIds} = useAppSelector(state => selectNoteById(state, noteId))
    const labelNames = useAppSelector(state => labelIds.map(labelId => state.labels.entities[labelId].name))

    return (
        <button onClick={onClick}
                className="btn-ghost card-compact card-bordered rounded-xl basis-[calc(25%-1rem)]"
        >
            <div className="card-body min-h-full">
                <div className="text-left card-title bw">{title}</div>
                {
                    content !== null &&
                    <div className="text-left flex-grow bw">{content}</div>
                }
                {
                    labelNames.length > 0 &&
                    <div className="flex pt-2 justify-end">
                        {
                            labelNames.map(
                                (labelName) => <div key={labelName} className="badge">{labelName}</div>)
                        }
                    </div>
                }

            </div>

        </button>
    );
}

export default NoteCard;