package com.eco4ndly.githubtrendingrepo.features.repolist.ui

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import coil.api.load
import coil.transform.CircleCropTransformation
import com.eco4ndly.githubtrendingrepo.R
import com.eco4ndly.githubtrendingrepo.common.extensions.hideIf
import com.eco4ndly.githubtrendingrepo.features.repolist.model.RepoUiModel
import kotlinx.android.synthetic.main.layout_repo_item.view.iv_fork
import kotlinx.android.synthetic.main.layout_repo_item.view.iv_repo_avatar
import kotlinx.android.synthetic.main.layout_repo_item.view.lang_color
import kotlinx.android.synthetic.main.layout_repo_item.view.tv_fork_count
import kotlinx.android.synthetic.main.layout_repo_item.view.tv_lang_name
import kotlinx.android.synthetic.main.layout_repo_item.view.tv_repo_description
import kotlinx.android.synthetic.main.layout_repo_item.view.tv_repo_name
import kotlinx.android.synthetic.main.layout_repo_item.view.tv_star_count
import kotlinx.android.synthetic.main.layout_repo_item.view.view_bottom_border

/**
 * A Sayan Porya code on 04/06/20
 */
class RepoListItemView(
  context: Context,
  attributeSet: AttributeSet
): CardView(context, attributeSet) {

  /**
   * Renders the UI according to data
   */
  fun render(repoUiModel: RepoUiModel) {
    repoUiModel.let {
      tv_repo_name.text = it.repoName
      tv_lang_name.text = it.language
      tv_star_count.text = it.starts.toString()
      tv_fork_count.text = it.forks.toString()
      renderDescription(it.description)
      renderLangColorViews(it.color)
      renderAvatar(it.avatar)
    }
  }

  private fun renderDescription(description: String) {
    tv_repo_description.hideIf { tv ->
      tv.text = description
      description.isEmpty()
    }
  }

  private fun renderLangColorViews(color: Int) {
    view_bottom_border.setBackgroundColor(color)

    ContextCompat.getDrawable(context, R.drawable.round_shape)?.let { d ->
      d.setTint(color)
      lang_color.background = d
    }

    ContextCompat.getDrawable(context, R.drawable.ic_github_fork)?.let {d ->
      d.setTint(color)
      iv_fork.background = d
    }
  }

  private fun renderAvatar(avatarLink: String) {
    iv_repo_avatar.load(avatarLink) {
      crossfade(true)
      placeholder(R.drawable.ic_image_ph_50dp)
      transformations(CircleCropTransformation())
      error(R.drawable.ic_broken_image_50dp)
    }
  }
}