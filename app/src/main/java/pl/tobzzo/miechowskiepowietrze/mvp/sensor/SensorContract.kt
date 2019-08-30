package pl.tobzzo.miechowskiepowietrze.mvp.sensor

import pl.tobzzo.miechowskiepowietrze.main.NavigationItem

interface SensorContract {
  interface SensorView {
    fun updateChart()
    fun showChart()
    fun hideChart()
  }

  interface SensorPresenter {
    fun onNavigationItemClicked(main: NavigationItem)
  }
}