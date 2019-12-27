import { connect } from 'react-redux';
import { Dispatch } from 'redux';

import { RootState } from '../../reducer';
import { AreaPageProps } from './AreaPage';
import { Session } from '../../models/session';
import { lockAndDo } from '../../utils/lockAndDo';
import { authorizedBackendApi } from '../../utils/backendApi';

type StateProps = Pick<AreaPageProps, 'session'>;

function mapStateToProps(state: RootState): StateProps {
    return { session: state.application.session };
}

type DispatchProps = Pick<AreaPageProps, 'onSubmitQuery'>;

function mapDispatchToProps(dispatch: Dispatch): DispatchProps {
    return {
        onSubmitQuery(x: number, y: number, r: number, session: Session, addPoint: (result: boolean) => void): void {
            lockAndDo(dispatch, async () => {
                const response = await authorizedBackendApi('area/check', session, 'POST', { x, y, r });

                if (!response.ok) {
                    return;
                }

                addPoint(await response.json());
            })
        }
    }
}

export const areaPageConnect = connect(mapStateToProps, mapDispatchToProps);
