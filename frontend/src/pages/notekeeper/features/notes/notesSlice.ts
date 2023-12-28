import {createAsyncThunk, createEntityAdapter, createSlice, isAnyOf} from "@reduxjs/toolkit";
import {NoteGet, NotePost, NotePut, notesApi} from "../../../../api/notesApi.ts";
import {store} from "../../store.ts";

const notesAdapter = createEntityAdapter<NoteGet>()

export type NotesStateInfo = {
    status: 'idle' | 'loading' | 'succeeded' | 'failed',
    error: string | undefined
}

const initialState = notesAdapter.getInitialState({
    status: "idle",
    error: undefined
} as NotesStateInfo)

export const fetchNotes = createAsyncThunk(
    'notes/fetchNotes',
    async () => {
        const response = await notesApi.getAll()
        return response.data
    }
)

export const fetchNotesByLabelName = createAsyncThunk(
    'notes/fetchNotesByLabelName',
    async (labelName: string) => {
        const response = await notesApi.getAllByLabelName(labelName)
        return response.data
    }
)

export const addLabelToNote = createAsyncThunk(
    'notes/addLabelToNote',
    async ({noteId, labelId}: { noteId: number, labelId: number }) => {
        const response = await notesApi.addLabelToNote(noteId, labelId)
        return response.data
    }
)

export const removeLabelFromNote = createAsyncThunk(
    'notes/removeLabelFromNote',
    async ({noteId, labelId}: { noteId: number, labelId: number }) => {
        const response = await notesApi.removeLabelFromNote(noteId, labelId)
        return response.data
    }
)

export const deleteNote = createAsyncThunk(
    'notes/deleteNote',
    async (noteId: number) => {
        await notesApi.delete(noteId)
        return noteId
    }
)

export const updateNote = createAsyncThunk(
    'notes/updateNote',
    async ({noteId, note}: { noteId: number, note: NotePut }) => {
        const response = await notesApi.update(noteId, note)
        return response.data
    }
)

export const createNote = createAsyncThunk(
    'notes/createNote',
    async (note: NotePost) => {
        const response
            = await notesApi.create(note)
        return response.data
    }
)

export const notesSlice = createSlice({
    name: 'notes',
    initialState: initialState,
    reducers: {},
    extraReducers(builder) {
        builder
            .addCase(addLabelToNote.fulfilled, (state, action) => {
                notesAdapter.updateOne(state, {
                    id: action.payload.id,
                    changes: action.payload
                })
            })
            .addCase(removeLabelFromNote.fulfilled, (state, action) => {
                notesAdapter.updateOne(state, {
                    id: action.payload.id,
                    changes: action.payload
                })
            })
            .addCase(createNote.fulfilled, notesAdapter.addOne)
            .addCase(deleteNote.fulfilled, notesAdapter.removeOne)
            .addCase(updateNote.fulfilled, (state, action) => {
                notesAdapter.updateOne(state, {
                    id: action.payload.id,
                    changes: action.payload
                })
            })
            .addMatcher(isAnyOf(fetchNotes.fulfilled, fetchNotesByLabelName.fulfilled), (state, action) => {
                state.status = 'succeeded'
                notesAdapter.setAll(state, action.payload)
            })
            .addMatcher(isAnyOf(fetchNotes.pending, fetchNotesByLabelName.pending), (state) => {
                state.status = 'loading'
            })
            .addMatcher(isAnyOf(fetchNotes.rejected, fetchNotesByLabelName.rejected), (state, action) => {
                state.status = 'failed'
                state.error = action.error.message
            })
    }
})

type RootState = ReturnType<typeof store.getState>

export const {
    selectAll: selectAllNotes,
    selectIds: selectAllNoteIds,
    selectById: selectNoteById
} = notesAdapter.getSelectors<RootState>((state) => state.notes)

export default notesSlice.reducer