import { Action } from '../../models/action';

export const SIGN_IN = 'SIGN_IN';
export const SIGN_OUT = 'SIGN_OUT';

export interface SignInAction extends Action<typeof SIGN_IN> {

    type: typeof SIGN_IN;

    payload: {
        username: string;
        password: string;
    };
}

export interface SignOutAction extends Action<typeof SIGN_OUT> {

    type: typeof SIGN_OUT;
}

export type SessionAction = SignInAction | SignOutAction;

export function singIn(username: string, password: string): SignInAction {
    return {
        type: SIGN_IN,

        payload: { username, password }
    };
}

export function singOut(): SignOutAction {
    return { type: SIGN_OUT };
}
