package pl.tobzzo.miechowskiepowietrze.main

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import pl.tobzzo.miechowskiepowietrze.R
import pl.tobzzo.miechowskiepowietrze.rest.v2.Measurements
import pl.tobzzo.miechowskiepowietrze.sensor.Sensor
import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace
import pl.tobzzo.miechowskiepowietrze.utils.extensions.changeVisibility
import java.util.SortedMap

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
//      listViewHolder.info = convertView.findViewById<View>(R.id.info)
      listViewHolder.name = convertView.findViewById<View>(R.id.name) as TextView
//      listViewHolder.pm2dot5 = convertView.findViewById<View>(R.id.pm2dot5) as TextView
//      listViewHolder.pm1dot0 = convertView.findViewById<View>(R.id.pm1dot0) as TextView
      listViewHolder.caqi = convertView.findViewById<View>(R.id.caqi) as TextView
      listViewHolder.detailsButton = convertView.findViewById<View>(R.id.details_button) as Button
      listViewHolder.detailsBox = convertView.findViewById<View>(R.id.details_box) as View
      convertView.tag = listViewHolder
    } else {
      listViewHolder = convertView.tag as ViewHolder
    }

    val item = values[position]
    val caqi = item.current.indexes[0].value
    val caqiColor = item.current.indexes[0].color
    val patternPm2dot5 = "%1\$s%% (pm 2.5)"
    val patternPm10 = "%1\$s%% (pm  10)"
    val patternCaqi = "%1\$s%% (caqi)"
    val pm2dot5 = item.current.standards[0].percent
    val pm10 = item.current.standards[1].percent
    val infoPm2dot5 = String.format(patternPm2dot5, pm2dot5.toInt())
    val infoPm10 = String.format(patternPm10, pm10.toInt())
    val infoCaqi = String.format(patternCaqi, caqi.toInt())
    val mDrawable = ContextCompat.getDrawable(context, R.drawable.rounded_corner)
    mDrawable?.colorFilter = PorterDuffColorFilter(Color.parseColor(caqiColor), PorterDuff.Mode.SRC)

    listViewHolder.contentContainer?.background = mDrawable
    listViewHolder.name?.text = keys[position].name
    listViewHolder.pm2dot5?.text = infoPm2dot5
    listViewHolder.pm1dot0?.text = infoPm10
    listViewHolder.caqi?.text = infoCaqi
    listViewHolder.detailsButton?.setOnClickListener(OnClickListener { listViewHolder.detailsBox?.changeVisibility() })

    return convertView!!
  }

  internal class ViewHolder {
    var card: CardView? = null
    var mainContainer: View? = null
    var contentContainer: View? = null
    var info: View? = null
    var name: TextView? = null
    var pm2dot5: TextView? = null
    var pm1dot0: TextView? = null
    var caqi: TextView? = null
    var detailsButton: Button? = null
    var detailsBox: View? = null
  }

}