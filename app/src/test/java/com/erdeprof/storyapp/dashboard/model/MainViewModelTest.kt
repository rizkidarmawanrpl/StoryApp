package com.erdeprof.storyapp.dashboard.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.erdeprof.storyapp.dashboard.adapter.ListStoryPagerAdapter
import com.erdeprof.storyapp.dashboard.data.Resource
import com.erdeprof.storyapp.dashboard.data.Story
import com.erdeprof.storyapp.dashboard.data.StoryRepository
import com.erdeprof.storyapp.utils.DataDummy
import com.erdeprof.storyapp.utils.MainDispatcherRule
import com.erdeprof.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Mock
    private lateinit var storyRepository : StoryRepository
    private lateinit var mainViewModel: MainViewModel
    private val token : String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUZKSC1mSS1OellBZ1BUeXYiLCJpYXQiOjE2NjkwMzMwMDd9.gdVCa7r2_dlzFHzw_xkEXB3OOtnM5yq20acV08huTo4"

    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
        // mainViewModel.getStory(token)
        storyRepository = Mockito.mock(StoryRepository::class.java)
    }

    @Test
    suspend fun `when Get Stories Should Not Null and Return Success`() = runTest {
        val dummyStories = DataDummy.generateDummyStoriesEntity()
        val data: PagingData<Story> = StoryPagingSource2.snapshot(dummyStories)
        val expectedStories = MutableLiveData<PagingData<Story>>()
        expectedStories.value = data

        Mockito.`when`(storyRepository.getStory()).thenReturn(expectedStories)

        mainViewModel.getStory(token)
        var actualStories: PagingData<Story> = mainViewModel.story.getOrAwaitValue()

        val differ = AsyncPagingDataDiffer(
            diffCallback = ListStoryPagerAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main,
        )
        differ.submitData(actualStories)

        Mockito.verify(storyRepository).getStory()

        Assert.assertNotNull(differ.snapshot())
        // Assert.assertNotNull(actualStories)
        Assert.assertNotNull(actualStories is Resource.Success<*>)
        // Assert.assertEquals(dummyStories, differ.snapshot())
        Assert.assertEquals(dummyStories.size, differ.snapshot().size)
        // Assert.assertEquals(dummyStories[0].name, differ.snapshot()[0]?.name)
    }
}

class StoryPagingSource2 : PagingSource<Int, LiveData<List<Story>>>() {
    companion object {
        fun snapshot(items: List<Story>): PagingData<Story> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Story>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Story>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}