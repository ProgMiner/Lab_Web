import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import { applyMiddleware, createStore } from 'redux';

import { App } from './components/App/App';
import { rootReducer } from './reducer';
import { appConnect } from './components/App/connector';
import * as serviceWorker from './serviceWorker';

import './index.css';
import { sessionSaver } from './middlewares/sessionSaver';

const AppContainer = appConnect(App);

const store = createStore(rootReducer, applyMiddleware(sessionSaver));

render(<Provider store={store}><AppContainer /></Provider>, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
