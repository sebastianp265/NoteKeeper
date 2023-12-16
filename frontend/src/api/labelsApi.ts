import {backendApi} from "./backendApi.ts";

const labelClient = backendApi('/labels')

export type Label = {
    name: string,
    description: string
}

export const labelApi = {
    getAll() {
        return labelClient.get<Label[]>('')
    },

    getById(id: number) {
        return labelClient.get<Label>(`/${id}`)
    },

    create(label: Label) {
        return labelClient.post<Label>('', label)
    },

    update(name: string, label: Label) {
        return labelClient.put<Label>(`/${name}`, label)
    },

    delete(name: string) {
        return labelClient.delete<void>(`/${name}`)
    }
}