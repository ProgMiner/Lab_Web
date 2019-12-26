import { ApplicationAction, LOCK, SIGN_IN, SIGN_OUT, UNLOCK } from './actions';
import { Session } from '../../models/session';

export interface ApplicationState {

    locked: boolean;
    session: Session | null;
}

export const initialState: ApplicationState = {

    locked: false,
    session: null
};

export function reducer(state: ApplicationState = initialState, action: ApplicationAction): ApplicationState {
    switch (action.type) {
        case LOCK:
        case UNLOCK:
            return { ...state, locked: action.type === LOCK };

        case SIGN_IN:
            return { ...state, session: action.payload };

        case SIGN_OUT:
            return { ...state, session: null };
    }

    return state;
}
