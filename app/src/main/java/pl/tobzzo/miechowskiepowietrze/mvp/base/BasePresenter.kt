package pl.tobzzo.miechowskiepowietrze.mvp.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class BasePresenter<V> protected constructor(protected val view: V) {

  private val disposables = CompositeDisposable()

  /**
   * Contains common setup actions needed for all presenters, if any.
   * Subclasses may override this.
   */
  fun start() {}

  /**
   * Contains common cleanup actions needed for all presenters, if any.
   * Subclasses may override this.
   */
  fun stop() {
    disposables.clear()
  }

  protected fun addDisposable(disposable: Disposable) {
    disposables.add(disposable)
  }
}