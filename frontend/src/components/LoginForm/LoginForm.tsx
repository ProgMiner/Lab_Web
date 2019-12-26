import React from 'react';

import { htmlInputStateDispatcher } from '../../utils/htmlInputStateDispatcher';

interface LoginFormState {

    username: string;
    password: string;
}

export class LoginForm extends React.Component<{}, LoginFormState> {

    state: LoginFormState = {
        username: '',
        password: ''
    };

    private onSignIn() {
        // TODO make sign in action
    }

    private onSignUp() {
        // TODO make sign up action
    }

    render() {
        return (
            <div>
                <form>
                    <label>Username: <input type="text" onInput={htmlInputStateDispatcher(this, 'username', String)} /></label>
                    <label>Password: <input type="password" onInput={htmlInputStateDispatcher(this, 'password', String)} /></label>

                    <button type="button" onClick={this.onSignIn.bind(this)}>Sign in</button>
                    <button type="button" onClick={this.onSignUp.bind(this)} disabled>Sign up</button>
                </form>
            </div>
        );
    }
}
