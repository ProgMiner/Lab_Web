import React from 'react';

import { Session } from '../../models/session';

export interface PageProps {

    session: Session | null;
}

export abstract class Page<P = {}, S = {}> extends React.Component<P & PageProps, S> {

    abstract renderContent(): React.ReactNode;

    render() {
        return (
            <>
                <header>Lab4_Web</header>

                {this.renderContent()}
            </>
        );
    }
}
