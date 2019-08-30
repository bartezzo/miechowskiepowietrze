//package pl.tobzzo.miechowskiepowietrze.activities
//
//import android.os.Bundle
//import android.os.Handler
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
//import androidx.appcompat.app.AppCompatActivity
//import android.widget.ImageView
//import android.widget.ProgressBar
//import android.widget.ScrollView
//import android.widget.TableLayout
//import android.widget.TableRow
//import android.widget.TextView
//import com.daimajia.numberprogressbar.NumberProgressBar
//import com.github.mikephil.charting.charts.BarChart
//import com.github.mikephil.charting.components.Description
//import com.github.mikephil.charting.components.XAxis
//import com.github.mikephil.charting.data.BarData
//import com.github.mikephil.charting.data.BarDataSet
//import com.github.mikephil.charting.data.BarEntry
//import pl.tobzzo.miechowskiepowietrze.MpowApplication
//import pl.tobzzo.miechowskiepowietrze.R.id
//import pl.tobzzo.miechowskiepowietrze.R.layout
//import pl.tobzzo.miechowskiepowietrze.analytics.AnalyticsComponent
//import pl.tobzzo.miechowskiepowietrze.logging.LoggingManager
//import pl.tobzzo.miechowskiepowietrze.network.NetworkComponent
//import pl.tobzzo.miechowskiepowietrze.network.NetworkListener
//import pl.tobzzo.miechowskiepowietrze.rest.v1.SensorMeasurementsResponseV1
//import pl.tobzzo.miechowskiepowietrze.sensor.SensorObject
//import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace
//import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_KOPERNIKA
//import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_KROTKA
//import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_PARKOWE
//import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_RYNEK
//import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_SIKORSKIEGO
//import pl.tobzzo.miechowskiepowietrze.sensor.SensorPlace.MIECHOW_SZPITALNA
//import pl.tobzzo.miechowskiepowietrze.utils.extensions.bindView
//import pl.tobzzo.miechowskiepowietrze.utils.extensions.changeVisibility
//import pl.tobzzo.miechowskiepowietrze.utils.extensions.isVisible
//import pl.tobzzo.miechowskiepowietrze.utils.extensions.mapToBarColor
//import pl.tobzzo.miechowskiepowietrze.utils.extensions.mapToLogoImage
//import timber.log.Timber
//import java.text.ParseException
//import java.text.SimpleDateFormat
//import java.util.ArrayList
//import java.util.Calendar
//import java.util.Date
//import java.util.Locale
//import javax.inject.Inject
//
//
//private const val PM25_STANDARD = 25.0
//private const val PM10_STANDARD = 50.0
//private const val ANIMATION_TIME_IN_MS = 500
//
//class MainActivityWithCharts : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener, NetworkListener {
//
//  @Inject lateinit var sensorObject: SensorObject
//  @Inject lateinit var loggingManager: LoggingManager
//  @Inject lateinit var networkComponent: NetworkComponent
//  @Inject lateinit var analyticsComponent: AnalyticsComponent
//
//  private val swipeLayout: SwipeRefreshLayout by bindView(id.swipe_container)
//  private val sensorLoadingProgress: ProgressBar by bindView(id.sensorLoadingProgress)
//  private val sensorResultTable: TableLayout by bindView(id.sensorResultTable)
//
//  private val sensorSikorskiego: TableRow by bindView(id.sensorSikorskiego)
//  private val sensorSikorskiegoProgress: NumberProgressBar by bindView(id.sensorSikorskiegoProgress)
//  private val sensorSikorskiegoDetailsPm25: TextView by bindView(id.sensorSikorskiegoDetailsPm25)
//  private val sensorSikorskiegoDetailsPm10: TextView by bindView(id.sensorSikorskiegoDetailsPm10)
//  private val sensorSikorskiegoLegend: ImageView by bindView(id.sensorSikorskiegoLegend)
//  private val sensorSikorskiegoHistory: TableRow by bindView(id.sensorSikorskiegoHistory)
//  private val sensorSikorskiegoHistoryChart: BarChart by bindView(id.sensorSikorskiegoHistoryChart)
//
//  private val sensorRynek: TableRow by bindView(id.sensorRynek)
//  private val sensorRynekProgress: NumberProgressBar by bindView(id.sensorRynekProgress)
//  private val sensorRynekDetailsPm25: TextView by bindView(id.sensorRynekDetailsPm25)
//  private val sensorRynekDetailsPm10: TextView by bindView(id.sensorRynekDetailsPm10)
//  private val sensorRynekLegend: ImageView by bindView(id.sensorRynekLegend)
//  private val sensorRynekHistory: TableRow by bindView(id.sensorRynekHistory)
//  private val sensorRynekHistoryChart: BarChart by bindView(id.sensorRynekHistoryChart)
//
//  private val sensorKopernika: TableRow by bindView(id.sensorKopernika)
//  private val sensorKopernikaProgress: NumberProgressBar by bindView(id.sensorKopernikaProgress)
//  private val sensorKopernikaDetailsPm25: TextView by bindView(id.sensorKopernikaDetailsPm25)
//  private val sensorKopernikaDetailsPm10: TextView by bindView(id.sensorKopernikaDetailsPm10)
//  private val sensorKopernikaLegend: ImageView by bindView(id.sensorKopernikaLegend)
//  private val sensorKopernikaHistory: TableRow by bindView(id.sensorKopernikaHistory)
//  private val sensorKopernikaHistoryChart: BarChart by bindView(id.sensorKopernikaHistoryChart)
//
//  private val sensorParkowe: TableRow by bindView(id.sensorParkowe)
//  private val sensorParkoweProgress: NumberProgressBar by bindView(id.sensorParkoweProgress)
//  private val sensorParkoweDetailsPm25: TextView by bindView(id.sensorParkoweDetailsPm25)
//  private val sensorParkoweDetailsPm10: TextView by bindView(id.sensorParkoweDetailsPm10)
//  private val sensorParkoweLegend: ImageView by bindView(id.sensorParkoweLegend)
//  private val sensorParkoweHistory: TableRow by bindView(id.sensorParkoweHistory)
//  private val sensorParkoweHistoryChart: BarChart by bindView(id.sensorParkoweHistoryChart)
//
//  private val sensorSzpitalna: TableRow by bindView(id.sensorSzpitalna)
//  private val sensorSzpitalnaProgress: NumberProgressBar by bindView(id.sensorSzpitalnaProgress)
//  private val sensorSzpitalnaDetailsPm25: TextView by bindView(id.sensorSzpitalnaDetailsPm25)
//  private val sensorSzpitalnaDetailsPm10: TextView by bindView(id.sensorSzpitalnaDetailsPm10)
//  private val sensorSzpitalnaLegend: ImageView by bindView(id.sensorSzpitalnaLegend)
//  private val sensorSzpitalnaHistory: TableRow by bindView(id.sensorSzpitalnaHistory)
//  private val sensorSzpitalnaHistoryChart: BarChart by bindView(id.sensorSzpitalnaHistoryChart)
//
//  private val sensorKrotka: TableRow by bindView(id.sensorKrotka)
//  private val sensorKrotkaProgress: NumberProgressBar by bindView(id.sensorKrotkaProgress)
//  private val sensorKrotkaDetailsPm25: TextView by bindView(id.sensorKrotkaDetailsPm25)
//  private val sensorKrotkaDetailsPm10: TextView by bindView(id.sensorKrotkaDetailsPm10)
//  private val sensorKrotkaLegend: ImageView by bindView(id.sensorKrotkaLegend)
//  private val sensorKrotkaHistory: TableRow by bindView(id.sensorKrotkaHistory)
//  private val sensorKrotkaHistoryChart: BarChart by bindView(id.sensorKrotkaHistoryChart)
//
//  private val airQualityImageView: ImageView by bindView(id.airQualityImageView)
//  private val sensorsScrollView: ScrollView by bindView(id.sensorsScrollView)
//
//  private var httpHandler: Handler? = null
//  private var fakeCAQI = -1.0
//
//  override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(layout.activity_main)
//
//    (this.application as MpowApplication).appComponent.inject(this)
//    loggingManager.initialize()
//    networkComponent.initialize()
//    analyticsComponent.initialize()
//
//    networkComponent.attachNetworkListener(this)
//  }
//
//  override fun onPostCreate(savedInstanceState: Bundle?) {
//    super.onPostCreate(savedInstanceState)
//
//    analyticsComponent.logAction("logAction", "onCreate")
//    setElements()
//    setListeners()
//  }
//
//  override fun onDestroy() {
//    super.onDestroy()
//    networkComponent.detachNetworkListener(this)
//  }
//
//  override fun onValuesLoading() {
//    sensorLoadingProgress.isVisible = true
//    sensorResultTable.isVisible = false
//  }
//
//  override fun onValuesAvailable() {
//    setGlobalChart()
//    sensorLoadingProgress.isVisible = false
//    sensorResultTable.isVisible = true
//  }
//
//  private fun setElements() {
//    httpHandler = Handler()
//
//    swipeLayout.setOnRefreshListener(this)
//    swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
//      android.R.color.holo_green_light,
//      android.R.color.holo_orange_light,
//      android.R.color.holo_red_light)
//  }
//
//  private fun setListeners() {
//    airQualityImageView.setOnClickListener {
//      analyticsComponent.logAction("logAction", "airQualityImageView")
//      fakeCAQI = (fakeCAQI + 25) % 168 - 1
//      airQualityImageView.setImageResource(fakeCAQI.mapToLogoImage())
//    }
//
//    sensorSikorskiego.setOnClickListener {
//      analyticsComponent.logAction("actionHistory", "sensorSikorskiegoHistory")
//      sensorSikorskiegoHistory.changeVisibility()
//      sensorSikorskiegoHistoryChart.animateY(ANIMATION_TIME_IN_MS)
//    }
//
//    sensorRynek.setOnClickListener {
//      analyticsComponent.logAction("actionHistory", "sensorRynekHistory")
//      sensorRynekHistory.changeVisibility()
//      sensorRynekHistoryChart.animateY(ANIMATION_TIME_IN_MS)
//    }
//
//    sensorKopernika.setOnClickListener {
//      analyticsComponent.logAction("actionHistory", "sensorKopernikaHistory")
//      sensorKopernikaHistory.changeVisibility()
//      sensorKopernikaHistoryChart.animateY(ANIMATION_TIME_IN_MS)
//    }
//
//    sensorParkowe.setOnClickListener {
//      analyticsComponent.logAction("actionHistory", "sensorParkoweHistory")
//      sensorParkoweHistory.changeVisibility()
//      sensorParkoweHistoryChart.animateY(ANIMATION_TIME_IN_MS)
//    }
//
//    sensorSzpitalna.setOnClickListener {
//      analyticsComponent.logAction("actionHistory", "sensorSzpitalnaHistory")
//      sensorSzpitalnaHistory.changeVisibility()
//      sensorSzpitalnaHistoryChart.animateY(ANIMATION_TIME_IN_MS)
//    }
//
//    sensorKrotka.setOnClickListener {
//      analyticsComponent.logAction("actionHistory", "sensorKrotkaHistory")
//      sensorKrotkaHistory.changeVisibility()
//      sensorKrotkaHistoryChart.animateY(ANIMATION_TIME_IN_MS)
//    }
//  }
//
//  private fun setGlobalChart() {
//    var iterator: Iterator<*> = networkComponent.getResponseMap()!!.entries.iterator()
//    var maxCAQI = 0.0
//    var scaledMaxCAQI = 0
//    var sumCAQI = 0.0
//    var avgCAQI = 0.0
//    var countCAQI = 0
//
//    while (iterator.hasNext()) {
//      val entry = iterator.next() as Map.Entry<SensorPlace, SensorMeasurementsResponseV1>
//      val CAQI = entry.value.currentMeasurementsV1.airQualityIndex
//      maxCAQI = Math.max(maxCAQI, CAQI)
//      sumCAQI += CAQI
//      countCAQI += 1
//    }
//    scaledMaxCAQI = (100 * maxCAQI / 50).toInt()
//    avgCAQI = sumCAQI / countCAQI
//
//    iterator = networkComponent.getResponseMap()!!.entries.iterator()
//    while (iterator.hasNext()) {
//      val entry = iterator.next() as Map.Entry<SensorPlace, SensorMeasurementsResponseV1>
//      val sensorName = entry.key
//      val sensorValues = entry.value
//      var progressToUpdate: NumberProgressBar?
//      var textViewToUpdatePm25: TextView?
//      var textViewToUpdatePm10: TextView?
//      var chartViewToUpdate: BarChart?
//
//      when (sensorName) {
//        MIECHOW_SIKORSKIEGO -> {
//          progressToUpdate = sensorSikorskiegoProgress
//          textViewToUpdatePm25 = sensorSikorskiegoDetailsPm25
//          textViewToUpdatePm10 = sensorSikorskiegoDetailsPm10
//          chartViewToUpdate = sensorSikorskiegoHistoryChart
//        }
//        MIECHOW_RYNEK -> {
//          progressToUpdate = sensorRynekProgress
//          textViewToUpdatePm25 = sensorRynekDetailsPm25
//          textViewToUpdatePm10 = sensorRynekDetailsPm10
//          chartViewToUpdate = sensorRynekHistoryChart
//        }
//        MIECHOW_KOPERNIKA -> {
//          progressToUpdate = sensorKopernikaProgress
//          textViewToUpdatePm25 = sensorKopernikaDetailsPm25
//          textViewToUpdatePm10 = sensorKopernikaDetailsPm10
//          chartViewToUpdate = sensorKopernikaHistoryChart
//        }
//        MIECHOW_PARKOWE -> {
//          progressToUpdate = sensorParkoweProgress
//          textViewToUpdatePm25 = sensorParkoweDetailsPm25
//          textViewToUpdatePm10 = sensorParkoweDetailsPm10
//          chartViewToUpdate = sensorParkoweHistoryChart
//        }
//        MIECHOW_SZPITALNA -> {
//          progressToUpdate = sensorSzpitalnaProgress
//          textViewToUpdatePm25 = sensorSzpitalnaDetailsPm25
//          textViewToUpdatePm10 = sensorSzpitalnaDetailsPm10
//          chartViewToUpdate = sensorSzpitalnaHistoryChart
//        }
//        MIECHOW_KROTKA -> {
//          progressToUpdate = sensorKrotkaProgress
//          textViewToUpdatePm25 = sensorKrotkaDetailsPm25
//          textViewToUpdatePm10 = sensorKrotkaDetailsPm10
//          chartViewToUpdate = sensorKrotkaHistoryChart
//        }
//        else -> {
//          throw Exception("Wrong sensor passed")
//        }
//      }
//
//      val CAQI = sensorValues.currentMeasurementsV1.airQualityIndex
//      val scaledCAQI = (100 * CAQI / 50).toInt()
//
//      progressToUpdate.max = maxCAQI.toInt()
//      progressToUpdate.progress = CAQI.toInt()
//      progressToUpdate.reachedBarColor = CAQI.mapToBarColor(resources)
//
//      setDetailsInfo(entry, textViewToUpdatePm25, textViewToUpdatePm10, chartViewToUpdate)
//    }
//
//    airQualityImageView.setImageResource(avgCAQI.mapToBarColor(resources))
//  }
//
//  private fun setDetailsInfo(entry: Map.Entry<SensorPlace, SensorMeasurementsResponseV1>, textViewToUpdatePm25: TextView,
//    textViewToUpdatePm10: TextView, chartViewToUpdate: BarChart?) {
//    val patternPm25 = "%1\$s%% (pm 2.5)"
//    val patternPm10 = "%1\$s%% (pm  10)"
//    val pm25 = 100 * entry.value.currentMeasurementsV1.pm25 / PM25_STANDARD
//    val pm10 = 100 * entry.value.currentMeasurementsV1.pm10 / PM10_STANDARD
//    val infoPm25 = String.format(patternPm25, pm25.toInt())
//    val infoPm10 = String.format(patternPm10, pm10.toInt())
//    textViewToUpdatePm25.text = infoPm25
//    textViewToUpdatePm10.text = infoPm10
//
//    showHistoryChart(chartViewToUpdate, entry)
//  }
//
//  private fun showHistoryChart(chartViewToUpdate: BarChart?, entry: Map.Entry<SensorPlace, SensorMeasurementsResponseV1>) {
//    val entries = ArrayList<BarEntry>()
//    val entriesColor = ArrayList<Int>()
//
//    val sensorName = entry.key
//    val sensorValues = entry.value
//    val history = sensorValues.history
//    val calendar = Calendar.getInstance()
//    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
//
//    for (i in history.indices) {
//      val measurementsTimeFramed = history.get(i)
//      val fromDate = measurementsTimeFramed.fromDateTime
//      val tillDate = measurementsTimeFramed.tillDateTime
//      val measurements = measurementsTimeFramed.measurementsV1
//      val pm25 = measurements.pm25
//      val pm10 = measurements.pm10
//      val caqi = measurements.airQualityIndex
//      var date: Date? = null
//      var hour = -1
//      try {
//        date = format.parse(fromDate)
//        calendar.time = date
//        hour = calendar.get(Calendar.HOUR_OF_DAY)
//      } catch (e: ParseException) {
//        Timber.e("Error parsing date from $fromDate")
//      }
//
//      entries.add(BarEntry(i.toFloat(), Math.round(caqi).toInt().toFloat(), hour))
//      entriesColor.add(caqi.mapToBarColor(resources))
//    }
//
//
//    val dataSet = BarDataSet(entries, "CAQI")
//    dataSet.colors = entriesColor
//
//    val barData = BarData(dataSet)
//    barData.setValueFormatter { value, entry, dataSetIndex, viewPortHandler -> "" + value.toInt() }
//
//    chartViewToUpdate!!.data = barData
//    chartViewToUpdate.axisLeft.axisMinimum = 0f
//    chartViewToUpdate.axisLeft.removeAllLimitLines()
//    chartViewToUpdate.xAxis.isEnabled = false
//    chartViewToUpdate.legend.isEnabled = false
//    val description = Description()
//    description.isEnabled = false
//    chartViewToUpdate.setDrawValueAboveBar(false)
//    chartViewToUpdate.description = description
//    chartViewToUpdate.xAxis.setDrawGridLines(false)
//    chartViewToUpdate.xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
//    chartViewToUpdate.xAxis.labelRotationAngle = 90f
//
//    chartViewToUpdate.xAxis.setDrawGridLines(false)
//    chartViewToUpdate.axisLeft.setDrawGridLines(false)
//    chartViewToUpdate.xAxis.setDrawLimitLinesBehindData(false)
//    chartViewToUpdate.axisLeft.setDrawLimitLinesBehindData(false)
//    chartViewToUpdate.xAxis.setDrawAxisLine(false)
//    chartViewToUpdate.axisLeft.setDrawAxisLine(false)
//    chartViewToUpdate.xAxis.isEnabled = false
//    chartViewToUpdate.axisLeft.isEnabled = false
//
//    chartViewToUpdate.invalidate()
//  }
//
//  override fun onResume() {
//    super.onResume()
//
//    analyticsComponent.logAction("logAction", "onResume")
//    analyticsComponent.logScreen("MainActivity")
//    networkComponent.restartLoading(false)
//  }
//
//  override fun onRefresh() {
//    analyticsComponent.logAction("logAction", "onRefresh")
//
//    httpHandler!!.postDelayed({
//      networkComponent.restartLoading(true)
//      swipeLayout.isRefreshing = false
//    }, 5000)
//
//  }
//}