import { backendHost } from '../config';
import { Session } from '../models/session';

export function backendApi(
    method: string,
    httpMethod: string = 'GET',
    data: { [key: string]: any } = {},
    headers: { [key: string]: any } = {},
    init: object = {}
) {
    const dataArray: string[] = [];

    for (const key in data) {
        dataArray.push(`${encodeURIComponent(key)}=${encodeURIComponent(data[key])}`);
    }

    return fetch(`//${backendHost}/api/v1/${method}`, {
        ...init,

        method: httpMethod,
        body: dataArray.join('&'),
        headers: {
            ...headers,

            'Content-Type': 'application/x-www-form-urlencoded',
            'Accept': 'application/json'
        }
    })
}

export function authorizedBackendApi(
    method: string,
    session: Session,
    httpMethod: string = 'GET',
    data: { [key: string]: any } = {},
    headers: { [key: string]: any },
    init: object = {}
) {
    return backendApi(method, httpMethod, data, { ...headers,
        'X-USER-ID': session.userId, 'X-TOKEN': session.token }, init);
}
