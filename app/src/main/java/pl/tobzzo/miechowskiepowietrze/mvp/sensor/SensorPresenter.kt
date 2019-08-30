package pl.tobzzo.miechowskiepowietrze.mvp.sensor

import pl.tobzzo.miechowskiepowietrze.main.NavigationItem
import pl.tobzzo.miechowskiepowietrze.mvp.base.BasePresenter
import pl.tobzzo.miechowskiepowietrze.mvp.sensor.SensorContract.SensorPresenter
import pl.tobzzo.miechowskiepowietrze.mvp.sensor.SensorContract.SensorView

class SensorPresenter(view: SensorView) : BasePresenter<SensorView>(view), SensorPresenter {
  override fun onNavigationItemClicked(main: NavigationItem) {
    view.showChart()
  }

}