package pl.tobzzo.miechowskiepowietrze.mvp.main

import pl.tobzzo.miechowskiepowietrze.main.NavigationItem

interface MainContract {
  interface MainView {
    fun updateChart()
    fun showChart()
    fun hideChart()
  }

  interface MainPresenter {
    fun onNavigationItemClicked(main: NavigationItem)
  }
}