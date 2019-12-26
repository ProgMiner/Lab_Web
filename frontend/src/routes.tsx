import React from 'react';
import { Route, Switch } from 'react-router-dom';

import { AreaPage } from './pages/AreaPage/AreaPage';
import { LoginPage } from './pages/LoginPage/LoginPage';
import { areaPageConnect } from './pages/AreaPage/connector';
import { loginPageConnect } from './pages/LoginPage/connector';

const LoginPageContainer = loginPageConnect(LoginPage);
const AreaPageContainer = areaPageConnect(AreaPage);

export const routes = (
    <Switch>
        <Route exact path="/" component={LoginPageContainer} />
        <Route exact path="/area" component={AreaPageContainer} />
    </Switch>
);
