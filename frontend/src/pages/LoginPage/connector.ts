import { connect } from 'react-redux';

import { PageProps } from '../Page/Page';
import { RootState } from '../../reducer';

type StateProps = Pick<PageProps, 'session'>;

function mapStateToProps(state: RootState): StateProps {
    return { session: state.application.session };
}

export const loginPageConnect = connect(mapStateToProps);
