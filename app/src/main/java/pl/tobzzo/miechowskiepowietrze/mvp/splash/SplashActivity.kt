package pl.tobzzo.miechowskiepowietrze.mvp.splash

import android.content.Intent
import pl.tobzzo.miechowskiepowietrze.R
import pl.tobzzo.miechowskiepowietrze.mvp.base.BaseActivity
import pl.tobzzo.miechowskiepowietrze.mvp.main.MainActivity

const val DELAY_TIME = 2000L
class SplashActivity : BaseActivity() {
  override fun setListeners() = Unit

  override fun getLayoutResId(): Int = R.layout.activity_splash

  override fun init() {
    setStatusBarTransparent()
    postDelay(Runnable {
      startActivity(Intent(this, MainActivity::class.java))
      finish()
    }, DELAY_TIME)

    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
  }

}