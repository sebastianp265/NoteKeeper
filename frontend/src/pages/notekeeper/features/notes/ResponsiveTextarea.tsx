import React from 'react';

interface IProps {
    initialContent: string,
    textareaRef: React.Ref<HTMLTextAreaElement>
}

function ResponsiveTextarea({initialContent, textareaRef}: Readonly<IProps>) {
    const handleOnContentChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
        event.target.style.height = 'auto';
        event.target.style.height = event.target.scrollHeight + 2 + 'px';
    }

    return (
        <textarea
            ref={textareaRef}
            defaultValue={initialContent}
            onChange={handleOnContentChange}
            className="textarea textarea-bordered resize-none scroll"
        />
    );
}

export default ResponsiveTextarea;