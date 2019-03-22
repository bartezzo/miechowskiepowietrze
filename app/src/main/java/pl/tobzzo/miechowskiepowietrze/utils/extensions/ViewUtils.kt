package pl.tobzzo.miechowskiepowietrze.utils.extensions

import android.view.View


inline fun View.changeVisibility() {
  this.visibility = if (this.visibility == View.VISIBLE) View.GONE else View.VISIBLE
}

inline var View.isVisible: Boolean
  get() =
    visibility == View.VISIBLE
  set(value: Boolean) {
    visibility = if(value) View.VISIBLE else View.GONE
  }