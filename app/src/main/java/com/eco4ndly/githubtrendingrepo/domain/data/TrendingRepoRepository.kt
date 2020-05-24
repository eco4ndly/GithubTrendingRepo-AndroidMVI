package com.eco4ndly.githubtrendingrepo.domain.data

import com.eco4ndly.githubtrendingrepo.data.api.ApiResult
import com.eco4ndly.githubtrendingrepo.data.entities.RepoModel
import com.eco4ndly.githubtrendingrepo.features.repolist.model.RepoUiModel
import kotlinx.coroutines.flow.Flow

/**
 * A Sayan Porya code on 12/05/20
 */
interface TrendingRepoRepository {
    /**
     * Gets the flow of the trending repositories
     */
    suspend fun getTrendingRepositoryList(): Flow<ApiResult<List<RepoUiModel>>>
}