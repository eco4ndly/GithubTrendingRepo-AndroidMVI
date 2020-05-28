package com.eco4ndly.githubtrendingrepo.widgets

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.eco4ndly.githubtrendingrepo.common.extensions.ofType
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.sample

/**
 * A Sayan Porya code on 25/05/20
 *
 * Sort of the base class for the adapters.
 *
 * This can be used as an adapter without making any subclass from it, if u just want
 * basic funcs
 */
open class ItemAdapter<I : ItemAdapter.Item<E>, E>(diffCallBack: DiffUtil.ItemCallback<I>) :
  ListAdapter<I, ItemViewHolder>(diffCallBack) {

  private val eventChannel = ConflatedBroadcastChannel<E>()

  /**
   * Flow to send event from adapter
   */
  val eventFlow = eventChannel.asFlow()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    return ItemViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    getItem(position).render(holder, eventChannel)
  }

  /**
   * List items for [ItemAdapter] must implement this interface.
   *
   * This makes it easier to use multiple views in same recyclerview as each item supplies its own
   * layout res.
   */
  interface Item<E> {

    /**
     * Provides the layout res for an item.
     * This makes it easier to implement multiple views in same recyclerview
     */
    @LayoutRes
    fun layoutResId(): Int

    /**
     * renders the ui with appropriate data
     *
     * @param holder The common view holder
     * @param channel to receive events from the list to ui
     */
    fun render(holder: ItemViewHolder, channel: SendChannel<E>)
  }
}