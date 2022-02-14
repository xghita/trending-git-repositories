package com.test.gittrendingrepo.core.base

import androidx.fragment.app.Fragment
import com.test.gittrendingrepo.R
import com.test.gittrendingrepo.extensions.createLoadingDialog
import com.test.gittrendingrepo.extensions.getMessageDialog
import com.test.gittrendingrepo.ui.repo.RepoViewModel
import com.test.gittrendingrepo.ui.repo.event.RepoActions

open class BaseFragment : Fragment() {

    private val loadingDialog by lazy { createLoadingDialog() }

    protected fun observeRepoEvents(viewModel: RepoViewModel) {
        viewModel.eventsState().observe(viewLifecycleOwner, {
            it.repoLoadingUI?.let {
                loadingDialog.show()
            }
            it.repoErrorUI?.let { errorUI ->
                viewModel.onEventAcknowledged(RepoActions.REPO_DISPLAY)
                loadingDialog.dismiss()

                getMessageDialog(
                    title = getString(R.string.generic_error_title),
                    message = errorUI.description ?: getString(R.string.generic_error_description),
                    action = getString(R.string.generic_error_action)
                ).show()
            }
            (it.repoIdleUI ?: it.repoSuccessUI)?.let {
                viewModel.onEventAcknowledged(RepoActions.REPO_DISPLAY)
                loadingDialog.dismiss()
            }
        })
    }
}