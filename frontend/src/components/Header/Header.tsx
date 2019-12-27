import React from 'react';
import { Button } from 'primereact/button';

import { Session } from '../../models/session';

import './Header.css';

export interface HeaderProps {

    session: Session | null;
}

export class Header extends React.Component<HeaderProps> {

    render() {
        const { session } = this.props;

        return (
            <header className="main-header">
                <div className="logo">Lab4_Web</div>
                {!session && (
                    <div id="logout-button">
                        <Button label="Sign out" className="p-button-secondary" />
                    </div>
                )}
            </header>
        );
    }
}
