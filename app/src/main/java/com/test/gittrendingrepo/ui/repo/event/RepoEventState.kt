package com.test.gittrendingrepo.ui.repo.event

import com.test.gittrendingrepo.core.events.ErrorUI
import com.test.gittrendingrepo.core.events.IdleUI
import com.test.gittrendingrepo.core.events.LoadingUI
import com.test.gittrendingrepo.core.events.SuccessUI

data class RepoEventState(
    val repoIdleUI: IdleUI? = null,
    val repoLoadingUI: LoadingUI? = null,
    val repoErrorUI: ErrorUI? = null,
    val repoSuccessUI: SuccessUI? = null,
)