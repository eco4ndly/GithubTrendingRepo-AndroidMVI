package com.eco4ndly.githubtrendingrepo.common.extensions

import android.content.Context
import android.widget.Toast
import com.eco4ndly.githubtrendingrepo.data.entities.BuiltBy
import com.eco4ndly.githubtrendingrepo.data.entities.RepoModel
import com.eco4ndly.githubtrendingrepo.features.repolist.mapper.RepoUiModelMapper
import com.eco4ndly.githubtrendingrepo.features.repolist.model.BuiltByUiModel
import com.eco4ndly.githubtrendingrepo.features.repolist.model.RepoUiModel

/**
 * A Sayan Porya code on 14/05/20
 *
 * This extension property will be used in when statements to exhaust all of its branches.
 *
 * This property has a custom getter which returns the object itself,
 * so if we use it on a when block, it’s treated as an expression and the compiler will
 * force us to specify all cases.
 *
 * https://proandroiddev.com/til-when-is-when-exhaustive-31d69f630a8b
 */
val <T> T.exhaustive: T
  get() = this

/**
 * Toasting in android is easier like never before
 */
fun Context.toast(text: CharSequence) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

/**
 * An Extension on [RepoModel] that
 * Maps [RepoModel] to [RepoUiModel]
 */
fun RepoModel.mapAsUi(): RepoUiModel {
  return RepoUiModel(
    repoName = name ?: "",
    description = description ?: "",
    language = language ?: "",
    avatar = avatar ?: "",
    starts = stars ?: 0,
    forks = forks ?: 0,
    builtBy = builtBy.mapAsUi()
  )
}

/**
 * An extension on ArrayList of [BuiltBy] that
 * Maps List of [BuiltBy] as a list of [BuiltByUiModel]
 */
fun ArrayList<BuiltBy>?.mapAsUi(): List<BuiltByUiModel> {
  if (this == null) return emptyList()
  return map {
    BuiltByUiModel(
      userName = it.username,
      profileLink = it.href,
      avatar = it.avatar
    )
  }.toList()
}

/**
 * Maps list of [RepoModel] as list of [RepoUiModel]
 */
suspend fun List<RepoModel>.map(): List<RepoUiModel> {
  return RepoUiModelMapper(this).map()
}