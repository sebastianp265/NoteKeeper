import React from 'react';

interface IProps {
    initialContent: string,
    onContentChange: (newContent: string) => void
}

function ResponsiveTextarea({initialContent, onContentChange}: Readonly<IProps>) {
    const handleOnContentChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        const newContent = event.target.value;
        event.target.style.height = 'auto';
        event.target.style.height = event.target.scrollHeight + 2 + 'px';

        onContentChange(newContent)
    }

    return (
        <textarea
            value={initialContent}
            onChange={handleOnContentChange}
            className="textarea textarea-bordered resize-none scroll"
        />
    );
}

export default ResponsiveTextarea;