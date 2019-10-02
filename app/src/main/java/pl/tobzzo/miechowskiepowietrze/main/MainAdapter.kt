package pl.tobzzo.miechowskiepowietrze.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import pl.tobzzo.miechowskiepowietrze.R
import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace

class MainAdapter(private val context: Context, private val sensorResult: LinkedHashMap<SensorPlace, Measurements>?) : BaseAdapter() {
  lateinit var keys: Array<SensorPlace>
  lateinit var values: Array<Measurements>

  init {
    sensorResult?.let {
      keys = it.keys.toTypedArray()
      values = it.values.toTypedArray()
    }
  }

  override fun getCount(): Int {
    return sensorResult?.size ?: 0
  }

  override fun getItem(position: Int): Any {
    return position
  }

  override fun getItemId(position: Int): Long {
    return position.toLong()
  }

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    var convertView = convertView

    val listViewHolder: ViewHolder
    if (convertView == null) {
      listViewHolder = ViewHolder()
      convertView = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout
        .activity_main_sensor_item, parent, false)
      listViewHolder.textView1 = convertView!!.findViewById<View>(R.id.textView1) as TextView
      listViewHolder.textView2 = convertView.findViewById<View>(R.id.textView2) as TextView
      listViewHolder.textView3 = convertView.findViewById<View>(R.id.textView3) as TextView
      listViewHolder.textView4 = convertView.findViewById<View>(R.id.textView4) as TextView
      convertView.tag = listViewHolder
    } else {
      listViewHolder = convertView.tag as ViewHolder
    }

    val patternPm25 = "%1\$s%% (pm 2.5)"
    val patternPm10 = "%1\$s%% (pm  10)"
    val pm25 = values[position].current.standards[0].percent
    val pm10 = values[position].current.standards[1].percent
    val infoPm25 = String.format(patternPm25, pm25.toInt())
    val infoPm10 = String.format(patternPm10, pm10.toInt())

    listViewHolder.textView1!!.text = keys[position].name
    listViewHolder.textView2?.text = infoPm25
    listViewHolder.textView3?.text = infoPm10

    return convertView
  }

  internal class ViewHolder {
    var textView1: TextView? = null
    var textView2: TextView? = null
    var textView3: TextView? = null
    var textView4: TextView? = null
  }

}