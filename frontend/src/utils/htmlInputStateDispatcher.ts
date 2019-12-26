import React from 'react';

import { dispatchState } from './dispatchState';

export function htmlInputStateDispatcher<T>(component: React.Component, fieldName: string, caster: (value: string) => T) {
    return (event: React.FormEvent<HTMLInputElement>) => {
        const { target } = event;

        if (target instanceof HTMLInputElement && target.value) {
            setTimeout(() => dispatchState<T>(component, fieldName, caster(target.value)), 1);
        }
    }
}
