package com.test.gittrendingrepo.core.events

open class ErrorUI(
    open val description: String?
) : EventUI {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ErrorUI) return false

        if (description != other.description) return false

        return true
    }

    override fun hashCode(): Int {
        return description.hashCode()
    }
}