import React from 'react';
import { Route, Switch } from 'react-router-dom';

import { LoginPage } from './pages/LoginPage/LoginPage';
import { AreaPage } from './pages/AreaPage/AreaPage';
import { areaPageConnect } from './pages/AreaPage/connector';

const LoginPageContainer: React.FC = () => (<LoginPage session={null} />);
const AreaPageContainer = areaPageConnect(AreaPage);

export const routes = (
    <Switch>
        <Route exact path="/" component={LoginPageContainer} />
        <Route exact path="/area" component={AreaPageContainer} />
    </Switch>
);
