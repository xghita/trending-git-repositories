package com.test.gittrendingrepo.repo

import com.google.gson.Gson
import com.test.gittrendingrepo.data.network.model.RepoDetailResponse
import com.test.gittrendingrepo.data.network.model.TrendingRepoResponse
import com.test.gittrendingrepo.data.repo.Repo


class RepoFactory {

    fun mockTrendingRepoResponse(classLoader: ClassLoader): TrendingRepoResponse {
        val jsonString =
            classLoader.getResourceAsStream("trendingRepositories.json").bufferedReader()
                .use { it.readText() }
        return Gson().fromJson(jsonString, TrendingRepoResponse::class.java)
    }

    fun mockRepoDetailResponse(): Repo.Detail {
        return Repo.Detail()
    }
}