package com.eco4ndly.githubtrendingrepo.data.api

import androidx.annotation.WorkerThread
import com.eco4ndly.githubtrendingrepo.data.entities.RepoModel
import retrofit2.Response
import retrofit2.http.GET

/**
 * A Sayan Porya code on 2020-02-08
 */
interface WebService {
  @WorkerThread
  @GET("/repositories")
  suspend fun getTrendingRepos(): Response<List<RepoModel>>
}