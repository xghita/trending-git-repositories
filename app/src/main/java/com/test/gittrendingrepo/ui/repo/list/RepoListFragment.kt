package com.test.gittrendingrepo.ui.repo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.GridLayoutManager
import com.test.gittrendingrepo.R
import com.test.gittrendingrepo.core.base.BaseFragment
import com.test.gittrendingrepo.databinding.FragmentRepoListBinding
import com.test.gittrendingrepo.extensions.getMessageDialog
import com.test.gittrendingrepo.ui.repo.list.adapter.RepoListAdapter
import kotlinx.android.synthetic.main.dialog_message.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RepoListFragment : BaseFragment() {

    private val navController: NavController by lazy {
        requireActivity().findNavController(R.id.navHostFragment)
    }
    private lateinit var binding: FragmentRepoListBinding
    private lateinit var repoAdapter: RepoListAdapter
    private val repoListViewModel: RepoListViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_repo_list,
            container,
            false
        )

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        observeRepoEvents(repoListViewModel)

        repoListViewModel.repoListViewState().observe(viewLifecycleOwner, { viewState ->
            if (viewState.repos.isNullOrEmpty()) {
                getMessageDialog(
                    title = getString(R.string.repo_list_nodata_title),
                    message = getString(R.string.repo_list_nodata_title_description),
                    action = getString(R.string.repo_list_nodata_title_action),
                )
                return@observe
            }

            repoAdapter.updateList(viewState.repos)
            if (viewState.scrollList) binding.reposRv.scrollToPosition(0)
        })
    }

    private fun initViews() {
        repoAdapter = RepoListAdapter { repoId, name, cardView ->
            val extras =
                FragmentNavigatorExtras(cardView to getString(R.string.card_view_transition))
            navController.navigate(
                RepoListFragmentDirections.actionRepoListToRepoDetail(
                    repoId,
                    name
                ),
                extras
            )
        }

        binding.reposRv.adapter = repoAdapter
    }
}