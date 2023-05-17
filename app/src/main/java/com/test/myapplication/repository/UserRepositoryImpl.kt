package com.test.myapplication.repository

import android.util.Log
import com.test.myapplication.data.UserDao
import com.test.myapplication.data.entities.User
import com.test.myapplication.ui.login.model.LoginModel
import com.test.myapplication.ui.signup.model.SignupModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDao: UserDao) : UserRepository {

    override suspend fun checkUserStatus(loginModel: LoginModel): Boolean {
        val user = userDao.findByEmail(loginModel.email)
        user?.let {
            if (it.password == loginModel.password) {
                Log.v("User found!", it.userName)
                return true
            }
            return false
        }
        return false
    }

    override suspend fun addUser(signupModel: SignupModel): Boolean {
        userDao.addUser(
            User(
                id = 0,
                firstName = signupModel.fullName,
                lastName = "",
                userName = "",
                email = signupModel.email,
                password = signupModel.password
            )
        )
        return true
    }

    override suspend fun deleteUser(user: User) {
        userDao.deleteUser(user)
    }

    override fun getUserList(): Flow<List<User>> {
        return userDao.readAllData()
    }

    override suspend fun getUserById(id: Int): User {
        return userDao.userById(id)
    }

    override suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}