package pl.tobzzo.miechowskiepowietrze.mvp.main

import pl.tobzzo.miechowskiepowietrze.main.NavigationItem
import pl.tobzzo.miechowskiepowietrze.mvp.base.BasePresenter
import pl.tobzzo.miechowskiepowietrze.mvp.main.MainContract.MainPresenter
import pl.tobzzo.miechowskiepowietrze.mvp.main.MainContract.MainView

class MainPresenter(view: MainView) : BasePresenter<MainView>(view), MainPresenter {

  override fun onNavigationItemClicked(main: NavigationItem) {
      view.showChart()
  }
}
