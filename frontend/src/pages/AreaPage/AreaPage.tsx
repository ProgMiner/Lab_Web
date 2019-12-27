import React from 'react';

import { Page } from '../Page/Page';
import { Query } from '../../models/query';
import { Session } from '../../models/session';
import { Area } from '../../components/Area/Area';
import { AreaForm } from '../../components/AreaForm/AreaForm';
import { HistoryTable } from '../../components/HistoryTable/HistoryTable';
import { stateDispatcher } from '../../utils/stateDispatcher';
import { areaFormConnect } from '../../components/AreaForm/connector';
import { areaConnect } from '../../components/Area/connector';
import { forAuthorizedConnect } from '../../components/Guard/forAuthorizedConnect';
import { Guard } from '../../components/Guard/Guard';

import './AreaPage.css';

const AreaContainer = areaConnect(Area);
const AreaFormContainer = areaFormConnect(AreaForm);
const ForAuthorizedGuard = forAuthorizedConnect(Guard);

export interface AreaPageProps {

    session: Session | null;
}

interface AreaPageState {

    r: number;
    history: Query[];
}

export class AreaPage extends Page<AreaPageProps, AreaPageState> {

    state: AreaPageState = {
        r: 0,
        history: []
    };

    renderContent() {
        const { r, history } = this.state;

        return (
            <ForAuthorizedGuard redirectUrl="/">
                <div className="area-page-main">
                    <div className="area-container">
                        <AreaContainer r={r} history={history} />
                    </div>

                    <div className="area-form-container">
                        <AreaFormContainer
                            r={r}
                            dispatchR={stateDispatcher(this, 'r')}
                            dispatchHistory={stateDispatcher(this, 'history')}
                        />
                    </div>
                </div>

                <HistoryTable history={[
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                    { x: 1, y: 2, r: 3, result: true },
                ]} />
            </ForAuthorizedGuard>
        );
    }
}
