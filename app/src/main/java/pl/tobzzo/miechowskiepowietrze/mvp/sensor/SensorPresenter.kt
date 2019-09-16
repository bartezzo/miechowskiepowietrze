package pl.tobzzo.miechowskiepowietrze.mvp.sensor

import pl.tobzzo.miechowskiepowietrze.main.NavigationItem
import pl.tobzzo.miechowskiepowietrze.mvp.sensor.SensorContract.View
import javax.inject.Inject

class SensorPresenter @Inject
constructor() : SensorContract.Presenter {
  private var mView: SensorContract.View? = null

  override fun onNavigationItemClicked(main: NavigationItem) {
    mView?.showChart()
  }

  override fun takeView(view: View) {
    mView = view
  }

  override fun dropView() {
    mView = null
  }

}
