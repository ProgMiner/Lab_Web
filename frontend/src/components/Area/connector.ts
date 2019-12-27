import { connect } from 'react-redux';

import { RootState } from '../../reducer';
import { AreaProps } from './Area';

type StateProps = Pick<AreaProps, 'locked'>;

function mapStateToProps(rootState: RootState): StateProps {
    return { locked: rootState.application.locked }
}

export const areaConnect = connect(mapStateToProps);
