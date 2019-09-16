package pl.tobzzo.miechowskiepowietrze.mvp.sensor

import pl.tobzzo.miechowskiepowietrze.main.NavigationItem
import pl.tobzzo.miechowskiepowietrze.mvp.base.BasePresenter
import pl.tobzzo.miechowskiepowietrze.mvp.base.BaseView

interface SensorContract {
  interface View : BaseView {
    fun updateChart()
    fun showChart()
    fun hideChart()
  }

  interface Presenter : BasePresenter<SensorContract.View> {
    fun onNavigationItemClicked(main: NavigationItem)
  }
}