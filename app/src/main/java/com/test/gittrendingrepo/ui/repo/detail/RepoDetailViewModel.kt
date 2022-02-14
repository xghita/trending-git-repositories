package com.test.gittrendingrepo.ui.repo.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.gittrendingrepo.core.events.ErrorUI
import com.test.gittrendingrepo.core.events.IdleUI
import com.test.gittrendingrepo.core.events.LoadingUI
import com.test.gittrendingrepo.provider.RepoProvider
import com.test.gittrendingrepo.ui.repo.RepoViewModel
import com.test.gittrendingrepo.ui.repo.event.RepoActions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoDetailViewModel(repoId: Int, repoProvider: RepoProvider) :
    RepoViewModel() {

    private val detailViewStateMapper = RepoDetailViewStateMapper()
    private val repoDetailViewState = MutableLiveData<RepoDetailViewState>()

    init {
        addDisposable(repoProvider.getRepoDetail(repoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { publishEvent(RepoActions.REPO_DISPLAY, LoadingUI()) }
            .doAfterSuccess { publishEvent(RepoActions.REPO_DISPLAY, IdleUI) }
            .subscribe({
                repoDetailViewState.postValue(detailViewStateMapper.toViewState(it))
            }, {
                publishEvent(
                    RepoActions.REPO_DISPLAY, ErrorUI(description = it.localizedMessage)
                )
            })
        )
    }

    fun repoDetailViewState(): LiveData<RepoDetailViewState> {
        return repoDetailViewState
    }

}