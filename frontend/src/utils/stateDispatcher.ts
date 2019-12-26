import React from 'react';

import { dispatchState } from './dispatchState';

export function stateDispatcher<T>(component: React.Component, fieldName: string) {
    return (value: T) => dispatchState<T>(component, fieldName, value);
}
