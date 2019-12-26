import { SIGN_OUT, SessionAction } from './actions';
import { Session } from '../../models/session';

export interface SessionState {

    session: Session | null;
}

export const initialState: SessionState = {

    session: null
};

export function reducer(state: SessionState = initialState, action: SessionAction): SessionState {
    if (action.type === SIGN_OUT) {
        return { ...state, session: null };
    }

    return state;
}
