import { connect } from 'react-redux';
import { Dispatch } from 'redux';

import { RootState } from '../../reducer';
import { AreaPageProps } from './AreaPage';
import { Session } from '../../models/session';
import { lockAndDo } from '../../utils/lockAndDo';
import { authorizedBackendApi } from '../../utils/backendApi';
import { backendApiUserNotifyWrapper } from '../../utils/backendApiUserNotifyWrapper';
import { signOut } from '../../store/application/actions';

type StateProps = Pick<AreaPageProps, 'session'>;

function mapStateToProps(state: RootState): StateProps {
    return { session: state.application.session };
}

type DispatchProps = Pick<AreaPageProps, 'onSubmitQuery' | 'signOut'>;

function mapDispatchToProps(dispatch: Dispatch): DispatchProps {
    return {
        onSubmitQuery(x: string, y: string, r: string, session: Session, addPoint: (result: boolean) => void): void {
            lockAndDo(dispatch, async () => {
                const response = await backendApiUserNotifyWrapper(
                    authorizedBackendApi('area/check', session, 'POST', { x, y, r })
                );

                if (!response.ok) {
                    return;
                }

                addPoint(await response.json());
            })
        },

        signOut(): void {
            dispatch(signOut());
        }
    }
}

export const areaPageConnect = connect(mapStateToProps, mapDispatchToProps);
