package com.test.gittrendingrepo.ui.repo.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.test.gittrendingrepo.core.events.ErrorUI
import com.test.gittrendingrepo.core.events.LoadingUI
import com.test.gittrendingrepo.core.events.SuccessUI
import com.test.gittrendingrepo.provider.RepoProvider
import com.test.gittrendingrepo.ui.repo.RepoViewModel
import com.test.gittrendingrepo.ui.repo.event.RepoActions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RepoListViewModel(repoProvider: RepoProvider) : RepoViewModel() {

    private val listViewStateMapper = RepoListViewStateMapper()
    private val repoListViewState = MutableLiveData<RepoListViewState>()

    init {
        addDisposable(repoProvider.getRepoList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                publishEvent(RepoActions.REPO_DISPLAY, LoadingUI())
            }
            .subscribe({
                repoListViewState.postValue(listViewStateMapper.toViewState(it))
                publishEvent(RepoActions.REPO_DISPLAY, SuccessUI())
            }, {
                publishEvent(
                    RepoActions.REPO_DISPLAY, ErrorUI(description = it.localizedMessage)
                )
            })
        )
    }

    fun repoListViewState(): LiveData<RepoListViewState> {
        return repoListViewState
    }

    fun sortByFavorite() {
        repoListViewState.value?.let { viewState ->
            repoListViewState.postValue(
                RepoListViewState(
                    repos = viewState.repos?.sortedByDescending { it.isFavorite },
                    scrollList = true
                )
            )
        }
    }

    fun sortByTrending() {
        repoListViewState.value?.let { viewState ->
            repoListViewState.postValue(
                RepoListViewState(
                    repos = viewState.repos?.sortedByDescending { it.watchers },
                    scrollList = true
                )
            )
        }
    }
}