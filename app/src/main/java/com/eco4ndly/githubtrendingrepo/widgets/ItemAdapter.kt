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

/**
 * A Sayan Porya code on 25/05/20
 */
open class ItemAdapter<I : ItemAdapter.Item<E>, E>(diffCallBack: DiffUtil.ItemCallback<I>) :
  ListAdapter<I, ItemViewHolder>(diffCallBack) {

  private val eventChannel = ConflatedBroadcastChannel<E>()
  val eventFlow = eventChannel.asFlow()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
    return ItemViewHolder(LayoutInflater.from(parent.context).inflate(viewType, parent, false))
  }

  override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
    getItem(position).render(holder, eventChannel)
  }

  interface Item<E> {
    @LayoutRes
    fun layoutResId(): Int

    fun render(holder: ItemViewHolder, channel: SendChannel<E>)
  }
}