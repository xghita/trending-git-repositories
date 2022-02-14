package com.test.gittrendingrepo.ui.repo.list

import com.test.gittrendingrepo.data.repo.Repo

data class RepoListViewState(val repos: List<Repo.ListItem>?, val scrollList:Boolean)