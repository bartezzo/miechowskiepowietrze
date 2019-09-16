package pl.tobzzo.miechowskiepowietrze.mvp.base


interface BasePresenter<T> {
  fun takeView(view: T)
  fun dropView()
}