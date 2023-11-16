import axios from "axios";

export const backendApi = (mapping: string) => {
    const client = axios.create({
        baseURL: "http://localhost:8080" + mapping,
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json'
        }
    });

    client.interceptors.response.use(
        response => {
            return response;
        },
        error => {
            console.log('An error occurred while calling backend', error)
            return Promise.reject("error response status: " + error.response.status)
        }
    )

    return client
}