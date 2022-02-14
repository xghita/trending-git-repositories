package com.test.gittrendingrepo.core.base

import androidx.lifecycle.*
import com.test.gittrendingrepo.core.events.EventUI
import com.test.gittrendingrepo.core.events.IdleUI
import com.test.gittrendingrepo.extensions.distinctUntilChanged
import com.test.gittrendingrepo.extensions.map
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlin.collections.set

open class BaseViewModel<Action> : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }
    private val eventsLiveData = MutableLiveData<MutableMap<Action, EventUI>>(mutableMapOf())

    fun onEventAcknowledged(action: Action) {
        val newMap = eventsLiveData.value!!
        newMap[action] = IdleUI
        eventsLiveData.postValue(newMap)
    }

    protected fun publishEvent(action: Action, eventUI: EventUI) {
        val newMap = eventsLiveData.value!!
        newMap[action] = eventUI
        eventsLiveData.postValue(newMap)
    }

    protected fun getEvents(): LiveData<Map<Action, EventUI>> {
        return eventsLiveData
            .map { it.toMap() }
            .distinctUntilChanged()
    }

    protected fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onViewLifecycleStart() {
        onStart()
    }

    protected open fun onStart() {}

}