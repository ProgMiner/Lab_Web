import React from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';

import { Query } from '../../models/query';

import './HistoryTable.css';

export interface HistoryTableProps {

    history: Query[];
}

interface HistoryTableState {

    first: number;
}

const PER_PAGE = 20;

export class HistoryTable extends React.Component<HistoryTableProps> {

    state: HistoryTableState = {
        first: 0
    };

    render() {
        const { history } = this.props;
        const { first } = this.state;

        const value = history
            .map(query => ({ ...query, result: query.result ? 'includes' : 'not includes' }))
            .reverse();

        return (
            <DataTable value={value} paginator rows={PER_PAGE} first={first}
                       onPage={e => this.setState({ first: e.first })}>
                <Column field="x" header="X" bodyStyle={{ wordBreak: 'break-all' }} />
                <Column field="y" header="Y" bodyStyle={{ wordBreak: 'break-all' }} />
                <Column field="r" header="R" bodyStyle={{ wordBreak: 'break-all' }} />
                <Column field="result" header="Result" bodyStyle={{ whiteSpace: 'nowrap' }} />
            </DataTable>
        );
    }
}

