package pl.tobzzo.miechowskiepowietrze.mvp.main

import pl.tobzzo.miechowskiepowietrze.main.NavigationItem
import pl.tobzzo.miechowskiepowietrze.mvp.base.BasePresenter
import pl.tobzzo.miechowskiepowietrze.mvp.base.BaseView

interface MainContract {
  interface View : BaseView {
    fun updateChart()
    fun showChart()
    fun hideChart()
  }

  interface Presenter : BasePresenter<View> {
    fun onNavigationItemClicked(main: NavigationItem)
  }
}