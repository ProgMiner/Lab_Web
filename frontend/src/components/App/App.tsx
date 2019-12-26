import React from 'react';
import { applyMiddleware, createStore } from 'redux';
import { Provider } from 'react-redux';
import { BrowserRouter as Router } from 'react-router-dom';

import { rootReducer } from '../../reducer';
import { routes } from '../../routes';

import './App.css';

const store = createStore(rootReducer, applyMiddleware(/* TODO */));

export class App extends React.Component {

    render() {
        return (
            <Provider store={store}>
                <Router>{routes}</Router>
            </Provider>
        );
    }
}
