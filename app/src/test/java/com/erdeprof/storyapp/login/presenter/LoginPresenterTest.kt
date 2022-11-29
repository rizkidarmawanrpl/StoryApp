package com.erdeprof.storyapp.login.presenter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.erdeprof.storyapp.dashboard.model.MainViewModel
import com.erdeprof.storyapp.login.data.LoginRepository
import com.erdeprof.storyapp.utils.DataDummy
import com.erdeprof.storyapp.utils.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify

class LoginPresenterTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private val dataDummy = DataDummy.generateResultLogin()
    private val dummyEmail = "rd1@gmail.com"
    private val dummyPassword = "123456"
    private var loginRepository = LoginRepository()
    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel()
        mainViewModel.getLogin(dummyEmail, dummyPassword)
        loginRepository = Mockito.mock(LoginRepository::class.java)
    }

    @get:Rule
    val mainDispatcherRules = MainDispatcherRule()

    @Test
    fun `when Login Success`() = runTest {
        val expectedResponse = dataDummy

        `when`(loginRepository.getLogin()).thenReturn(
            expectedResponse
        )

        mainViewModel.login.observeForever {
            verify(loginRepository).getLogin()
            Assert.assertNotNull(it.loginResult)
            Assert.assertEquals(it.message, "success")
            Assert.assertFalse(it.error?:false)
        }
    }
}