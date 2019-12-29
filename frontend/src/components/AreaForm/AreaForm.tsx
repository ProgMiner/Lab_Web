import React from 'react';
import { compose } from 'redux';
import { Slider } from 'primereact/slider';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Panel } from 'primereact/panel';

import { Query } from '../../models/query';

import './AreaForm.css';

const SLIDER_ZOOM = 1e10;

export interface AreaFormProps {

    locked: boolean;

    x: string;
    y: string | null;
    r: string;

    dispatchX: (value: string) => void;
    dispatchY: (value: string | null) => void;
    dispatchR: (value: string) => void;
    dispatchHistory: (history: Query[]) => void;

    submitQuery(x: string, y: string): void;
}

export class AreaForm extends React.Component<AreaFormProps> {

    private onCheck(event: React.FormEvent) {
        const { x, y, submitQuery } = this.props;

        if (y != null) {
            submitQuery(x, y);
        }

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

    private static verifyY(value: string): string | null {
        value = value.trim();

        if (value.length === 0) {
            return null;
        }

        for (const key in AreaForm.replacements) {
            value = value.replace(new RegExp(key, 'g'), '' + AreaForm.replacements[key]);
        }

        value = value.split(' ').join('');

        const numeric = +value;
        if (isNaN(numeric)) {
            return null;
        }

        return value;
    }

    private static validateY(value: string | null): string | null {
        if (value == null) {
            return null;
        }

        const dotPosition = value.indexOf('.');
        if ((dotPosition >= 0 ? dotPosition : value.length) > 10) {
            return null;
        }

        let preparedValue = value;
        if (dotPosition >= 0) {
            preparedValue = value.substring(0, Math.min(dotPosition + 11, value.length));
        }

        const numeric = +preparedValue;
        if (isNaN(numeric) || numeric <= -5 || numeric >= 3) {
            return null;
        }

        return value;
    }

    private static normalizeSlider(value: string): string {
        return '' + (+value / SLIDER_ZOOM);
    }

    private static onChange<T>(dispatch: (value: T) => void, filter: (value: string) => T): (event: { value: any }) => void {
        return function (event: { value: any }) {
            const { value } = event as { value: string };

            if (value != null) {
                dispatch(filter(value));
            }
        };
    }

    private static onChangeFormInput<T>(dispatch: (value: T) => void, filter: (value: string) => T):
        (event: React.FormEvent<HTMLInputElement>) => void {
        return function (event: React.FormEvent<HTMLInputElement>) {
            const { target } = event;

            if (target instanceof HTMLInputElement && target.value) {
                const { value } = target;

                setTimeout(() => dispatch(filter(value)), 1);
            }
        };
    }

    render() {
        const { locked, x, y, r, dispatchX, dispatchY, dispatchR } = this.props;

        return (
            <Panel header="New query" className="area-form">
                <form onSubmit={this.onCheck.bind(this)}>
                    <div className="form-group">
                        <label>X: {x}</label>
                        <Slider min={-5 * SLIDER_ZOOM + 1} max={3 * SLIDER_ZOOM - 1} step={1} value={+x * SLIDER_ZOOM}
                                disabled={locked} onChange={AreaForm.onChange(dispatchX, AreaForm.normalizeSlider)} />
                    </div>

                    <div className="form-group max-width">
                        <label>Y:&nbsp;</label>
                        <InputText data-invalid={y == null} placeholder="(-5, 3)" disabled={locked} onInput={AreaForm
                            .onChangeFormInput(dispatchY, compose(AreaForm.validateY, AreaForm.verifyY))} />
                    </div>

                    <div className="form-group">
                        <label>R: {r}</label>
                        <Slider min={-5 * SLIDER_ZOOM + 1} max={3 * SLIDER_ZOOM - 1}
                                step={1} disabled={locked} value={+r * SLIDER_ZOOM}
                                onChange={AreaForm.onChange(dispatchR, AreaForm.normalizeSlider)} />
                    </div>

                    <Button type="submit" disabled={y == null} label="Check" />
                </form>
            </Panel>
        );
    }
}
