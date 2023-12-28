import {useAppDispatch, useAppSelector} from "../../hooks.ts";
import {createLabel} from "./labelsSlice.ts";
import {useRef} from "react";
import {addLabelToNote, removeLabelFromNote} from "../notes/notesSlice.ts";

interface IProps {
    noteId: number
}

export const LabelDropdown = ({noteId}: Readonly<IProps>) => {
    const allLabelsWithIsChecked = useAppSelector(
        (state) => state.labels.ids.map(
            id => {
                return {
                    ...state.labels.entities[id]!,
                    isChecked: state.notes.entities[noteId]?.labelIds.includes(id)
                }
            }
        )
    )

    const dispatch = useAppDispatch()

    const labelNameInputRef = useRef<HTMLInputElement>(null)

    const onAddLabelClicked = () => {
        const labelName = labelNameInputRef.current?.value
        if (labelName !== undefined) {
            dispatch(createLabel({name: labelName}))
            labelNameInputRef.current!.value = ''
        }
    }

    const onLabelCheckboxChange = (isChecked: boolean, labelId: number) => {
        if (isChecked) {
            dispatch(addLabelToNote({noteId, labelId}))
        } else {
            dispatch(removeLabelFromNote({noteId, labelId}))
        }
    }

    return (
        <div className="dropdown">
            <div tabIndex={0} role="button" className="btn m-2">Edit labels</div>
            <ul tabIndex={0} className="text-sm dropdown-content z-[1] menu shadow bg-base-100 rounded-box">
                {
                    allLabelsWithIsChecked.map(({id, name, isChecked}) =>
                        <li key={id}>
                            <div className="flex p-0 pl-2 pr-2">
                                <label className="label cursor-pointer ml-0 pl-0 w-full">
                                    <input type="checkbox"
                                           defaultChecked={isChecked}
                                           className="checkbox checkbox-sm ml-0"
                                           onChange={(e) =>
                                               onLabelCheckboxChange(e.target.checked, id)}/>
                                    <span className="label-text text-sm pl-4 flex-grow">{name}</span>
                                </label>
                            </div>
                        </li>
                    )
                }
                <li>
                    <div className="flex h-[36px] p-0 pl-2 pr-2">
                        <svg onClick={onAddLabelClicked}
                             className="h-5 stroke-neutral-content"
                             xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
                             strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
                            <line x1="12" y1="5" x2="12" y2="19"></line>
                            <line x1="5" y1="12" x2="19" y2="12"></line>
                        </svg>
                        <input ref={labelNameInputRef} className="input text-sm h-fit pl-2"
                               placeholder="Label name"/>
                    </div>
                </li>
            </ul>
        </div>
    )
};