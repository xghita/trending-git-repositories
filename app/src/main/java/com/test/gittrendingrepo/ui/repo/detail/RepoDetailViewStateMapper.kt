package com.test.gittrendingrepo.ui.repo.detail

import com.test.gittrendingrepo.core.base.ViewStateMapper
import com.test.gittrendingrepo.data.repo.Repo

class RepoDetailViewStateMapper : ViewStateMapper<Repo.Detail, RepoDetailViewState> {

    override fun toViewState(payload: Repo.Detail): RepoDetailViewState {
        return RepoDetailViewState(
            avatarUrl = payload.avatarUrl,
            fullName = payload.fullName,
            description = payload.description,
            language = payload.language,
            stars = payload.stars,
            openIssues = payload.openIssues,
            forks = payload.forks,
            gitUrl = payload.gitUrl
        )
    }
}