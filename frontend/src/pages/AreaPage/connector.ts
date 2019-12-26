import { connect } from 'react-redux';

import { RootState } from '../../reducer';
import { AreaPageProps } from './AreaPage';

type StateProps = Pick<AreaPageProps, 'session'>;

const mapStateToProps = (state: RootState): StateProps => {
    return { session: state.app.session };
};

export const areaPageConnect = connect(mapStateToProps);
