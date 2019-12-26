import React from 'react';

import { Page } from '../Page/Page';
import { Query } from '../../models/query';
import { Session } from '../../models/session';
import { Area } from '../../components/Area/Area';
import { AreaForm } from '../../components/AreaForm/AreaForm';
import { HistoryTable } from '../../components/HistoryTable/HistoryTable';
import { htmlInputStateDispatcher } from '../../utils/htmlInputStateDispatcher';
import { stateDispatcher } from '../../utils/stateDispatcher';
import { areaFormConnect } from '../../components/AreaForm/connector';
import { areaConnect } from '../../components/Area/connector';

const AreaContainer = areaConnect(Area);
const AreaFormContainer = areaFormConnect(AreaForm);

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
            <div>
                <AreaContainer r={r} history={history} />

                <AreaFormContainer r={r} dispatchR={htmlInputStateDispatcher(this, 'r', Number)}
                          dispatchHistory={stateDispatcher(this, 'history')} />

                <HistoryTable r={r} history={history} />
            </div>
        );
    }
}
