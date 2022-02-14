package com.test.gittrendingrepo.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepoDetailEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val avatarUrl: String,
    val fullName: String,
    val description: String,
    val language: String,
    val stars: Int,
    val openIssues: Int,
    val forks: Int,
    val gitUrl: String,
)