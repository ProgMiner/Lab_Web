import React from 'react';

import { Page } from '../Page/Page';
import { LoginForm } from '../../components/LoginForm/LoginForm';
import { loginFormConnect } from '../../components/LoginForm/connector';

const LoginFormContainer = loginFormConnect(LoginForm);

export class LoginPage extends Page {

    renderContent() {
        return (
            <div>
                <LoginFormContainer/>
            </div>
        );
    }
}
