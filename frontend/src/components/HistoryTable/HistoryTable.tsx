import React from 'react';

import { Query } from '../../models/query';

interface HistoryTableProps {

    pageSize?: number;

    r: number;
    history: Query[];
}

interface HistoryTableState {
    pageNumber: number;
}

export class HistoryTable extends React.Component<HistoryTableProps, HistoryTableState> {

    static defaultProps = {
        pageSize: 20
    };

    state: HistoryTableState = {
        pageNumber: 0
    };

    render() {
        const { pageSize, r, history } = this.props;
        const { pageNumber } = this.state;

        const offset = pageSize! * pageNumber;
        const content = history.slice(offset, offset + pageSize!).map(query => (
            <tr data-r={query.r}>
                <td>{query.x}</td>
                <td>{query.y}</td>
                <td>{query.r}</td>
                <td>{query.result ? '' : 'not '}includes</td>
            </tr>
        ));

        return (
            <table data-r={r}>
                <thead>
                    <tr>
                        <th>X</th>
                        <th>Y</th>
                        <th>R</th>
                        <th>Result</th>
                    </tr>
                </thead>

                <tbody>{content}</tbody>
            </table>
        );
    }
}

