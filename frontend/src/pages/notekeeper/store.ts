import {configureStore} from '@reduxjs/toolkit';
import notesReducer from './features/notes/notesSlice.ts';
import labelsReducer from './features/labels/labelsSlice.ts';

export const store = configureStore({
    reducer: {
        notes: notesReducer,
        labels: labelsReducer
    }
})

export type RootState = ReturnType<typeof store.getState>

export type AppDispatch = typeof store.dispatch