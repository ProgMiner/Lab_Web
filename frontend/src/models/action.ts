import { Action as ReduxAction } from 'redux';

export interface Action<T> extends ReduxAction<T> {

    payload?: any;
    meta?: any;
}
