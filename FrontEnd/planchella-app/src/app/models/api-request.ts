export interface ApiRequest<T> {
    data: T; // The payload to be sent in the request
    headers?: Record<string, string>; // Optional headers for the request
    params?: Record<string, string | number>; // Optional query parameters
}