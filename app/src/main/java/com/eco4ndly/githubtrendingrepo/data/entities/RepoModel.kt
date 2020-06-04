package com.eco4ndly.githubtrendingrepo.data.entities

import com.squareup.moshi.Json

/**
 * A Sayan Porya code on 2020-02-08
 */

data class RepoModel(
  @field:Json(name = "author")
  val author: String?,
  @field:Json(name = "avatar")
  val avatar: String?,
  @field:Json(name = "builtBy")
  val builtBy: List<BuiltBy>?,
  @field:Json(name = "currentPeriodStars")
  val currentPeriodStars: Int?,
  @field:Json(name = "description")
  val description: String?,
  @field:Json(name = "forks")
  val forks: Int?,
  @field:Json(name = "language")
  val language: String?,
  @field:Json(name = "languageColor")
  val languageColor: String?,
  @field:Json(name = "name")
  val name: String?,
  @field:Json(name = "stars")
  val stars: Int?,
  @field:Json(name = "url")
  val url: String?
)

data class BuiltBy(
  @field:Json(name = "avatar")
  val avatar: String,
  @field:Json(name = "href")
  val href: String,
  @field:Json(name = "username")
  val username: String
)