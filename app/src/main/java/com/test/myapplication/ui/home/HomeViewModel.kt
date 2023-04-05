package com.test.myapplication.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.myapplication.data.entities.User
import com.test.myapplication.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val users = userRepository.getUserList()

    fun deleteUser(user: User) {
        viewModelScope.launch {
            userRepository.deleteUser(user)
        }
    }
}

