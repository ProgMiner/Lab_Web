import { connect } from 'react-redux';
import { Dispatch } from 'redux';

import { signIn } from '../../store/application/actions';
import { Session } from '../../models/session';
import { LoginFormProps } from './LoginForm';
import { RootState } from '../../reducer';
import { backendApi } from '../../utils/backendApi';
import { lockAndDo } from '../../utils/lockAndDo';
import { growl } from '../../index';

type StateProps = Pick<LoginFormProps, 'locked'>;

const mapStateToProps = (rootState: RootState): StateProps => {
    return { locked: rootState.application.locked }
};

type DispatchProps = Pick<LoginFormProps, 'onSignIn' | 'onSignUp'>;

const mapDispatchToProps = (dispatch: Dispatch): DispatchProps => {
    return {
        onSignIn(username: string, password: string): void {
            lockAndDo(dispatch, async () => {
                const response = await backendApi('session/create', 'POST', { username, password });

                if (response.ok) {
                    const json = await response.json();

                    dispatch(signIn(json as Session));
                } else {
                    growl.current?.show({
                        severity: 'error',
                        summary: 'Sign in error',
                        detail: 'Unknown user or invalid password'
                    });
                }
            });
        },

        onSignUp(username: string, password: string): void {
            lockAndDo(dispatch, async () => {
                const response = await backendApi('user/create', 'POST', { username, password });

                if (response.ok) {
                    growl.current?.show({
                        severity: 'success',
                        summary: 'Successful',
                        detail: 'Registered successfully. Please, sign in now'
                    });
                } else {
                    growl.current?.show({
                        severity: 'error',
                        summary: 'Registration error',
                        detail: 'An unknown error occurred while registration'
                    });
                }
            });
        }
    };
};

export const loginFormConnect = connect(mapStateToProps, mapDispatchToProps);
