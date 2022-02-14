package com.test.gittrendingrepo.core.base

interface EventViewStateMapper<K, V> {
    fun toEventState(input: K): V
}