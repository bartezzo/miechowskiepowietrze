package pl.tobzzo.miechowskiepowietrze.main

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Guideline
import androidx.core.content.ContextCompat
import pl.tobzzo.miechowskiepowietrze.R
import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import java.util.SortedMap
import kotlin.math.max


const val DEFAULT_MAX_PERCENT = 100

class MainAdapter(private val context: Context, sensorResult: LinkedHashMap<Sensor, Measurements>?) : BaseAdapter() {

  private val comparator = compareBy<Sensor> { it.name }.thenBy { it.place }.thenBy { it.gpsLatitude }.thenBy { it.gpsLongitude }
  var data: SortedMap<Sensor, Measurements>
  var keys: Array<Sensor>
  var values: Array<Measurements>

  init {
    data = sensorResult?.toSortedMap(comparator) ?: emptyMap<Sensor, Measurements>().toSortedMap(comparator)
    keys = data.keys.toTypedArray()
    values = data.values.toTypedArray()
  }

  override fun getCount(): Int {
    return data.size
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
      listViewHolder.card = convertView.findViewById<View>(R.id.card) as CardView
      listViewHolder.mainContainer = convertView.findViewById<View>(R.id.main_container)
      listViewHolder.contentContainer = convertView.findViewById<View>(R.id.content_container)
      listViewHolder.name = convertView.findViewById<View>(R.id.name) as TextView
      listViewHolder.pm10 = convertView.findViewById<View>(R.id.pm10) as TextView
      listViewHolder.pm10progressBar = convertView.findViewById<View>(R.id.pm10progressBar) as ProgressBar
      listViewHolder.pm2dot5 = convertView.findViewById<View>(R.id.pm2) as TextView
      listViewHolder.pm2dot5progressBar = convertView.findViewById<View>(R.id.pm2progressBar) as ProgressBar
      listViewHolder.spaceBottom = convertView.findViewById<View>(R.id.space_bottom) as View
      listViewHolder.caqi = convertView.findViewById<View>(R.id.caqi) as TextView
      listViewHolder.guidelineVerticalBottomSpaceProgress = convertView.findViewById<View>(R.id.guidelineVerticalBottomSpaceProgress) as
        Guideline
      convertView.tag = listViewHolder
    } else {
      listViewHolder = convertView.tag as ViewHolder
    }

    val maxPm2dot5 = (values.maxBy { it.current.standards[0].percent.toInt() })?.current?.standards?.get(0)?.percent?.toInt() ?: 0
    val maxPm1dot0 = (values.maxBy { it.current.standards[1].percent.toInt() })?.current?.standards?.get(1)?.percent?.toInt() ?: 0

    val item = values[position]
    val caqi = item.current.indexes[0].value.toInt().toString()
    val caqiColor = item.current.indexes[0].color
    val pm2dot5 = item.current.standards[0].percent.toInt()
    val pm10 = item.current.standards[1].percent.toInt()
    val mDrawable = ContextCompat.getDrawable(context, R.drawable.rounded_corner)
    val parsedCaqiColor = Color.parseColor(caqiColor)
    mDrawable?.colorFilter = PorterDuffColorFilter(parsedCaqiColor, PorterDuff.Mode.SRC)

    listViewHolder.spaceBottom?.setBackgroundColor(parsedCaqiColor)
    listViewHolder.name?.text = keys[position].name
    listViewHolder.pm10?.text = pm10.toString()
    listViewHolder.pm10progressBar?.progress = pm10
    listViewHolder.pm10progressBar?.max = max(DEFAULT_MAX_PERCENT, maxPm1dot0)
    listViewHolder.pm2dot5?.text = pm2dot5.toString()
    listViewHolder.pm2dot5progressBar?.progress = pm2dot5
    listViewHolder.pm2dot5progressBar?.max = max(DEFAULT_MAX_PERCENT, maxPm2dot5)
    listViewHolder.caqi?.text = caqi

    val params = listViewHolder.guidelineVerticalBottomSpaceProgress?.layoutParams as ConstraintLayout.LayoutParams
    params.guidePercent = 0.95f
    listViewHolder.guidelineVerticalBottomSpaceProgress?.layoutParams = params

    return convertView!!
  }

  internal class ViewHolder {
    var card: CardView? = null
    var mainContainer: View? = null
    var contentContainer: View? = null
    var info: View? = null
    var name: TextView? = null
    var pm10: TextView? = null
    var pm10progressBar: ProgressBar? = null
    var pm2dot5: TextView? = null
    var pm2dot5progressBar: ProgressBar? = null
    var caqi: TextView? = null
    var spaceBottom: View? = null
    var guidelineVerticalBottomSpaceProgress: Guideline? = null
  }

}