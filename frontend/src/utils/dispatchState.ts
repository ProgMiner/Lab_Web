import React from 'react';

export function dispatchState<T = string>(component: React.Component, fieldName: string, value: T) {
    component.setState({ ...component.state, [fieldName]: value });
}
