import React from 'react';

import { Query } from '../../models/query';
import { htmlInputStateDispatcher } from '../../utils/htmlInputStateDispatcher';
import { compose } from 'redux';

const SLIDER_STEP = 1e-10;

export interface AreaFormProps {

    locked: boolean;

    r: number;

    dispatchR: (event: React.FormEvent<HTMLInputElement>) => void;
    dispatchHistory: (history: Query[]) => void;
}

interface AreaFormState {
    x: number;
    y: number | null;
}

export class AreaForm extends React.Component<AreaFormProps, AreaFormState> {

    state: AreaFormState = {
        x: 0,
        y: null
    };

    private onCheck(event: React.FormEvent) {
        console.log(this);
        // TODO make check action
        event.preventDefault();
    }

    private static replacements: { [key: string]: string } = {
        'minus': '-',
        'zero': '0',
        'one': '1',
        'two': '2',
        'three': '3',
        'four': '4',
        'five': '5',
        'six': '6',
        'seven': '7',
        'eight': '8',
        'nine': '9',
        'dot': '.',
        'and': '.'
    };

    private static verifyY(value: string): number | null {
        value = value.trim();

        for (const key in AreaForm.replacements) {
            value = value.replace(new RegExp(key, 'g'), '' + AreaForm.replacements[key]);
        }

        value = value.split(' ').join('');

        const dotPosition = value.indexOf('.');
        if ((dotPosition >= 0 ? dotPosition : value.length) > 10) {
            return null;
        }

        if (dotPosition >= 0) {
            value = value.substring(0, Math.min(dotPosition + 11, value.length));
        }

        const numeric = +value;
        if (isNaN(numeric)) {
            return null;
        }

        return numeric;
    }

    private static validateY(value: number | null): number | null {
        if (value == null) {
            return null;
        }

        if (value <= -5 || value >= 3) {
            return null;
        }

        return value;
    }

    render() {
        const { r, dispatchR } = this.props;
        const { x, y } = this.state;

        return (
            <div>
                <form onSubmit={this.onCheck.bind(this)}>
                    <label>X: <input type="range" min={-5 + SLIDER_STEP} max={3 - SLIDER_STEP} step={SLIDER_STEP}
                                     value={x} onChange={htmlInputStateDispatcher(this, 'x', Number)} /> {x}</label>

                    <label>Y: <input type="text" onChange={htmlInputStateDispatcher(this, 'y',
                        compose(AreaForm.validateY, AreaForm.verifyY))} data-invalid={y == null}
                                     placeholder="(-5, 3)" /></label>

                    <label>R: <input type="range" min={-5 + SLIDER_STEP} max={3 - SLIDER_STEP} step={SLIDER_STEP}
                                     value={r} onChange={dispatchR} /> {r}</label>

                    <button type="submit">Check</button>
                </form>
            </div>
        );
    }
}
