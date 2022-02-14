package com.test.gittrendingrepo.ui.repo.list

import com.test.gittrendingrepo.core.base.ViewStateMapper
import com.test.gittrendingrepo.data.network.model.TrendingRepoResponse
import com.test.gittrendingrepo.data.repo.Repo

class RepoListViewStateMapper : ViewStateMapper<TrendingRepoResponse, RepoListViewState> {

    override fun toViewState(payload: TrendingRepoResponse): RepoListViewState {
        return RepoListViewState(
            repos = payload.items?.map {
                Repo.ListItem(
                    id = it.id,
                    name = it.name,
                    avatarUrl = it.owner.avatarUrl,
                    language = it.language,
                    watchers = it.watchers
                )
            },
            scrollList = false
        )
    }
}