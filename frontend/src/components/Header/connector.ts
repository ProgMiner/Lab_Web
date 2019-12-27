import { connect } from 'react-redux';
import { Dispatch } from 'redux';

import { HeaderProps } from './Header';
import { RootState } from '../../reducer';
import { authorizedBackendApi } from '../../utils/backendApi';
import { signOut } from '../../store/application/actions';
import { Session } from '../../models/session';

type StateProps = Pick<HeaderProps, 'locked' | 'session'>;

function mapStateToProps(rootState: RootState): StateProps {
    return {
        locked: rootState.application.locked,
        session: rootState.application.session
    }
}

type DispatchProps = Pick<HeaderProps, 'onSignOut'>

function mapDispatchToProps(dispatch: Dispatch): DispatchProps {
    return {
        onSignOut(session: Session) {
            // noinspection JSIgnoredPromiseFromCall
            authorizedBackendApi('session/destroy', session, 'DELETE', { token: session.token });
            dispatch(signOut());
        }
    }
}

export const headerConnect = connect(mapStateToProps, mapDispatchToProps);
