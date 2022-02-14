package com.test.gittrendingrepo.ui.repo

import androidx.lifecycle.LiveData
import com.test.gittrendingrepo.core.base.BaseViewModel
import com.test.gittrendingrepo.extensions.map
import com.test.gittrendingrepo.ui.repo.event.RepoActions
import com.test.gittrendingrepo.ui.repo.event.RepoEventState
import com.test.gittrendingrepo.ui.repo.event.RepoEventStateMapper

open class RepoViewModel : BaseViewModel<RepoActions>() {

    private val eventStateMapper = RepoEventStateMapper()

    fun eventsState(): LiveData<RepoEventState> {
        return getEvents().map { eventStateMapper.toEventState(it) }
    }

}
