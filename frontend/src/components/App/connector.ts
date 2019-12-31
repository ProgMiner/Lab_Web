import { connect } from 'react-redux';
import { Dispatch } from 'redux';

import { AppProps } from './App';
import { lockAndDo } from '../../utils/lockAndDo';
import { authorizedBackendApi } from '../../utils/backendApi';
import { signOut } from '../../store/application/actions';
import { Session } from '../../models/session';
import { RootState } from '../../reducer';
import { backendApiUserNotifyWrapper } from '../../utils/backendApiUserNotifyWrapper';

type StateProps = Pick<AppProps, 'session'>;

function mapStateToProps(rootState: RootState): StateProps {
    return { session: rootState.application.session };
}

type DispatchProps = Pick<AppProps, 'checkSession'>

function mapDispatchToProps(dispatch: Dispatch): DispatchProps {
    return {
        checkSession(session: Session): void {
            lockAndDo(dispatch, async () => {
                const response = await backendApiUserNotifyWrapper(authorizedBackendApi('session/check', session));

                if (!response.ok) {
                    dispatch(signOut());
                }
            });
        }
    }
}

export const appConnect = connect(mapStateToProps, mapDispatchToProps);
