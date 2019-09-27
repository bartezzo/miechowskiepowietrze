package pl.tobzzo.miechowskiepowietrze.example

import android.content.Context
import android.util.Log
import pl.tobzzo.miechowskiepowietrze.R
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

class MpowExampleImplementation @Inject constructor(private val context: Context) : ExampleInterface{
  init {
    Log.d("mExamImpl", "init")
  }
  override fun takeInt() {
    Log.d("mExamImpl", "app_name ${context.getString(R.string.app_name)}")
  }

}