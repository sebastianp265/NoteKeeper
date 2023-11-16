import {backendApi} from "./backendApi.ts";

const labelClient = backendApi('/labels')

interface Label {
    name: string,
    description: string
}


export const labelApi = {
    getAll() {
        console.log('Get all labels')
        return labelClient.get<Label[]>('')
    },

    getById(id: number) {
        console.log('Get note', id)
        return labelClient.get<Label>(`/${id}`)
    },

    create(label: Label) {
        console.log('Create label', label)
        return labelClient.post<Label>('')
    },

    update(name: string, label: Label) {
        console.log('Update note with name ' + name + ' and body ' + label)
        return labelClient.put<Label>(`/${name}`)
    },

    delete(name: string) {
        console.log('Delete note with name ' + name)
        return labelClient.delete<void>(`/${name}`)
    }
}