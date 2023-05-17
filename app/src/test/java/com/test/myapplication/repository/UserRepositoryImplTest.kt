package com.test.myapplication.repository

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.test.myapplication.data.UserDao
import com.test.myapplication.data.entities.User
import com.test.myapplication.ui.signup.model.SignupModel
import com.test.myapplication.unit.MainDispatcherRule
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class UserRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var userDao: UserDao
    lateinit var userRepository: UserRepository

    @Before
    fun setup() {
        userDao = mockk()
        userRepository = UserRepositoryImpl(userDao)
    }

    @Test
    fun `fetch all users`() = runTest {
        //given
        val user = User(
            id = 0,
            firstName = "Khawaja absar uddin",
            lastName = "",
            userName = "",
            email = "absar@gmail.com",
            password = "12345678"
        )

        //when
        coEvery { userRepository.getUserList() } returns flowOf(listOf(user))

        //expected
        userRepository.getUserList().test {
            assertThat(awaitItem()).isEqualTo(listOf(user))
            cancelAndConsumeRemainingEvents()
        }

        coVerify { userRepository.getUserList() }
    }

    @Test
    fun `add user`() = runTest {
        //when
        coEvery { userDao.addUser(any()) } just Runs

        //expected
        userRepository.addUser(
            SignupModel().apply {
                fullName = "Khawaja absar uddin"
                email = "absar@gmail.com"
                password = "12345678"
            }
        )

        coVerify { userDao.addUser(any()) }
    }

    @Test
    fun `delete user`() = runTest {

        //when
        coEvery { userDao.deleteUser(any()) } just Runs

        //expected
        userRepository.deleteUser(
            User(
                id = 0,
                firstName = "Khawaja absar uddin",
                lastName = "",
                userName = "",
                email = "absar@gmail.com",
                password = "12345678"
            )
        )

        coVerify { userDao.deleteUser(any()) }
    }

    @Test
    fun `get user by id`() = runTest {
        //given
        val user = User(
            id = 0,
            firstName = "Khawaja absar uddin",
            lastName = "",
            userName = "",
            email = "absar@gmail.com",
            password = "12345678"
        )
        //when
        coEvery { userRepository.getUserById(0) } returns user

        //expected
        assertThat(userRepository.getUserById(0).equals(user))

        coVerify { userDao.userById(0) }
    }
}