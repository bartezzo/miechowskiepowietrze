package pl.tobzzo.miechowskiepowietrze.mvp.sensor

import pl.tobzzo.miechowskiepowietrze.main.NavigationItem
import pl.tobzzo.miechowskiepowietrze.mvp.base.BasePresenter
import pl.tobzzo.miechowskiepowietrze.mvp.base.BaseView

interface SensorContract {
  interface View : BaseView {
    fun updateView()
    fun showMain()
    fun showSettings()
  }

  interface Presenter : BasePresenter<View> {
    fun onNavigationItemClicked(navigationItem: NavigationItem)
  }
}