package com.eco4ndly.githubtrendingrepo.common.extensions

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.webkit.URLUtil
import android.widget.Toast
import com.eco4ndly.githubtrendingrepo.data.entities.BuiltBy
import com.eco4ndly.githubtrendingrepo.data.entities.RepoModel
import com.eco4ndly.githubtrendingrepo.features.repolist.mapper.RepoUiModelMapper
import com.eco4ndly.githubtrendingrepo.features.repolist.model.BuiltByUiModel
import com.eco4ndly.githubtrendingrepo.features.repolist.model.RepoUiModel
import timber.log.Timber
import java.lang.IllegalArgumentException
import java.lang.NullPointerException
import java.net.MalformedURLException

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
  val colorVal = try {
    Color.parseColor(languageColor)
  } catch (e: IllegalArgumentException) {
    Color.TRANSPARENT
  } catch (e: NullPointerException) {
    Color.TRANSPARENT
  }
  return RepoUiModel(
    repoName = name ?: "",
    description = description ?: "",
    language = language ?: "",
    avatar = avatar ?: "",
    starts = stars ?: 0,
    forks = forks ?: 0,
    color = colorVal,
    builtBy = builtBy.mapAsUi()
  )
}

/**
 * An extension on ArrayList of [BuiltBy] that
 * Maps List of [BuiltBy] as a list of [BuiltByUiModel]
 */
fun List<BuiltBy>?.mapAsUi(): List<BuiltByUiModel> {
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

/**
 * Helper method for bundle
 */
fun <T> Bundle.put(key: String, value: T) {
  when (value) {
    is Boolean -> putBoolean(key, value)
    is String -> putString(key, value)
    is Int -> putInt(key, value)
    is Short -> putShort(key, value)
    is Long -> putLong(key, value)
    is Byte -> putByte(key, value)
    is ByteArray -> putByteArray(key, value)
    is Char -> putChar(key, value)
    is CharArray -> putCharArray(key, value)
    is CharSequence -> putCharSequence(key, value)
    is Float -> putFloat(key, value)
    is Bundle -> putBundle(key, value)
    is Parcelable -> putParcelable(key, value)
    is java.io.Serializable -> putSerializable(key, value)
    else -> throw IllegalStateException("Type of property $key is not supported")
  }
}

/**
 * If provided string is url, will open a browser else execute the provided function
 */
fun String.ifUrlOpenBrowserElse(context: Context?, action : () -> Unit) {
  if (URLUtil.isValidUrl(this)) {
    try {
      Intent(Intent.ACTION_VIEW, Uri.parse(this)).apply {
        context?.startActivity(this)
      }
    } catch (e: MalformedURLException) {
      Timber.e(e)
      action()
    }
  } else {
    action()
  }
}