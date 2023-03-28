package com.test.myapplication.ui.home

import com.test.myapplication.data.entities.User

sealed interface HomeUiState {

    object loading : HomeUiState
    data class Error(val msg: String) : HomeUiState
    data class UserList(val items: List<User>) : HomeUiState
}