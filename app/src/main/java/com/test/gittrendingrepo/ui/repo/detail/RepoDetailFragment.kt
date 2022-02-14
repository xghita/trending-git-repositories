package com.test.gittrendingrepo.ui.repo.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.test.gittrendingrepo.R
import com.test.gittrendingrepo.core.AppGlide
import com.test.gittrendingrepo.core.base.BaseFragment
import com.test.gittrendingrepo.databinding.FragmentRepoDetailBinding
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf

class RepoDetailFragment : BaseFragment() {

    private lateinit var repoDetailViewModel: RepoDetailViewModel
    private lateinit var binding: FragmentRepoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val repoId = requireArguments().getInt("repo_id")

        repoDetailViewModel = get { parametersOf(repoId) }

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_repo_detail,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeRepoEvents(repoDetailViewModel)

        repoDetailViewModel.repoDetailViewState().observe(viewLifecycleOwner, { viewState ->
            binding.apply {

                this.data = viewState
                AppGlide.load(viewState.avatarUrl, avatarIv)

                viewOnGithubBtn.setOnClickListener {
                    requireActivity().startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(viewState.gitUrl)
                        )
                    )
                }
            }
        })
    }
}