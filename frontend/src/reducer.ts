import { combineReducers } from 'redux';

import { reducer as application } from './store/application/reducer';

export const rootReducer = combineReducers({ application });

export type RootState = ReturnType<typeof rootReducer>;
