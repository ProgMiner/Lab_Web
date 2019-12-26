import React from 'react';
import { Redirect } from 'react-router-dom';

export interface GuardProps {

    isAllowed: boolean;
    redirectUrl?: string;
}

export class Guard extends React.Component<React.PropsWithChildren<GuardProps>> {
    render() {
        const { isAllowed, redirectUrl, children } = this.props;

        if (!isAllowed) {
            if (redirectUrl) {
                return (<Redirect to={redirectUrl} />);
            }

            return null;
        }

        return children;
    }
}
