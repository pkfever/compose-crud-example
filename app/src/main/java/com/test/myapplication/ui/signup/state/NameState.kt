package com.test.myapplication.ui.signup.state

import com.test.myapplication.ui.login.state.TextFieldState

class NameState : TextFieldState(validator = ::isValidName, errorFor = ::validationError)

fun validationError(name: String): String {
    return "Please enter name to proceed!"
}

fun isValidName(name: String): Boolean {
    return name.isNotBlank()
}
