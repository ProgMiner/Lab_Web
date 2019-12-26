import { Action } from '../../models/action';
import { Session } from '../../models/session';

export const SIGN_IN = 'SIGN_IN';
export const SIGN_OUT = 'SIGN_OUT';

export const LOCK = 'LOCK';
export const UNLOCK = 'UNLOCK';

export interface SignInAction extends Action<typeof SIGN_IN> {

    payload: Session;
}

export interface SignOutAction extends Action<typeof SIGN_OUT> {}

export interface LockAction extends Action<typeof LOCK> {}
export interface UnlockAction extends Action<typeof UNLOCK> {}

export type ApplicationAction = SignInAction | SignOutAction | LockAction | UnlockAction;

export function signIn(session: Session): SignInAction {
    return { type: SIGN_IN, payload: session };
}

export function signOut(): SignOutAction {
    return { type: SIGN_OUT };
}

export function lock(): LockAction {
    return { type: LOCK };
}

export function unlock(): UnlockAction {
    return { type: UNLOCK };
}
