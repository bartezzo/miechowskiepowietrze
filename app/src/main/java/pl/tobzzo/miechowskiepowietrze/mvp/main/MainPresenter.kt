package pl.tobzzo.miechowskiepowietrze.mvp.main

import pl.tobzzo.miechowskiepowietrze.main.NavigationItem
import pl.tobzzo.miechowskiepowietrze.mvp.main.MainContract.View
import javax.inject.Inject


class MainPresenter @Inject
constructor() : MainContract.Presenter {
  private var mView: MainContract.View? = null

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
