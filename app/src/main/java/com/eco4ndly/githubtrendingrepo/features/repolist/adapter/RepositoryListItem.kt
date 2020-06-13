package com.eco4ndly.githubtrendingrepo.features.repolist.adapter

import androidx.recyclerview.widget.DiffUtil
import com.eco4ndly.githubtrendingrepo.R
import com.eco4ndly.githubtrendingrepo.common.extensions.safeOffer
import com.eco4ndly.githubtrendingrepo.features.repolist.RepoListIntent
import com.eco4ndly.githubtrendingrepo.features.repolist.adapter.RepositoryListItem.Event.ItemClicked
import com.eco4ndly.githubtrendingrepo.features.repolist.adapter.RepositoryListItem.Event.ProfilePicClick
import com.eco4ndly.githubtrendingrepo.features.repolist.adapter.RepositoryListItem.RepoItem
import com.eco4ndly.githubtrendingrepo.features.repolist.model.RepoUiModel
import com.eco4ndly.githubtrendingrepo.widgets.ItemAdapter
import com.eco4ndly.githubtrendingrepo.widgets.ItemViewHolder
import kotlinx.android.synthetic.main.layout_repo_item.view.iv_repo_avatar
import kotlinx.android.synthetic.main.layout_repo_item.view.repo_list_item_view
import kotlinx.coroutines.channels.SendChannel

/**
 * A Sayan Porya code on 26/05/20
 */
sealed class RepositoryListItem: ItemAdapter.Item<RepositoryListItem.Event> {

  companion object {
    /**
     * Produces list of [RepositoryListItem] from [RepoUiModel]
     */
    fun mapAsListItem(repoUiModelList: List<RepoUiModel>): List<RepositoryListItem> {
      return repoUiModelList
        .map(::RepoItem)
    }
  }

  /**
   * Each item in the [RepositoryListItem]
   */
  data class RepoItem(val repoUiModel: RepoUiModel): RepositoryListItem() {
    override fun layoutResId(): Int = R.layout.layout_repo_item

    override fun render(holder: ItemViewHolder, channel: SendChannel<Event>) {
      holder.itemView.iv_repo_avatar.setOnClickListener { channel.safeOffer(ProfilePicClick(repoUiModel.avatar)) }
      holder.itemView.repo_list_item_view.render(repoUiModel)
      holder.itemView.setOnClickListener {
        channel.safeOffer(ItemClicked(repoUiModel))
      }
    }

  }

  /**
   * Events from Repo Item Click
   */
  sealed class Event {
    /**
     * Item click event
     */
    data class ItemClicked(val repoUiModel: RepoUiModel): Event()

    /**
     * Event from repository list adapter to the ui, when user clicks on the avatar pic
     * in the list item
     */
    data class ProfilePicClick(val picUrl: String): Event()
  }
}

/**
 * Diff util callback for Repo List
 */
class RepoLisDiffCallback: DiffUtil.ItemCallback<RepositoryListItem>() {
  override fun areItemsTheSame(oldItem: RepositoryListItem, newItem: RepositoryListItem): Boolean {
    return when {
      oldItem is RepoItem && newItem is RepoItem -> oldItem.repoUiModel == newItem.repoUiModel
      else -> false
    }
  }

  override fun areContentsTheSame(
    oldItem: RepositoryListItem,
    newItem: RepositoryListItem
  ): Boolean {
    return oldItem == newItem
  }

}