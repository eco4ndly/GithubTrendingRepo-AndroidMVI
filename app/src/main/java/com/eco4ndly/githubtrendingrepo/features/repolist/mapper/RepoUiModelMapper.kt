package com.eco4ndly.githubtrendingrepo.features.repolist.mapper

import com.eco4ndly.githubtrendingrepo.data.entities.RepoModel
import com.eco4ndly.githubtrendingrepo.common.extensions.mapAsUi
import com.eco4ndly.githubtrendingrepo.domain.mapper.Mapper
import com.eco4ndly.githubtrendingrepo.features.repolist.model.RepoUiModel

/**
 * A Sayan Porya code on 24/05/20
 */
class RepoUiModelMapper(private val repoList: List<RepoModel>): Mapper<List<RepoUiModel>> {
  override fun getMapping(): List<RepoUiModel> {
    return repoList.map { it.mapAsUi() }.toList()
  }
}