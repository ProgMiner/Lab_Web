import { connect } from 'react-redux';

import { RootState } from '../../reducer';
import { HeaderProps } from './Header';

type StateProps = Pick<HeaderProps, 'session'>;

const mapStateToProps = (rootState: RootState): StateProps => {
    return { session: rootState.application.session }
};

export const headerConnect = connect(mapStateToProps);
