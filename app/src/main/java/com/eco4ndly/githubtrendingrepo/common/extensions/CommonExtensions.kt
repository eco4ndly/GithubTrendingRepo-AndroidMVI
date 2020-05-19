package com.eco4ndly.githubtrendingrepo.common.extensions

import android.content.Context
import android.widget.Toast

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
