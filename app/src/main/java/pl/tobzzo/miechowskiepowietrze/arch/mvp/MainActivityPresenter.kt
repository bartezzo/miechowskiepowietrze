package pl.tobzzo.miechowskiepowietrze.arch.mvp

//https://medium.com/cr8resume/make-you-hand-dirty-with-mvp-model-view-presenter-eab5b5c16e42
//https://medium.com/cr8resume/working-with-mvp-and-retrofit-2-in-android-b771b8369ec0

class MainActivityPresenter(private val view: MainActivityView) {
  private var user: User

  init {
    user = User("g", "g@example.com")
  }
}