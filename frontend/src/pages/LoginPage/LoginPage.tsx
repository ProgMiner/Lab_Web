import React from 'react';

import { Page } from '../Page/Page';
import { LoginForm } from '../../components/LoginForm/LoginForm';

export class LoginPage extends Page {

    renderContent() {
        return (
            <div>
                <LoginForm/>
            </div>
        );
    }
}
