interface IProps {
    title: string
    content: string
    labelNames: string[]
    onClick: () => void
}

function NoteCard({title, content, labelNames, onClick}: Readonly<IProps>) {

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