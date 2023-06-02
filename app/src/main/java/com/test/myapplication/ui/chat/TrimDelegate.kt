package com.test.myapplication.ui.chat

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class TrimDelegate : ReadWriteProperty<Any?, String> {

    private var trimmedValue: String = ""

    override fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return trimmedValue
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        trimmedValue = value.trim()
    }
}