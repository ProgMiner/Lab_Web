import React from 'react';

import { dispatchState } from './dispatchState';

export function valueStateDispatcher<T, R>(component: React.Component, fieldName: string, caster: (value: T) => R) {
    return (event: { value: T }) => {
        const { value } = event;

        if (value) {
            setTimeout(() => dispatchState<R>(component, fieldName, caster(value)), 1);
        }
    }
}
