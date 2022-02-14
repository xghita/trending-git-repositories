package com.test.gittrendingrepo.extensions

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

inline fun <X, Y> LiveData<X>.map(crossinline transform: (X) -> Y): LiveData<Y> =
    Transformations.map(this) { transform(it) }

@Suppress("NOTHING_TO_INLINE")
inline fun <X> LiveData<X>.distinctUntilChanged(): LiveData<X> =
    Transformations.distinctUntilChanged(this)