package pl.tobzzo.miechowskiepowietrze

import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.daimajia.numberprogressbar.NumberProgressBar
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.gms.analytics.HitBuilders
import com.google.android.gms.analytics.Tracker
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.orhanobut.logger.Logger
import pl.tobzzo.miechowskiepowietrze.connection.IonHelper
import pl.tobzzo.miechowskiepowietrze.utils.extensions.bindView
import pl.tobzzo.miechowskiepowietrze.rest.SensorMeasurementsResponse
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Calendar
import java.util.Date
import java.util.HashMap
import javax.inject.Inject


class MainActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

  @Inject lateinit var ionHelper: IonHelper

  private val swipeLayout: SwipeRefreshLayout by bindView(R.id.swipe_container)
  private val sensorLoadingProgress: ProgressBar by bindView(R.id.sensorLoadingProgress)
  private val sensorResultTable: TableLayout by bindView(R.id.sensorResultTable)

  private val sensorSikorskiego: TableRow by bindView(R.id.sensorSikorskiego)
  private val sensorSikorskiegoProgress: NumberProgressBar by bindView(R.id.sensorSikorskiegoProgress)
  private val sensorSikorskiegoDetailsPm25: TextView by bindView(R.id.sensorSikorskiegoDetailsPm25)
  private val sensorSikorskiegoDetailsPm10: TextView by bindView(R.id.sensorSikorskiegoDetailsPm10)
  private val sensorSikorskiegoLegend: ImageView by bindView(R.id.sensorSikorskiegoLegend)
  private val sensorSikorskiegoHistory: TableRow by bindView(R.id.sensorSikorskiegoHistory)
  private val sensorSikorskiegoHistoryChart: BarChart by bindView(R.id.sensorSikorskiegoHistoryChart)

  private val sensorRynek: TableRow by bindView(R.id.sensorRynek)
  private val sensorRynekProgress: NumberProgressBar by bindView(R.id.sensorRynekProgress)
  private val sensorRynekDetailsPm25: TextView by bindView(R.id.sensorRynekDetailsPm25)
  private val sensorRynekDetailsPm10: TextView by bindView(R.id.sensorRynekDetailsPm10)
  private val sensorRynekLegend: ImageView by bindView(R.id.sensorRynekLegend)
  private val sensorRynekHistory: TableRow by bindView(R.id.sensorRynekHistory)
  private val sensorRynekHistoryChart: BarChart by bindView(R.id.sensorRynekHistoryChart)

  private val sensorKopernika: TableRow by bindView(R.id.sensorKopernika)
  private val sensorKopernikaProgress: NumberProgressBar by bindView(R.id.sensorKopernikaProgress)
  private val sensorKopernikaDetailsPm25: TextView by bindView(R.id.sensorKopernikaDetailsPm25)
  private val sensorKopernikaDetailsPm10: TextView by bindView(R.id.sensorKopernikaDetailsPm10)
  private val sensorKopernikaLegend: ImageView by bindView(R.id.sensorKopernikaLegend)
  private val sensorKopernikaHistory: TableRow by bindView(R.id.sensorKopernikaHistory)
  private val sensorKopernikaHistoryChart: BarChart by bindView(R.id.sensorKopernikaHistoryChart)

  private val sensorParkowe: TableRow by bindView(R.id.sensorParkowe)
  private val sensorParkoweProgress: NumberProgressBar by bindView(R.id.sensorParkoweProgress)
  private val sensorParkoweDetailsPm25: TextView by bindView(R.id.sensorParkoweDetailsPm25)
  private val sensorParkoweDetailsPm10: TextView by bindView(R.id.sensorParkoweDetailsPm10)
  private val sensorParkoweLegend: ImageView by bindView(R.id.sensorParkoweLegend)
  private val sensorParkoweHistory: TableRow by bindView(R.id.sensorParkoweHistory)
  private val sensorParkoweHistoryChart: BarChart by bindView(R.id.sensorParkoweHistoryChart)

  private val sensorSzpitalna: TableRow by bindView(R.id.sensorSzpitalna)
  private val sensorSzpitalnaProgress: NumberProgressBar by bindView(R.id.sensorSzpitalnaProgress)
  private val sensorSzpitalnaDetailsPm25: TextView by bindView(R.id.sensorSzpitalnaDetailsPm25)
  private val sensorSzpitalnaDetailsPm10: TextView by bindView(R.id.sensorSzpitalnaDetailsPm10)
  private val sensorSzpitalnaLegend: ImageView by bindView(R.id.sensorSzpitalnaLegend)
  private val sensorSzpitalnaHistory: TableRow by bindView(R.id.sensorSzpitalnaHistory)
  private val sensorSzpitalnaHistoryChart: BarChart by bindView(R.id.sensorSzpitalnaHistoryChart)

  private val sensorKrotka: TableRow by bindView(R.id.sensorKrotka)
  private val sensorKrotkaProgress: NumberProgressBar by bindView(R.id.sensorKrotkaProgress)
  private val sensorKrotkaDetailsPm25: TextView by bindView(R.id.sensorKrotkaDetailsPm25)
  private val sensorKrotkaDetailsPm10: TextView by bindView(R.id.sensorKrotkaDetailsPm10)
  private val sensorKrotkaLegend: ImageView by bindView(R.id.sensorKrotkaLegend)
  private val sensorKrotkaHistory: TableRow by bindView(R.id.sensorKrotkaHistory)
  private val sensorKrotkaHistoryChart: BarChart by bindView(R.id.sensorKrotkaHistoryChart)

  private val airQualityImageView: ImageView by bindView(R.id.airQualityImageView)
  private val sensorsScrollView: ScrollView by bindView(R.id.sensorsScrollView)

  private var httpHandler: Handler? = null
  private var fakeCAQI = -1
  private var mTracker: Tracker? = null

  private val apiKey: String
    get() {
      return (if (System.currentTimeMillis() % 2 == 0L)
        BuildConfig.hiddenPassword1
      else
        BuildConfig.hiddenPassword2
        ).also {
        googleAnalyticsAction("getApiKey", it)
        Logger.d("api key:%s", it)
      }
    }


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    MpowApp.getApp().appComponent.inject(this)
  }

  override fun onPostCreate(savedInstanceState: Bundle?) {
    super.onPostCreate(savedInstanceState)

    setGoogleAnalytics()
    googleAnalyticsAction("action", "onCreate")
    setElements()
    setListeners()
  }

  private fun setElements() {
    httpHandler = Handler()

    swipeLayout.setOnRefreshListener(this)
    swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
      android.R.color.holo_green_light,
      android.R.color.holo_orange_light,
      android.R.color.holo_red_light)
  }

  private fun setListeners() {
    airQualityImageView.setOnClickListener {
      googleAnalyticsAction("action", "airQualityImageView")
      fakeCAQI = (fakeCAQI + 25) % 168 - 1
      airQualityImageView.setImageResource(getLogoImage(fakeCAQI.toDouble()))
    }

    sensorSikorskiego.setOnClickListener {
      googleAnalyticsAction("actionHistory", "sensorSikorskiegoHistory")
      changeViewVisibility(sensorSikorskiegoHistory)
      sensorSikorskiegoHistoryChart.animateY(animationTimeInMs)
    }

    sensorRynek.setOnClickListener {
      googleAnalyticsAction("actionHistory", "sensorRynekHistory")
      changeViewVisibility(sensorRynekHistory)
      sensorRynekHistoryChart.animateY(animationTimeInMs)
    }

    sensorKopernika.setOnClickListener {
      googleAnalyticsAction("actionHistory", "sensorKopernikaHistory")
      changeViewVisibility(sensorKopernikaHistory)
      sensorKopernikaHistoryChart.animateY(animationTimeInMs)
    }

    sensorParkowe.setOnClickListener {
      googleAnalyticsAction("actionHistory", "sensorParkoweHistory")
      changeViewVisibility(sensorParkoweHistory)
      sensorParkoweHistoryChart.animateY(animationTimeInMs)
    }

    sensorSzpitalna.setOnClickListener {
      googleAnalyticsAction("actionHistory", "sensorSzpitalnaHistory")
      changeViewVisibility(sensorSzpitalnaHistory)
      sensorSzpitalnaHistoryChart.animateY(animationTimeInMs)
    }

    sensorKrotka.setOnClickListener {
      googleAnalyticsAction("actionHistory", "sensorKrotkaHistory")
      changeViewVisibility(sensorKrotkaHistory)
      sensorKrotkaHistoryChart.animateY(animationTimeInMs)
    }
  }

  private fun changeViewVisibility(v: View) {
    v.visibility = if (v.visibility == View.VISIBLE) View.GONE else View.VISIBLE
  }

  private fun restartLoading(forceRefresh: Boolean) {
    googleAnalyticsAction("action", "restartLoading")

    if (forceRefresh)
      lastLoading = 0

    if (System.currentTimeMillis() - lastLoading < 30 * 60 * 1000) {
      showResult()
    } else {
      lastLoading = System.currentTimeMillis()
      responseMap = HashMap()
      httpHandler!!.postDelayed({ listSensorsMeasurements() }, (2 * 1000).toLong())
      sensorLoadingProgress.visibility = View.VISIBLE
      sensorResultTable.visibility = View.GONE
    }
  }

  private fun listSensorsMeasurements() {
    var url: String? = null
    val iterator = SensorMap.getSensors().entries.iterator()
    while (iterator.hasNext()) {
      val sensorEntry = iterator.next()
      val sensor = sensorEntry.value
      if (sensor.type == SensorMap.SensorType.REQ_MAP_POINT) {
        url = String.format(URL_REQ_MAP_POINT, sensor.gpsLatitude, sensor.gpsLongitude)
      } else if (sensor.type == SensorMap.SensorType.REQ_SENSOR) {
        url = String.format(URL_REQ_SENSOR, sensor.id)
      }

      makeHttpRequest(url, sensor)
    }
  }

  private fun setGlobalChart() {
    var iterator: Iterator<*> = responseMap!!.entries.iterator()
    var maxCAQI = 0.0
    var scaledMaxCAQI = 0
    var sumCAQI = 0.0
    var avgCAQI = 0.0
    var countCAQI = 0

    while (iterator.hasNext()) {
      val entry = iterator.next() as Map.Entry<String, SensorMeasurementsResponse>
      val CAQI = entry.value.currentMeasurements.airQualityIndex
      maxCAQI = Math.max(maxCAQI, CAQI)
      sumCAQI += CAQI
      countCAQI += 1
    }
    scaledMaxCAQI = (100 * maxCAQI / 50).toInt()
    avgCAQI = sumCAQI / countCAQI

    iterator = responseMap!!.entries.iterator()
    while (iterator.hasNext()) {
      val entry = iterator.next() as Map.Entry<String, SensorMeasurementsResponse>
      val sensorName = entry.key
      val sensorValues = entry.value
      var progressToUpdate: NumberProgressBar? = null
      var textViewToUpdatePm25: TextView? = null
      var textViewToUpdatePm10: TextView? = null
      var chartViewToUpdate: BarChart? = null

      when (sensorName) {
        SensorMap.MIECHOW_SIKORSKIEGO -> {
          progressToUpdate = sensorSikorskiegoProgress
          textViewToUpdatePm25 = sensorSikorskiegoDetailsPm25
          textViewToUpdatePm10 = sensorSikorskiegoDetailsPm10
          chartViewToUpdate = sensorSikorskiegoHistoryChart
        }
        SensorMap.MIECHOW_RYNEK -> {
          progressToUpdate = sensorRynekProgress
          textViewToUpdatePm25 = sensorRynekDetailsPm25
          textViewToUpdatePm10 = sensorRynekDetailsPm10
          chartViewToUpdate = sensorRynekHistoryChart
        }
        SensorMap.MIECHOW_KOPERNIKA -> {
          progressToUpdate = sensorKopernikaProgress
          textViewToUpdatePm25 = sensorKopernikaDetailsPm25
          textViewToUpdatePm10 = sensorKopernikaDetailsPm10
          chartViewToUpdate = sensorKopernikaHistoryChart
        }
        SensorMap.MIECHOW_PARKOWE -> {
          progressToUpdate = sensorParkoweProgress
          textViewToUpdatePm25 = sensorParkoweDetailsPm25
          textViewToUpdatePm10 = sensorParkoweDetailsPm10
          chartViewToUpdate = sensorParkoweHistoryChart
        }
        SensorMap.MIECHOW_SZPITALNA -> {
          progressToUpdate = sensorSzpitalnaProgress
          textViewToUpdatePm25 = sensorSzpitalnaDetailsPm25
          textViewToUpdatePm10 = sensorSzpitalnaDetailsPm10
          chartViewToUpdate = sensorSzpitalnaHistoryChart
        }
        SensorMap.MIECHOW_KROTKA -> {
          progressToUpdate = sensorKrotkaProgress
          textViewToUpdatePm25 = sensorKrotkaDetailsPm25
          textViewToUpdatePm10 = sensorKrotkaDetailsPm10
          chartViewToUpdate = sensorKrotkaHistoryChart
        }
      }

      val CAQI = sensorValues.currentMeasurements.airQualityIndex
      val scaledCAQI = (100 * CAQI / 50).toInt()

      progressToUpdate!!.max = maxCAQI.toInt()
      progressToUpdate.progress = CAQI.toInt()
      progressToUpdate.reachedBarColor = getProgressColor(CAQI.toInt())

      setDetailsInfo(entry, textViewToUpdatePm25!!, textViewToUpdatePm10!!, chartViewToUpdate)
    }

    airQualityImageView.setImageResource(getLogoImage(avgCAQI))
  }

  private fun setDetailsInfo(entry: Map.Entry<String, SensorMeasurementsResponse>, textViewToUpdatePm25: TextView,
    textViewToUpdatePm10: TextView, chartViewToUpdate: BarChart?) {
    val patternPm25 = "%1\$s%% (pm 2.5)"
    val patternPm10 = "%1\$s%% (pm  10)"
    val pm25 = 100 * entry.value.currentMeasurements.pm25 / PM25_STANDARD
    val pm10 = 100 * entry.value.currentMeasurements.pm10 / PM10_STANDARD
    val infoPm25 = String.format(patternPm25, pm25.toInt())
    val infoPm10 = String.format(patternPm10, pm10.toInt())
    textViewToUpdatePm25.text = infoPm25
    textViewToUpdatePm10.text = infoPm10

    showHistoryChart(chartViewToUpdate, entry)
  }

  private fun showHistoryChart(chartViewToUpdate: BarChart?, entry: Map.Entry<String, SensorMeasurementsResponse>) {
    val entries = ArrayList<BarEntry>()
    val entriesColor = ArrayList<Int>()

    val sensorName = entry.key
    val sensorValues = entry.value
    val history = sensorValues.history
    val calendar = Calendar.getInstance()
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

    for (i in history.indices) {
      val measurementsTimeFramed = history.get(i)
      val fromDate = measurementsTimeFramed.fromDateTime
      val tillDate = measurementsTimeFramed.tillDateTime
      val measurements = measurementsTimeFramed.measurements
      val pm25 = measurements.pm25
      val pm10 = measurements.pm10
      val caqi = measurements.airQualityIndex
      var date: Date? = null
      var hour = -1
      try {
        date = format.parse(fromDate)
        calendar.time = date
        hour = calendar.get(Calendar.HOUR_OF_DAY)
      } catch (e: ParseException) {
        Logger.e("Error parsing date from %s", fromDate)
      }

      entries.add(BarEntry(i.toFloat(), Math.round(caqi).toInt().toFloat(), hour))
      entriesColor.add(getProgressColor(caqi.toInt()))
    }


    val dataSet = BarDataSet(entries, "CAQI")
    dataSet.colors = entriesColor

    val barData = BarData(dataSet)
    barData.setValueFormatter { value, entry, dataSetIndex, viewPortHandler -> "" + value.toInt() }

    chartViewToUpdate!!.data = barData
    chartViewToUpdate.axisLeft.axisMinimum = 0f
    chartViewToUpdate.axisLeft.removeAllLimitLines()
    chartViewToUpdate.xAxis.isEnabled = false
    chartViewToUpdate.legend.isEnabled = false
    val description = Description()
    description.isEnabled = false
    chartViewToUpdate.setDrawValueAboveBar(false)
    chartViewToUpdate.description = description
    chartViewToUpdate.xAxis.setDrawGridLines(false)
    chartViewToUpdate.xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
    chartViewToUpdate.xAxis.labelRotationAngle = 90f

    chartViewToUpdate.xAxis.setDrawGridLines(false)
    chartViewToUpdate.axisLeft.setDrawGridLines(false)
    chartViewToUpdate.xAxis.setDrawLimitLinesBehindData(false)
    chartViewToUpdate.axisLeft.setDrawLimitLinesBehindData(false)
    chartViewToUpdate.xAxis.setDrawAxisLine(false)
    chartViewToUpdate.axisLeft.setDrawAxisLine(false)
    chartViewToUpdate.xAxis.isEnabled = false
    chartViewToUpdate.axisLeft.isEnabled = false

    chartViewToUpdate.invalidate()
  }

  private fun getProgressColor(airQualityIndex: Int): Int {
    return when {
      airQualityIndex >= 100 -> resources.getColor(R.color.caqiVeryHigh)
      airQualityIndex >= 75 -> resources.getColor(R.color.caqiHigh)
      airQualityIndex >= 50 -> resources.getColor(R.color.caqiMedium)
      airQualityIndex >= 25 -> resources.getColor(R.color.caqiLow)
      airQualityIndex >= 0 -> resources.getColor(R.color.caqiVeryLow)
      else -> resources.getColor(R.color.caqiUnknown)
    }
  }

  private fun getLogoImage(airQualityIndex: Double): Int {
    return when {
      airQualityIndex >= 100 -> R.drawable.explosion_123690_640
      airQualityIndex >= 75 -> R.drawable.industry_611668_640
      airQualityIndex >= 50 -> R.drawable.storm_clouds_1967017_640
      airQualityIndex >= 25 -> R.drawable.barn_1302114_640
      airQualityIndex >= 0 -> R.drawable.clouds_1552166_640
      else -> R.drawable.question_mark_1421013_640
    }
  }

  private fun makeHttpRequest(url: String?, sensor: Sensor) {
    if (url == null)
      return

    responseMap!!.remove(sensor.name)
    ionHelper.getSmth(url, apiKey) {exception:Exception?, result: JsonObject -> parseResult(exception, result, sensor)}
  }

  @Synchronized private fun parseResult(exception: Exception?, result: JsonObject, sensor: Sensor) {
    try {
      exception?.let {
        Logger.d("ERROR original exception%s", exception)
      }

      val gsonBuilder = GsonBuilder()
      val gson = gsonBuilder.create()
      val decoded = gson.fromJson(result, SensorMeasurementsResponse::class.java)
      val isDecoded = SensorMeasurementsResponse::class.java.isInstance(decoded)
      val sensorMeasurementsResponse = decoded as SensorMeasurementsResponse
      responseMap!![sensor.name] = sensorMeasurementsResponse

      tryToShowResult()
    } catch (ex: Exception) {
      Logger.d("ERROR %s", ex.toString())
    }

  }

  private fun tryToShowResult() {
    val calls = responseMap!!.size
    var responses = 0

    val iterator = responseMap!!.entries.iterator()
    while (iterator.hasNext()) {
      val responseEntry = iterator.next() as Map.Entry<String, SensorMeasurementsResponse>
      if (responseEntry.value != null) {
        responses++
      }
    }

    if (responses < calls) {
      Log.d("SRES", "only $responses available (calls=$calls)")
    } else {
      Log.d("SRES", "all $responses available (calls=$calls)")
      showResult()
    }
  }

  private fun showResult() {
    googleAnalyticsAction("action", "showResult")

    setGlobalChart()
    sensorLoadingProgress.visibility = View.GONE
    sensorResultTable.visibility = View.VISIBLE
  }


  override fun onResume() {
    super.onResume()

    googleAnalyticsAction("action", "onResume")
    googleAnalyticsScreen("MainActivity")

    restartLoading(false)
  }

  override fun onRefresh() {
    googleAnalyticsAction("action", "onRefresh")

    httpHandler!!.postDelayed({
      restartLoading(true)
      swipeLayout.isRefreshing = false
    }, 5000)

  }

  private fun setGoogleAnalytics() {
    // Obtain the shared Tracker instance.
    val application = application as AnalyticsApplication
    mTracker = application.defaultTracker
  }

  private fun googleAnalyticsScreen(screenName: String) {

    mTracker!!.setScreenName(screenName)
    mTracker!!.send(HitBuilders.ScreenViewBuilder().build())
  }

  private fun googleAnalyticsAction(category: String, action: String) {

    mTracker!!.send(HitBuilders.EventBuilder()
      .setCategory(category)
      .setAction(action)
      .build())

  }

  companion object {
    //    private static String API_KEY1 = "3e0d2eba4cad490c8aa95f3b536ce4c8";
    //    private static String API_KEY2 = "58ea136e60db4a11a08826a73fdbe358";
    //    private static String REQ_MAP_POINT = "https://airapi.airly.eu/v1/mapPoint/measurements?latitude=50.356486&longitude=20.027900";
    private val URL_REQ_MAP_POINT = "https://airapi.airly.eu/v1/mapPoint/measurements?latitude=%1\$s&longitude=%2\$s"
    private val URL_REQ_SENSOR = "https://airapi.airly.eu/v1/sensor/measurements?sensorId=%1\$s"
    private val PM25_STANDARD = 25.0
    private val PM10_STANDARD = 50.0
    private val animationTimeInMs = 500
    private var responseMap: MutableMap<String, SensorMeasurementsResponse>? = null
    private var lastLoading: Long = 0
  }
}
