package com.erdeprof.storyapp.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.erdeprof.storyapp.dashboard.data.ResultStories
import com.erdeprof.storyapp.dashboard.data.Story
import com.erdeprof.storyapp.dashboard.data.StoryPagingSource
import com.erdeprof.storyapp.network.NetworkService

object DataDummy {
    fun generateDummyStoriesEntity(): List<Story> {
        val storiesList = ArrayList<Story>()
        for (i in 0..100) {
            val stories = Story(
                "",
                "",
                "2022-11-21T22:22:22Z",
                "https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/feature-1-kurikulum-global-3.png",
                "Test test..",
                "Name $i",
                "$i"
            )
            storiesList.add(stories)
        }

        return storiesList;
//        return Pager(
//            config = PagingConfig(
//                pageSize = 2
//            ),
//            pagingSourceFactory = {
//                StoryPagingSource2(storiesList)
//            }
//        ).liveData
    }
}

//class StoryPagingSource2(private val datas: ArrayList<Story>) : PagingSource<Int, Story>() {
//    override fun getRefreshKey(state: PagingState<Int, Story>): Int? {
//        return state.anchorPosition?.let { anchorPosition ->
//            val anchorPage = state.closestPageToPosition(anchorPosition)
//            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
//        }
//    }
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Story> {
//        return try {
//            val position = params.key ?: INITIAL_PAGE_INDEX
//            LoadResult.Page(
//                data = datas,
//                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
//                nextKey = if (datas.isNullOrEmpty()) null else position + 1
//            )
//        } catch (exception: Exception) {
//            return LoadResult.Error(exception)
//        }
//    }
//
//    private companion object {
//        const val INITIAL_PAGE_INDEX = 1
//    }
//}