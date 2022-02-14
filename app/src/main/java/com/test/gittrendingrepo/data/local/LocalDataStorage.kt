package com.test.gittrendingrepo.data.local

import com.test.gittrendingrepo.data.local.room.RepoDetailDao
import com.test.gittrendingrepo.data.local.room.RepoDetailEntity
import com.test.gittrendingrepo.data.repo.Repo
import io.reactivex.Completable
import io.reactivex.Single

interface LocalDataStorage {
    fun getRepoDetail(repoId: Int): Single<Repo.Detail>
    fun insertRepo(repoDetail: RepoDetailEntity): Completable
}

class LocalDataStorageImpl(private val repoDetailDao: RepoDetailDao) : LocalDataStorage {

    override fun getRepoDetail(repoId: Int): Single<Repo.Detail> {
        return repoDetailDao.getRepoDetail(repoId).map {
            Repo.Detail(
                avatarUrl = it.avatarUrl,
                fullName = it.fullName,
                description = it.description,
                language = it.language,
                stars = it.stars,
                openIssues = it.openIssues,
                forks = it.forks,
                gitUrl = it.gitUrl
            )
        }.onErrorReturn { Repo.Detail() }
    }

    override fun insertRepo(repoDetail: RepoDetailEntity): Completable {
        return repoDetailDao.insertRepo(repoDetail)
    }

}