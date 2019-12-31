import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';

import { routes } from '../../routes';
import { Session } from '../../models/session';

import './App.css';

export interface AppProps {

    session: Session | null;

    checkSession(session: Session): void;
}

export class App extends React.Component<AppProps> {

    componentDidMount(): void {
        const { session, checkSession } = this.props;

        if (session != null) {
            checkSession(session);
        }
    }

    render() {
        return (<Router>{routes}</Router>);
    }
}
