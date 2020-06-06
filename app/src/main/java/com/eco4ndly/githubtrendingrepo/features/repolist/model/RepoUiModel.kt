package com.eco4ndly.githubtrendingrepo.features.repolist.model

import android.graphics.Color
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A Sayan Porya code on 24/05/20
 */
@Parcelize
data class RepoUiModel(
  val repoName: String,
  val description: String,
  val language: String,
  val avatar: String,
  val starts: Int,
  val forks: Int,
  val color: Int,
  val builtBy: List<BuiltByUiModel>
): Parcelable

@Parcelize
data class BuiltByUiModel(
  val userName: String,
  val profileLink: String,
  val avatar: String
): Parcelable