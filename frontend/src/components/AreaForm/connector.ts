import { connect } from 'react-redux';

import { RootState } from '../../reducer';
import { AreaFormProps } from './AreaForm';

type StateProps = Pick<AreaFormProps, 'locked'>;

function mapStateToProps(rootState: RootState): StateProps {
    return { locked: rootState.application.locked }
}

export const areaFormConnect = connect(mapStateToProps);
