package com.test.gittrendingrepo.ui.repo.detail

data class RepoDetailViewState(
    val avatarUrl: String,
    val fullName: String,
    val description: String,
    val language: String,
    val stars: Int,
    val openIssues: Int,
    val forks: Int,
    val gitUrl: String
)