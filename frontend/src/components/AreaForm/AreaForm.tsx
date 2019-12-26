import React from 'react';

import { Query } from '../../models/query';
import { htmlInputStateDispatcher } from '../../utils/htmlInputStateDispatcher';

const SLIDER_STEP = 1e-10;

interface AreaFormProps {

    r: number;

    dispatchR: (event: React.FormEvent<HTMLInputElement>) => void;
    dispatchHistory: (history: Query[]) => void;
}

interface AreaFormState {
    x: number;
    y: number;
}

export class AreaForm extends React.Component<AreaFormProps, AreaFormState> {

    state: AreaFormState = {
        x: 0,
        y: 0
    };

    private onCheck(event: React.FormEvent) {
        console.log(this);
        // TODO make check action
        event.preventDefault();
    }

    render() {
        const { r, dispatchR } = this.props;
        const { x, y } = this.state;

        return (
            <div>
                <form onSubmit={this.onCheck.bind(this)}>
                    <label>X: <input type="range" min={-5 + SLIDER_STEP} max={3 - SLIDER_STEP} step={SLIDER_STEP}
                                     value={x} onChange={htmlInputStateDispatcher(this, 'x', Number)} /> {x}</label>

                    <label>Y: <input type="range" min={-5 + SLIDER_STEP} max={3 - SLIDER_STEP} step={SLIDER_STEP}
                                     value={y} onChange={htmlInputStateDispatcher(this, 'y', Number)} /> {y}</label>

                    <label>R: <input type="range" min={-5 + SLIDER_STEP} max={3 - SLIDER_STEP} step={SLIDER_STEP}
                                     value={r} onChange={dispatchR} /> {r}</label>

                    <button type="submit">Check</button>
                </form>
            </div>
        );
    }
}
