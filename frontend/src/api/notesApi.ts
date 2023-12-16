import {backendApi} from "./backendApi.ts";

const noteClient = backendApi('/notes')

export type NoteGet = {
    id: number,
    title: string,
    content: string,
    labelNames: string[]
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
    }
}