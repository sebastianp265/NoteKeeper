import axios, {AxiosError} from "axios";

export const backendApi = (mapping: string) => {
    const client = axios.create({
        baseURL: "http://localhost:8080" + mapping,
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json'
        }
    });

    client.interceptors.request.use(request => {
        console.log("Making request:")
        console.log(request)
        return request
    })

    client.interceptors.response.use(
        response=> {
            console.log("Got response: ")
            console.log(response)
            return response;
        },
        (error: AxiosError) => {
            if(error.request) {
                console.error(error.request)
            } else {
                console.error("Request was not made: ", error.message)
            }
            return Promise.reject(error)
        }
    )

    return client
}