package com.test.gittrendingrepo.core.base

interface ViewStateMapper<K, V> {
    fun toViewState(payload: K): V
}