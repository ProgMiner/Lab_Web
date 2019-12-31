import React from 'react';

import { Session } from '../../models/session';
import { headerConnect } from '../../components/Header/connector';
import { Header } from '../../components/Header/Header';
import { Layout } from '../../components/Layout/Layout';

const HeaderContainer = headerConnect(Header);

export interface PageProps {

    session: Session | null;
}

export abstract class Page<P = {}, S = {}> extends React.Component<P & PageProps, S> {

    abstract renderContent(): React.ReactNode;

    render() {
        return (
            <div>
                <HeaderContainer />

                <Layout>
                    {this.renderContent()}
                </Layout>
            </div>
        );
    }
}
