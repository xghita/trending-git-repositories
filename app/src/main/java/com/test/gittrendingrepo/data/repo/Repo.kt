package com.test.gittrendingrepo.data.repo

sealed class Repo {

    data class ListItem(
        val id: Int = -1,
        val name: String = "",
        val avatarUrl: String = "",
        val language: String = "",
        val watchers: Int = 1,
        var isFavorite: Boolean = false
    )

    data class Detail(
        val avatarUrl: String = "",
        val fullName: String = "",
        val description: String = "",
        val language: String = "",
        val stars: Int = -1,
        val openIssues: Int = -1,
        val forks: Int = -1,
        val gitUrl: String = "",
    )
}