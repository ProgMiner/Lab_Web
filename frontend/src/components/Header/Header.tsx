import React from 'react';
import { Button } from 'primereact/button';

import { Session } from '../../models/session';

import './Header.css';

export interface HeaderProps {

    locked: boolean;
    session: Session | null;

    onSignOut(session: Session): void;
}

export class Header extends React.Component<HeaderProps> {

    private onSignOut() {
        const { session, onSignOut } = this.props;

        if (session != null) {
            onSignOut(session);
        }
    }

    render() {
        const { locked, session } = this.props;

        return (
            <header className="main-header">
                <div className="logo">Lab4_Web</div>

                {session && (
                    <div id="logout-button">
                        <Button label="Sign out" className="p-button-secondary" disabled={locked}
                                onClick={this.onSignOut.bind(this)} />
                    </div>
                )}
            </header>
        );
    }
}
