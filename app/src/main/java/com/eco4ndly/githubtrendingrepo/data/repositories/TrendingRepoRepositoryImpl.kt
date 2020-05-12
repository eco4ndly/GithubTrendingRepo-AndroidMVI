package com.eco4ndly.githubtrendingrepo.data.repositories

import com.eco4ndly.githubtrendingrepo.common.exception.NoDataException
import com.eco4ndly.githubtrendingrepo.common.extensions.applyCommonStuffs
import com.eco4ndly.githubtrendingrepo.data.api.ApiResult
import com.eco4ndly.githubtrendingrepo.data.api.ErrorHandler
import com.eco4ndly.githubtrendingrepo.data.api.WebService
import com.eco4ndly.githubtrendingrepo.data.entities.RepoModel
import com.eco4ndly.githubtrendingrepo.domain.TrendingRepoRepository
import com.eco4ndly.tracorona.data.api.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

/**
 * Trending list repository implementation
 */
class TrendingRepoRepositoryImpl(private val webService: WebService) : TrendingRepoRepository {
    override suspend fun getTrendingRepositoryList(): Flow<ApiResult<List<RepoModel>>> {
        return flow {
            emit(webService.getTrendingRepos().getResultAndValidate())
        }
            .applyCommonStuffs()
            .flowOn(Dispatchers.IO)
    }

    /**
     * Initiates network call| checks the response and returns the appropriate result
     */
    private fun <T : Any> Response<T>.getResultAndValidate(): ApiResult<T> {
        return run {
            if (isSuccessful && body() != null) {
                ApiResult.Success(body()!!)
            } else {
                ApiResult.Error(
                    NoDataException(
                        ErrorHandler.parseError<ErrorResponse>(errorBody())?.message
                    )
                )
            }
        }
    }
}