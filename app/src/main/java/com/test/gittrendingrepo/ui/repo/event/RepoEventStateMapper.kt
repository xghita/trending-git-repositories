package com.test.gittrendingrepo.ui.repo.event

import com.test.gittrendingrepo.core.base.EventViewStateMapper
import com.test.gittrendingrepo.core.events.*

class RepoEventStateMapper : EventViewStateMapper<Map<RepoActions, EventUI>, RepoEventState> {

    override fun toEventState(input: Map<RepoActions, EventUI>): RepoEventState {
        return RepoEventState(
            repoIdleUI = input[RepoActions.REPO_DISPLAY] as? IdleUI,
            repoLoadingUI = input[RepoActions.REPO_DISPLAY] as? LoadingUI,
            repoErrorUI = input[RepoActions.REPO_DISPLAY] as? ErrorUI,
            repoSuccessUI = input[RepoActions.REPO_DISPLAY] as? SuccessUI
        )
    }
}