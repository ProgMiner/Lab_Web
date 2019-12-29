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
import { backendApiUserNotifyWrapper } from '../../utils/backendApiUserNotifyWrapper';

const AreaContainer = areaConnect(Area);
const AreaFormContainer = areaFormConnect(AreaForm);
const ForAuthorizedGuard = forAuthorizedConnect(Guard);

export interface AreaPageProps {

    session: Session | null;

    onSubmitQuery(x: string, y: string, r: string, session: Session, addPoint: (result: boolean) => void): void;
    signOut(): void;
}

interface AreaPageState {

    formPoint: {
        x: string;
        y: string | null;
    }

    r: string;
    history: Query[];
}

export class AreaPage extends Page<AreaPageProps, AreaPageState> {

    state: AreaPageState = {
        formPoint: {
            x: '0',
            y: null
        },

        r: '0',
        history: []
    };

    componentDidMount(): void {
        setInterval(async () => {
            const { session } = this.props;

            if (session == null) {
                return;
            }

            const response = await authorizedBackendApi('history/get', session);
            if (!response.ok) {
                if (response.status === 401) {
                    backendApiUserNotifyWrapper(Promise.resolve(response));
                }

                return;
            }

            const history = await response.json();
            this.setState({ ...this.state, history });
        }, 1000);
    }

    private submitQuery(x: string, y: string) {
        const { session, onSubmitQuery } = this.props;
        const { r } = this.state;

        if (session != null) {
            onSubmitQuery(x, y, r, session, result => {
                const { history } = this.state;

                if (history.length > 0) {
                    const lastQuery = history[history.length - 1];

                    if (lastQuery.x === x && lastQuery.y === y && lastQuery.r === r) {
                        return;
                    }
                }

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

    private dispatchFormPoint<T extends string | null>(field: string, value: T) {
        const { formPoint } = this.state;

        this.setState({
            ...this.state,

            formPoint: {
                ...formPoint,

                [field]: value
            }
        })
    }

    renderContent() {
        const { formPoint, r, history } = this.state;

        const historyTable = (<HistoryTable history={this.state.history} />);

        return (
            <ForAuthorizedGuard redirectUrl="/">
                <div className="area-page-main">
                    <div className="area-container">
                        <AreaContainer formPoint={formPoint} r={r} history={history}
                                       submitQuery={this.submitQuery.bind(this)} />
                    </div>

                    <div className="area-form-container">
                        <AreaFormContainer x={formPoint.x} y={formPoint.y} r={r}
                                           dispatchX={(x: string) => this.dispatchFormPoint('x', x)}
                                           dispatchY={(y: string | null) => this.dispatchFormPoint('y', y)}
                                           dispatchR={stateDispatcher(this, 'r')}
                                           dispatchHistory={stateDispatcher(this, 'history')}
                                           submitQuery={this.submitQuery.bind(this)} />
                    </div>
                </div>

                {history.length > 0 ? historyTable : null}
            </ForAuthorizedGuard>
        );
    }
}
