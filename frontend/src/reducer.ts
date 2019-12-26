import { combineReducers } from 'redux';

import { reducer as app } from './store/session/reducer';

export const rootReducer = combineReducers({ app });

export type RootState = ReturnType<typeof rootReducer>;
