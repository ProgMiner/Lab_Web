import { Middleware } from 'redux';

import { SIGN_IN, SIGN_OUT } from '../store/application/actions';

export const sessionSaver: Middleware = store => next => action => {
    switch (action.type) {
        case SIGN_IN:
            localStorage.setItem('session', JSON.stringify(action.payload));
            break;

        case SIGN_OUT:
            localStorage.removeItem('session');
            break;
    }

    next(action);
};
