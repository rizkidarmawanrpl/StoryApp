package com.erdeprof.storyapp.dashboard.model

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.erdeprof.storyapp.dashboard.adapter.ListStoryPagerAdapter
import com.erdeprof.storyapp.dashboard.data.Story
import com.erdeprof.storyapp.dashboard.data.StoryPagingSource
import com.erdeprof.storyapp.dashboard.data.StoryRepository
import com.erdeprof.storyapp.utils.DataDummy
import com.erdeprof.storyapp.utils.MainDispatcherRule
import com.erdeprof.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runner.manipulation.Ordering.Context
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
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
    private val dummyStories = DataDummy.generateDummyStoriesEntity()

    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
        mainViewModel.getStory("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUZKSC1mSS1OellBZ1BUeXYiLCJpYXQiOjE2NjkwMzMwMDd9.gdVCa7r2_dlzFHzw_xkEXB3OOtnM5yq20acV08huTo4")
    }

    @Test
    suspend fun `when Get Stories Should Not Null and Return Success`() = runTest {
//        val observer = Observer<MutableLiveData<PagingData<Story>>> {}
//        try {
            val data: PagingData<Story> = StoryPagingSource2.snapshot(dummyStories)

            val expectedStories = MutableLiveData<PagingData<Story>>()
            expectedStories.value = data
            Mockito.`when`(storyRepository.getStory()).thenReturn(expectedStories)

            // val mainViewModel = MainViewModel()
            var actualStories: PagingData<Story> = mainViewModel.story.getOrAwaitValue()
//            mainViewModel.story.observe(this) {
//                actualStories = it
//            }

            val differ = AsyncPagingDataDiffer(
                diffCallback = ListStoryPagerAdapter.DIFF_CALLBACK,
                updateCallback = noopListUpdateCallback,
                workerDispatcher = Dispatchers.Main,
            )
            differ.submitData(actualStories)

//            val actualStories = mainViewModel.getStory(this, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUZKSC1mSS1OellBZ1BUeXYiLCJpYXQiOjE2NjkwMjE0OTl9.Ud7JYq5lt8QAXZKLnz6LSauSTRsGL59JoNuoNw7qvbY").observeForever(observer)

             // Mockito.verify(storyRepository).getStory()
            // Assert.assertNotNull(actualStories)

            Assert.assertNotNull(differ.snapshot())
            Assert.assertEquals(dummyStories, differ.snapshot())
            Assert.assertEquals(dummyStories.size, differ.snapshot().size)
        Assert.assertEquals(dummyStories[0].name, differ.snapshot()[0]?.name)
//        } finally {
//            mainViewModel.getStory(this, "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLUZKSC1mSS1OellBZ1BUeXYiLCJpYXQiOjE2NjkwMjE0OTl9.Ud7JYq5lt8QAXZKLnz6LSauSTRsGL59JoNuoNw7qvbY").removeObserver(observer)
//        }
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