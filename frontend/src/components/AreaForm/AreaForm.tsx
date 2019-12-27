import React from 'react';
import { compose } from 'redux';
import { Slider } from 'primereact/slider';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Panel } from 'primereact/panel';

import { Query } from '../../models/query';
import { htmlInputStateDispatcher } from '../../utils/htmlInputStateDispatcher';
import { valueStateDispatcher } from '../../utils/valueStateDispatcher';

import './AreaForm.css';

const SLIDER_ZOOM = 1e10;

export interface AreaFormProps {

    locked: boolean;

    r: number;

    dispatchR: (value: number) => void;
    dispatchHistory: (history: Query[]) => void;

    submitQuery(x: number, y: number): void;
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
        const { submitQuery } = this.props;
        const { x, y } = this.state;

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

    private static normalizeSlider(value: number) {
        return value / SLIDER_ZOOM;
    }

    private onChangeR(event: { value: any }) {
        const { value } = event as { value: number };

        if (value != null) {
            this.props.dispatchR(AreaForm.normalizeSlider(value));
        }
    }

    render() {
        const { locked, r } = this.props;
        const { x, y } = this.state;

        return (
            <Panel header="Controls" className="area-form">
                <form onSubmit={this.onCheck.bind(this)}>
                    <div className="form-group">
                        <label>X: {x}</label>
                        <Slider min={-5 * SLIDER_ZOOM + 1} max={3 * SLIDER_ZOOM - 1} step={1} value={x * SLIDER_ZOOM}
                                disabled={locked} onChange={valueStateDispatcher(this, 'x',
                            compose(AreaForm.normalizeSlider, Number))} />
                    </div>

                    <div className="form-group max-width">
                        <label>Y:&nbsp;</label>
                        <InputText data-invalid={y == null} placeholder="(-5, 3)" disabled={locked}
                                   onChange={htmlInputStateDispatcher(this, 'y',
                                       compose(AreaForm.validateY, AreaForm.verifyY))} />
                    </div>

                    <div className="form-group">
                        <label>R: {r}</label>
                        <Slider min={-5 * SLIDER_ZOOM + 1} max={3 * SLIDER_ZOOM - 1} step={1} disabled={locked}
                                value={r * SLIDER_ZOOM} onChange={this.onChangeR.bind(this)} />
                    </div>

                    <Button type="submit" disabled={y == null} label="Check" />
                </form>
            </Panel>
        );
    }
}
