import { connect } from 'react-redux';

import { GuardProps } from './Guard';
import { RootState } from '../../reducer';

type StateProps = Pick<GuardProps, 'isAllowed'>;

const mapStateToProps = (state: RootState): StateProps => {
    return { isAllowed: !state.application.session };
};

export const forNonAuthorizedConnect = connect(mapStateToProps);
