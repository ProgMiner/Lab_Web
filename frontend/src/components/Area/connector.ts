import { connect } from 'react-redux';

import { RootState } from '../../reducer';
import { AreaProps } from './Area';

type StateProps = Pick<AreaProps, 'locked' | 'session'>;

function mapStateToProps(rootState: RootState): StateProps {
    return {
        locked: rootState.application.locked,
        session: rootState.application.session
    }
}

export const areaConnect = connect(mapStateToProps);
