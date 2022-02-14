package com.test.gittrendingrepo.provider

import com.test.gittrendingrepo.data.local.LocalDataStorage
import com.test.gittrendingrepo.data.local.room.RepoDetailEntity
import com.test.gittrendingrepo.data.network.GitHubApi
import com.test.gittrendingrepo.data.network.model.TrendingRepoResponse
import com.test.gittrendingrepo.data.repo.Repo
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

const val QUERY = "android%10language:kotlin"
const val SORT = "stars"
const val ORDER = "desc"
const val PER_PAGE = 50

interface RepoProvider {
    fun getRepoList(): Single<TrendingRepoResponse>
    fun getRepoDetail(repoId: Int): Single<Repo.Detail>
}

class RepoProviderImpl(
    private val gitHubApi: GitHubApi,
    private val localDataStorage: LocalDataStorage
) : RepoProvider {

    override fun getRepoList(): Single<TrendingRepoResponse> {
        return gitHubApi.getRepoList(
            queryText = QUERY,
            sort = SORT,
            order = ORDER,
            resultsNumber = PER_PAGE
        )
    }

    override fun getRepoDetail(repoId: Int): Single<Repo.Detail> {
        return localDataStorage.getRepoDetail(repoId)
            .flatMap { repoDetail ->
                if (repoDetail != Repo.Detail()) {
                    Single.just(repoDetail)
                } else {
                    gitHubApi.getRepoDetail(repoId)
                        .flatMap { repoDetailResponse ->
                            localDataStorage.insertRepo(
                                RepoDetailEntity(
                                    id = repoId,
                                    avatarUrl = repoDetailResponse.owner.avatarUrl,
                                    fullName = repoDetailResponse.fullName,
                                    description = repoDetailResponse.description,
                                    language = repoDetailResponse.language,
                                    stars = repoDetailResponse.stargazersCount,
                                    openIssues = repoDetailResponse.openIssues,
                                    forks = repoDetailResponse.forks,
                                    gitUrl = repoDetailResponse.htmlUrl
                                )
                            ).andThen(
                                    Single.just(
                                        Repo.Detail(
                                            avatarUrl = repoDetailResponse.owner.avatarUrl,
                                            fullName = repoDetailResponse.fullName,
                                            description = repoDetailResponse.description,
                                            language = repoDetailResponse.language,
                                            stars = repoDetailResponse.stargazersCount,
                                            openIssues = repoDetailResponse.openIssues,
                                            forks = repoDetailResponse.forks,
                                            gitUrl = repoDetailResponse.htmlUrl
                                        )
                                    )
                                )
                        }
                }
            }
    }

}