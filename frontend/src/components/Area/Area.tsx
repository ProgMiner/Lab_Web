import React from 'react';

import { Query } from '../../models/query';

export interface AreaProps {

    locked: boolean;

    width?: number | string;
    height?: number | string;

    r: number;
    history: Query[];
}

export class Area extends React.Component<AreaProps, {}> {

    static defaultProps = {
        width: 400,
        height: 400
    };

    private onCheck() {
        // TODO make check action
    }

    render() {
        return (<canvas width={this.props.width} height={this.props.height} />);
    }
}
