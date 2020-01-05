import React from 'react';

import { Page } from '../Page/Page';
import { LoginForm } from '../../components/LoginForm/LoginForm';
import { loginFormConnect } from '../../components/LoginForm/connector';
import { forNonAuthorizedConnect } from '../../components/Guard/forNonAuthorizedConnect';
import { Guard } from '../../components/Guard/Guard';

import './LoginPage.css';
import { Panel } from 'primereact/panel';

const LoginFormContainer = loginFormConnect(LoginForm);
const ForNonAuthorizedGuard = forNonAuthorizedConnect(Guard);

export class LoginPage extends Page {

    renderContent() {
        return (
            <ForNonAuthorizedGuard redirectUrl="/area">
                <div className="login-page-main">
                    <div className="lab-info-container">
                        <Panel header="Information" className="lab-info">
                            Domoratskiy Eridan Alekseevich
                            <span style={{"float": "right"}}>Group: P3211</span>

                            <hr />

                            Variant: 164468
                        </Panel>
                    </div>

                    <div className="login-form-container">
                        <LoginFormContainer />
                    </div>
                </div>
            </ForNonAuthorizedGuard>
        );
    }
}
