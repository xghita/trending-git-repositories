package com.test.gittrendingrepo.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase

const val REPO_DATABASE = "repoDatabase.db"

@Database(entities = [RepoDetailEntity::class], version = 1)
abstract class RepoDetailDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDetailDao
}