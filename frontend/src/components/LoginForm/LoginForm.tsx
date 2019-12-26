import React from 'react';

import { htmlInputStateDispatcher } from '../../utils/htmlInputStateDispatcher';

export interface LoginFormProps {

    locked: boolean;

    onSignIn(username: string, password: string): void;
    onSignUp(username: string, password: string): void;
}

interface LoginFormState {

    username: string;
    password: string;
}

export class LoginForm extends React.Component<LoginFormProps, LoginFormState> {

    state: LoginFormState = {
        username: '',
        password: ''
    };

    render() {
        const { onSignIn, onSignUp, locked } = this.props;
        const { username, password } = this.state;

        return (
            <div>
                <form onSubmit={(event) => { onSignIn(username, password); event.preventDefault()}}>
                    <label>Username: <input type="text" disabled={locked}
                                            onInput={htmlInputStateDispatcher(this, 'username', String)} /></label>
                    <label>Password: <input type="password" disabled={locked}
                                            onInput={htmlInputStateDispatcher(this, 'password', String)} /></label>

                    <button type="submit" disabled={locked}>Sign in</button>
                    <button type="button" disabled={locked} onClick={() => onSignUp(username, password)}>Sign up</button>
                </form>
            </div>
        );
    }
}
