import {backendApi} from "./backendApi.ts";

const labelClient = backendApi('/labels')

export type LabelGet = {
    id: number,
    name: string
}

export type LabelPost = {
    name: string
}

export type LabelPut = {
    name: string
}

export const labelsApi = {
    getAll() {
        return labelClient.get<LabelGet[]>('')
    },

    getById(id: number) {
        return labelClient.get<LabelGet>(`/${id}`)
    },

    create(label: LabelPost) {
        return labelClient.post<LabelGet>('', label)
    },

    update(id: number, label: LabelPut) {
        return labelClient.put<LabelGet>(`/${id}`, label)
    },

    delete(id: number) {
        return labelClient.delete<void>(`/${id}`)
    }
}