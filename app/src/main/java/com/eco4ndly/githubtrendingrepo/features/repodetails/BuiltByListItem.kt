package com.eco4ndly.githubtrendingrepo.features.repodetails

import androidx.recyclerview.widget.DiffUtil
import coil.api.load
import coil.transform.CircleCropTransformation
import com.eco4ndly.githubtrendingrepo.R
import com.eco4ndly.githubtrendingrepo.common.extensions.safeOffer
import com.eco4ndly.githubtrendingrepo.features.repolist.model.BuiltByUiModel
import com.eco4ndly.githubtrendingrepo.widgets.ItemAdapter
import com.eco4ndly.githubtrendingrepo.widgets.ItemViewHolder
import kotlinx.android.synthetic.main.layout_built_by_item.view.iv_avatar_built_by
import kotlinx.android.synthetic.main.layout_built_by_item.view.tv_name_built_by
import kotlinx.coroutines.channels.SendChannel

/**
 * A Sayan Porya code on 08/06/20
 */

/**
 * Item for list of contributors of the repo
 */
data class BuiltByListItem(val builtBy: BuiltByUiModel): ItemAdapter.Item<Event> {

  override fun layoutResId(): Int = R.layout.layout_built_by_item

  override fun render(holder: ItemViewHolder, channel: SendChannel<Event>) {
    holder.containerView.tv_name_built_by.text = builtBy.userName
    holder.containerView.iv_avatar_built_by.load(builtBy.avatar) {
      crossfade(true)
      placeholder(R.drawable.ic_image_ph_50dp)
      transformations(CircleCropTransformation())
      error(R.drawable.ic_broken_image_50dp)
    }

    holder.itemView.setOnClickListener {
      channel.safeOffer(Event.ItemClicked(builtBy))
    }
  }

  companion object {
    /**
     * Maps the list of [BuiltBy] as list of [BuiltByListItem]
     */
    fun mapAsListItem(builtByList: List<BuiltByUiModel>): List<BuiltByListItem> {
      return builtByList.map(::BuiltByListItem)
    }
  }

}

/**
 * Base Event for [BuiltByListItem] list
 */
sealed class Event {

  /**
   * When List item is clicked
   */
  data class ItemClicked(val builtBy: BuiltByUiModel): Event()
}

/**
 * Diff util callback for Repo List
 */
class BuiltByListDiffUtilCallback: DiffUtil.ItemCallback<BuiltByListItem>() {
  override fun areItemsTheSame(oldItem: BuiltByListItem, newItem: BuiltByListItem): Boolean {
    return oldItem.builtBy.profileLink == oldItem.builtBy.profileLink
  }

  override fun areContentsTheSame(
    oldItem: BuiltByListItem,
    newItem: BuiltByListItem
  ): Boolean {
    return oldItem == newItem
  }

}