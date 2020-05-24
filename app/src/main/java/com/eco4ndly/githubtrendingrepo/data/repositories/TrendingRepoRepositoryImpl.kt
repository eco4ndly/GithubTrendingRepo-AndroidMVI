package com.eco4ndly.githubtrendingrepo.data.repositories

import com.eco4ndly.githubtrendingrepo.common.extensions.applyCommonStuffs
import com.eco4ndly.githubtrendingrepo.common.extensions.map
import com.eco4ndly.githubtrendingrepo.data.api.ApiResult
import com.eco4ndly.githubtrendingrepo.data.api.WebService
import com.eco4ndly.githubtrendingrepo.domain.data.TrendingRepoRepository
import com.eco4ndly.githubtrendingrepo.features.repolist.model.RepoUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Trending list repository implementation
 */
class TrendingRepoRepositoryImpl(private val webService: WebService) :
  TrendingRepoRepository {
    override suspend fun getTrendingRepositoryList(): Flow<ApiResult<List<RepoUiModel>>> {
        return flow {
          emit(ApiResult.Success(webService.getTrendingRepos().map()))
        }.applyCommonStuffs()
    }
}