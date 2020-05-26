package com.eco4ndly.githubtrendingrepo.features.repolist.adapter

import com.eco4ndly.githubtrendingrepo.R
import com.eco4ndly.githubtrendingrepo.widgets.ItemAdapter
import com.eco4ndly.githubtrendingrepo.widgets.ItemViewHolder
import kotlinx.android.synthetic.main.layout_repo_item.view.tv_repo_name
import kotlinx.coroutines.channels.SendChannel

/**
 * A Sayan Porya code on 26/05/20
 */
class RepositoryListItem: ItemAdapter.Item<RepositoryListItem.Event> {

  override fun layoutResId(): Int = R.layout.layout_repo_item

  override fun render(holder: ItemViewHolder, channel: SendChannel<Event>) {
    //holder.containerView.tv_repo_name.text =
  }
  sealed class Event {
    data class ItemClicked(val position: Int)
  }
}