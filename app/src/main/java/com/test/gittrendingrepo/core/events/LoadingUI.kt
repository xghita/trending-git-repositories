package com.test.gittrendingrepo.core.events

open class LoadingUI(
    open val message: String? = null
) : EventUI {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is LoadingUI) return false

        if (message != other.message) return false

        return true
    }

    override fun hashCode(): Int {
        return message?.hashCode() ?: 0
    }
}