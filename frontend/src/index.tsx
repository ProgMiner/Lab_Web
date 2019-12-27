import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import { applyMiddleware, createStore } from 'redux';
import { Growl } from 'primereact/growl';

import { App } from './components/App/App';
import { rootReducer } from './reducer';
import { appConnect } from './components/App/connector';
import { sessionSaver } from './middlewares/sessionSaver';
import * as serviceWorker from './serviceWorker';

import 'primereact/resources/themes/nova-light/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';

const AppContainer = appConnect(App);

const store = createStore(rootReducer, applyMiddleware(sessionSaver));
export const growl = React.createRef<Growl>();

render(
    <Provider store={store}>
        <Growl ref={growl} />
        <AppContainer />
    </Provider>,

    document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
