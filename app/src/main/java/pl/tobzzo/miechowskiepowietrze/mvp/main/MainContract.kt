package pl.tobzzo.miechowskiepowietrze.mvp.main

import pl.tobzzo.miechowskiepowietrze.main.NavigationItem
import pl.tobzzo.miechowskiepowietrze.mvp.base.BasePresenter
import pl.tobzzo.miechowskiepowietrze.mvp.base.BaseView

interface MainContract {
  interface View : BaseView {
    fun updateView()
    fun showFavorite()
    fun showSettings()
  }

  interface Presenter : BasePresenter<View> {
    fun onNavigationItemClicked(navigationItem: NavigationItem)
  }
}