import {backendApi} from "./backendApi.ts";

const noteClient = backendApi('/notes')

interface NoteGet {
    id: number,
    title: string,
    content: string,
    labelNames: string[]
}

interface NotePost {
    title: string,
    content: string
}

interface NotePut {
    id: number,
    title: string,
    content: string
}

export const notesApi = {
    getAll() {
        console.log('Get all notes')
        return noteClient.get<NoteGet[]>('')
    },

    getById(id: number) {
        console.log('Get note', id)
        return noteClient.get<NoteGet>(`/${id}`)
    },

    create(note: NotePost) {
        console.log('Create note', note)
        return noteClient.post<NotePost>('')
    },

    update(id: number, note: NotePut) {
        console.log('Update note with ' + id + ' and body ' + note)
        return noteClient.put<NotePut>(`/${id}`)
    },

    delete(id: number) {
        console.log('Delete note with ' + id)
        return noteClient.delete<void>(`/${id}`)
    }
}