import {backendApi} from "./backendApi.ts";

const noteClient = backendApi('/notes')

export type NoteGet = {
    id: number,
    title: string,
    content: string,
    labelIds: number[]
}

export type NotePost = {
    title: string,
    content: string
}

export type NotePut = {
    id: number,
    title: string,
    content: string
}

export const notesApi = {
    getAll() {
        return noteClient.get<NoteGet[]>('')
    },

    getById(id: number) {
        return noteClient.get<NoteGet>(`/${id}`)
    },

    create(note: NotePost) {
        return noteClient.post<NoteGet>('', note)
    },

    update(id: number, note: NotePut) {
        return noteClient.put<NoteGet>(`/${id}`, note)
    },

    delete(id: number) {
        return noteClient.delete<void>(`/${id}`)
    },

    getAllByLabelName(labelName: string) {
        return noteClient.get<NoteGet[]>(`/by-label-name/${labelName}`)
    },

    addLabelToNote(noteId: number, labelId: number) {
        return noteClient.put<NoteGet>(`/${noteId}/add-label/${labelId}`)
    },

    removeLabelFromNote(noteId: number, labelId: number) {
        return noteClient.put<NoteGet>(`/${noteId}/remove-label/${labelId}`)
    }
}