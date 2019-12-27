import { backendHost } from '../config';
import { Session } from '../models/session';
import { growl } from '../index';

export async function backendApi(
    method: string,
    httpMethod: string = 'GET',
    data: { [key: string]: any } = {},
    headers: { [key: string]: any } = {},
    init: object = {}
): Promise<Response> {
    const dataArray: string[] = [];

    for (const key in data) {
        dataArray.push(`${encodeURIComponent(key)}=${encodeURIComponent(data[key])}`);
    }

    try {
        const response = await fetch(`//${backendHost}/api/v1/${method}`, {
            ...init,

            method: httpMethod,
            body: ['GET', 'HEAD'].includes(httpMethod.toUpperCase()) ? undefined : dataArray.join('&'),
            headers: {
                ...headers,

                'Content-Type': 'application/x-www-form-urlencoded',
                'Accept': 'application/json'
            }
        });

        if (!response.ok) {
            switch (response.status) {
                case 400:
                    growl.current?.show({
                        severity: 'warn',
                        summary: 'Query error',
                        detail: 'One of your entered values is invalid. Check them and try again, please.'
                    });
                    break;

                case 401:
                    growl.current?.show({
                        severity: 'error',
                        summary: 'Session error',
                        detail: 'Your session is not longer valid. Please, sign out and sign up again.'
                    });
                    break;
            }
        }

        return response;
    } catch (e) {
        growl.current?.show({
            severity: 'error',
            summary: 'Connection error',
            detail: 'Cannot connect to the server, please try again later'
        });

        throw e;
    }
}

export function authorizedBackendApi(
    method: string,
    session: Session,
    httpMethod: string = 'GET',
    data: { [key: string]: any } = {},
    headers: { [key: string]: any } = {},
    init: object = {}
) {
    return backendApi(method, httpMethod, data, { ...headers,
        'X-USER-ID': session.userId, 'X-TOKEN': session.token }, init);
}
