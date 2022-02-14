package com.test.gittrendingrepo.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RepoDetailDao {
    @Query("SELECT * FROM RepoDetailEntity WHERE id = :repoId ")
    fun getRepoDetail(repoId: Int): Single<RepoDetailEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRepo(repoDetail: RepoDetailEntity): Completable
}
