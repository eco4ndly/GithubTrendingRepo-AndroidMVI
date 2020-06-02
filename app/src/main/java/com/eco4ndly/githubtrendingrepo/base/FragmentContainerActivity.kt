package com.eco4ndly.githubtrendingrepo.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.eco4ndly.githubtrendingrepo.R
import com.eco4ndly.githubtrendingrepo.features.repolist.ui.RepoListFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
class FragmentContainerActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_fragment_container)

    supportFragmentManager
      .beginTransaction()
      .replace(R.id.fragment_container, RepoListFragment.newInstance(), RepoListFragment.TAG)
      .commitAllowingStateLoss()
  }
}