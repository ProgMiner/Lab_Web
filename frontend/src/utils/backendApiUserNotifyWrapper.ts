import { growl } from '../index';

export async function backendApiUserNotifyWrapper(responsePromise: Promise<Response>, exclude: number[] = []): Promise<Response> {
    try {
        const response = await responsePromise;

        if (!response.ok && !exclude.includes(response.status)) {
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

        return responsePromise;
    } catch (e) {
        growl.current?.show({
            severity: 'error',
            summary: 'Connection error',
            detail: 'Cannot connect to the server, please try again later'
        });

        throw e;
    }
}
