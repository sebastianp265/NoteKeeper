import {createAsyncThunk, createEntityAdapter, createSlice} from "@reduxjs/toolkit";
import {LabelGet, LabelPost, labelsApi} from "../../../../api/labelsApi.ts";
import {store} from "../../store.ts";

const labelsAdapter = createEntityAdapter<LabelGet>()

export type LabelStateInfo = {
    status: 'idle' | 'loading' | 'succeeded' | 'failed',
    error: string | undefined
}

const initialState = labelsAdapter.getInitialState({
    status: "idle",
    error: undefined
} as LabelStateInfo)

export const fetchLabels = createAsyncThunk(
    'posts/fetchLabels',
    async () => {
        const response = await labelsApi.getAll()
        return response.data
    }
)

export const createLabel = createAsyncThunk(
    'posts/addLabel',
    async (label: LabelPost) => {
        const response = await labelsApi.create(label)
        return response.data
    }
)

export const labelsSlice = createSlice({
    name: 'labels',
    initialState: initialState,
    reducers: {},
    extraReducers(builder) {
        builder
            .addCase(fetchLabels.pending, (state) => {
                state.status = 'loading'
            })
            .addCase(fetchLabels.fulfilled, (state, action) => {
                state.status = 'succeeded'
                labelsAdapter.setMany(state, action.payload)
            })
            .addCase(fetchLabels.rejected, (state, action) => {
                state.status = 'failed'
                state.error = action.error.message
            })
            .addCase(createLabel.fulfilled, (state, action) => {
                labelsAdapter.addOne(state, action.payload)
            })
    }
})

type RootState = ReturnType<typeof store.getState>

export const {
    selectAll: selectAllLabels,
    selectById: selectLabelById,
    selectIds: selectLabelIds
} = labelsAdapter.getSelectors<RootState>(state => state.labels)

export default labelsSlice.reducer