import React from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';

import { Query } from '../../models/query';

export interface HistoryTableProps {

    history: Query[];
}

interface HistoryTableState {

    first: number;
}

const PER_PAGE = 10;

export class HistoryTable extends React.Component<HistoryTableProps> {

    state: HistoryTableState = {
        first: 0,
    };

    render() {
        const { history } = this.props;
        const { first } = this.state;

        const value = history.map(query => ({
            x: query.x,
            y: query.y,
            r: query.r,
            result: query.result ? 'includes' : 'not includes',
        }));

        return (
            <DataTable
                value={value}
                paginator
                rows={PER_PAGE}
                first={first}
                onPage={e => this.setState({ first: e.first })}
            >
                <Column field="x" header="X" />
                <Column field="y" header="Y" />
                <Column field="r" header="R" />
                <Column field="result" header="Result" />
            </DataTable>
        );
    }
}

