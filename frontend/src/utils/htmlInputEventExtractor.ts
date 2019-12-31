import React from 'react';

export function htmlInputEventExtractor(event: React.FormEvent<HTMLInputElement>) {
    const { target } = event;

    if (target instanceof HTMLInputElement) {
        return target.value;
    }

    return null;
}
