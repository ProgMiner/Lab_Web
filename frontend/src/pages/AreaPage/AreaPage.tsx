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
import { authorizedBackendApi } from '../../utils/backendApi';

const AreaContainer = areaConnect(Area);
const AreaFormContainer = areaFormConnect(AreaForm);
const ForAuthorizedGuard = forAuthorizedConnect(Guard);

export interface AreaPageProps {

    session: Session | null;

    onSubmitQuery(x: number, y: number, r: number, session: Session, addPoint: (result: boolean) => void): void;
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

    componentDidMount(): void {
        const interval = setInterval(async () => {
            const { session } = this.props;

            if (session == null) {
                return;
            }

            const response = await authorizedBackendApi('history/get', session);
            if (!response.ok) {
                if (response.status === 401) {
                    clearInterval(interval);
                }

                return;
            }

            const history = await response.json();
            this.setState({ ...this.state, history });
        }, 1000);
    }

    private submitQuery(x: number, y: number) {
        const { session, onSubmitQuery } = this.props;
        const { r } = this.state;

        if (session != null) {
            onSubmitQuery(x, y, r, session, result => {
                this.setState({
                    ...this.state,

                    history: [
                        ...this.state.history,

                        {x, y, r, result}
                    ]
                });
            });
        }
    }

    renderContent() {
        const { r, history } = this.state;

        const historyTable = (<HistoryTable history={this.state.history} />);

        return (
            <ForAuthorizedGuard redirectUrl="/">
                <div className="area-page-main">
                    <div className="area-container">
                        <AreaContainer r={r} history={history} submitQuery={this.submitQuery.bind(this)} />
                    </div>

                    <div className="area-form-container">
                        <AreaFormContainer r={r} dispatchR={stateDispatcher(this, 'r')}
                                           dispatchHistory={stateDispatcher(this, 'history')}
                                           submitQuery={this.submitQuery.bind(this)} />
                    </div>
                </div>

                {history.length > 0 ? historyTable : null}
            </ForAuthorizedGuard>
        );
    }
}
