package com.test.gittrendingrepo.data.network

import com.test.gittrendingrepo.data.network.model.RepoDetailResponse
import com.test.gittrendingrepo.data.network.model.TrendingRepoResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApi {

    @GET("search/repositories")
    fun getRepoList(  @Query("q") queryText: String,
                      @Query("sort") sort: String? = null,
                      @Query("order") order: String? = null,
                      @Query("per_page") resultsNumber: Int? = null): Single<TrendingRepoResponse>

    @GET("repositories/{repo_id}")
    fun getRepoDetail(@Path("repo_id") repo_id: Int): Single<RepoDetailResponse>
}