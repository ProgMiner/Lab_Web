import React from 'react';

import './Layout.css';

export class Layout extends React.Component {
    render() {
        const { children } = this.props;

        return (
            <div className="container">
                {children}
            </div>
        );
    }
}
