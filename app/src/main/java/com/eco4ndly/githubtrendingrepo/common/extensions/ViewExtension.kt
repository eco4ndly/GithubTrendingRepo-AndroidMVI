package com.eco4ndly.githubtrendingrepo.common.extensions

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A Sayan Porya code on 27/05/20
 */

/**
 * makes a view visibility gone
 */
fun View.gone() {
  visibility = View.GONE
}

/**
 * makes a view visible
 */
fun View.visible() {
  visibility = View.VISIBLE
}

/**
 * Hide depending upon a condition
 */
inline fun <T : View> T.hideIf(condition: (T) -> Boolean): T {
  visibility = if (condition(this)) {
    View.GONE
  } else {
    View.VISIBLE
  }

  return this
}


/**
 * shows an alert dialog with a messag
 */
fun Fragment.showMessageDialog(message: String) {
  context?.let {
    AlertDialog.Builder(it).apply {
      setMessage(message)
      setCancelable(true)
    }.show()
  }
}

/**
 * The very basic recyclerview we use in most of the application
 */
fun RecyclerView.setUpBasicList(listAdapter: RecyclerView.Adapter<*>) {
  adapter = listAdapter
  setHasFixedSize(true)
  layoutManager = LinearLayoutManager(context)
}