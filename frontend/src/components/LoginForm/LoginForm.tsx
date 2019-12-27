import React from 'react';
import { InputText } from 'primereact/inputtext';
import { Password } from 'primereact/password';
import { Button } from 'primereact/button';
import { Panel } from 'primereact/panel';

import { htmlInputStateDispatcher } from '../../utils/htmlInputStateDispatcher';

import './LoginForm.css';

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
        const { locked } = this.props;

        return (
            <Panel header="Sign in / Sign up" className="login-form">
                <form onSubmit={this.onSignIn.bind(this)}>
                    <span className="p-float-label">
                        <InputText id="username" className="login-form__username" disabled={locked}
                                   onInput={htmlInputStateDispatcher(this, 'username', String)} />
                        <label htmlFor="username">Username</label>
                    </span>

                    <span className="p-float-label">
                        <Password id="password" className="login-form__password" feedback={false} disabled={locked}
                                  onInput={htmlInputStateDispatcher(this, 'password', String)} />
                        <label htmlFor="password">Password</label>
                    </span>

                    <div className="login-form__buttons">
                        <Button type="submit" disabled={locked} label="Sign in" />
                        <Button type="button" disabled={locked} label="Sign up"
                                onClick={this.onSignUp.bind(this)} className="p-button-secondary" />
                    </div>
                </form>
            </Panel>
        );
    }

    onSignIn(event: React.FormEvent<HTMLFormElement>) {
        event.preventDefault();

        const { username, password } = this.state;
        this.props.onSignIn(username, password);
    }

    onSignUp(event: React.MouseEvent<HTMLButtonElement, MouseEvent>) {
        event.preventDefault();

        const { username, password } = this.state;
        this.props.onSignUp(username, password);
    }
}
