package pl.tobzzo.miechowskiepowietrze.mvp.sensor

import pl.tobzzo.miechowskiepowietrze.main.NavigationItem
import pl.tobzzo.miechowskiepowietrze.main.NavigationItem.Favorite
import pl.tobzzo.miechowskiepowietrze.main.NavigationItem.Main
import pl.tobzzo.miechowskiepowietrze.main.NavigationItem.Settings
import pl.tobzzo.miechowskiepowietrze.mvp.sensor.SensorContract.View
import javax.inject.Inject

class SensorPresenter @Inject
constructor() : SensorContract.Presenter {
  private var mView: View? = null

  override fun onNavigationItemClicked(navigationItem: NavigationItem) {
    when(navigationItem){
      Main -> mView?.showMain()
      Favorite -> mView?.updateView()
      Settings -> mView?.showSettings()
    }
  }

  override fun takeView(view: View) {
    mView = view
  }

  override fun dropView() {
    mView = null
  }

}
