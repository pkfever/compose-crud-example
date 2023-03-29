package com.test.myapplication.repository

import com.test.myapplication.data.entities.User
import com.test.myapplication.ui.login.model.LoginModel
import com.test.myapplication.ui.signup.model.SignupModel
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun checkUserStatus(loginModel: LoginModel): Boolean
    suspend fun addUser(signupModel: SignupModel): Boolean
    suspend fun deleteUser(user: User)
    fun getUserList(): Flow<List<User>>
    suspend fun getUserById(id: Int): User
    suspend fun updateUser(user: User)
}