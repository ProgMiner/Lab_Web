import { Dispatch } from 'redux';

import { lock, unlock } from '../store/application/actions';

export function lockAndDo(dispatch: Dispatch, block: () => void) {
    try {
        dispatch(lock());
        block();
    } finally {
        dispatch(unlock());
    }
}