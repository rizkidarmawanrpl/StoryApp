package com.erdeprof.storyapp.dashboard.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.erdeprof.storyapp.network.NetworkService

class StoryPagingSource(private val apiService: NetworkService) : PagingSource<Int, Story>() {
    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
        return try {
            val location = 0;
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.stories2(location, position, params.loadSize)
            LoadResult.Page(
                data = responseData.listStory?.toList() ?: emptyList(),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.listStory.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}