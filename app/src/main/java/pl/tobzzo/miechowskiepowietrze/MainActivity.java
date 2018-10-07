package pl.tobzzo.miechowskiepowietrze;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.orhanobut.logger.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pl.tobzzo.miechowskiepowietrze.rest.AllMeasurements;
import pl.tobzzo.miechowskiepowietrze.rest.MeasurementsTimeFramed;
import pl.tobzzo.miechowskiepowietrze.rest.SensorMeasurementsResponse;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
//    private static String API_KEY1 = "3e0d2eba4cad490c8aa95f3b536ce4c8";
//    private static String API_KEY2 = "58ea136e60db4a11a08826a73fdbe358";
    //    private static String REQ_MAP_POINT = "https://airapi.airly.eu/v1/mapPoint/measurements?latitude=50.356486&longitude=20.027900";
    private static String URL_REQ_MAP_POINT = "https://airapi.airly.eu/v1/mapPoint/measurements?latitude=%1$s&longitude=%2$s";
    private static String URL_REQ_SENSOR = "https://airapi.airly.eu/v1/sensor/measurements?sensorId=%1$s";
    private static double PM25_STANDARD = 25;
    private static double PM10_STANDARD = 50;
    private static int animationTimeInMs = 500;

    //    private String globalInfo = "";
    private Handler httpHandler;
    private SwipeRefreshLayout swipeLayout;
    private static Map responseMap;
    //    private Button getInfoButton;
//    private Button exitButton;
//    private TextView resultTextView;
    private ProgressBar sensorLoadingProgress;
    private TableLayout sensorResultTable;
    //    private TextView sensorInfoTextView;
    //    private NumberProgressBar sensorSredniaProgress;
//    private TextView sensorSredniaDetails;
    private TableRow sensorSikorskiego;
    private NumberProgressBar sensorSikorskiegoProgress;
    private TextView sensorSikorskiegoDetailsPm25;
    private TextView sensorSikorskiegoDetailsPm10;
    private ImageView sensorSikorskiegoLegend;
    private TableRow sensorSikorskiegoHistory;
    private BarChart sensorSikorskiegoHistoryChart;

    private TableRow sensorRynek;
    private NumberProgressBar sensorRynekProgress;
    private TextView sensorRynekDetailsPm25;
    private TextView sensorRynekDetailsPm10;
    private ImageView sensorRynekLegend;
    private TableRow sensorRynekHistory;
    private BarChart sensorRynekHistoryChart;

    private TableRow sensorKopernika;
    private NumberProgressBar sensorKopernikaProgress;
    private TextView sensorKopernikaDetailsPm25;
    private TextView sensorKopernikaDetailsPm10;
    private ImageView sensorKopernikaLegend;
    private TableRow sensorKopernikaHistory;
    private BarChart sensorKopernikaHistoryChart;

    private TableRow sensorXXXlecia;
    private NumberProgressBar sensorXXXleciaProgress;
    private TextView sensorXXXleciaDetailsPm25;
    private TextView sensorXXXleciaDetailsPm10;
    private ImageView sensorXXXleciaLegend;
    private TableRow sensorXXXleciaHistory;
    private BarChart sensorXXXleciaHistoryChart;

    private TableRow sensorSzpitalna;
    private NumberProgressBar sensorSzpitalnaProgress;
    private TextView sensorSzpitalnaDetailsPm25;
    private TextView sensorSzpitalnaDetailsPm10;
    private ImageView sensorSzpitalnaLegend;
    private TableRow sensorSzpitalnaHistory;
    private BarChart sensorSzpitalnaHistoryChart;

    private TableRow sensorKrotka;
    private NumberProgressBar sensorKrotkaProgress;
    private TextView sensorKrotkaDetailsPm25;
    private TextView sensorKrotkaDetailsPm10;
    private ImageView sensorKrotkaLegend;
    private TableRow sensorKrotkaHistory;
    private BarChart sensorKrotkaHistoryChart;

    private ImageView airQualityImageView;
    private int fakeCAQI = -1;
    private ScrollView sensorsScrollView;
    private static long lastLoading = 0;
    private Tracker mTracker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setGoogleAnalytics();

        googleAnalyticsAction("action", "onCreate");
        setElements();
        setListeners();
    }

    private void setElements() {
        httpHandler = new Handler();

        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

//        getInfoButton = (Button) findViewById(R.id.getInfoButton);
//        exitButton = (Button) findViewById(R.id.exitButton);
//        resultTextView = ((TextView) findViewById(R.id.resultTextView));
        sensorLoadingProgress = (ProgressBar) findViewById(R.id.sensorLoadingProgress);
        sensorResultTable = (TableLayout) findViewById(R.id.sensorResultTable);
//        sensorInfoTextView = (TextView) findViewById(R.id.sensorInfoTextView);
        sensorsScrollView = (ScrollView) findViewById(R.id.sensorsScrollView);

        sensorSikorskiego = (TableRow) findViewById(R.id.sensorSikorskiego);
        sensorSikorskiegoProgress = (NumberProgressBar) findViewById(R.id.sensorSikorskiegoProgress);
        sensorSikorskiegoDetailsPm25 = (TextView) findViewById(R.id.sensorSikorskiegoDetailsPm25);
        sensorSikorskiegoDetailsPm10 = (TextView) findViewById(R.id.sensorSikorskiegoDetailsPm10);
        sensorSikorskiegoLegend = (ImageView) findViewById(R.id.sensorSikorskiegoLegend);
        sensorSikorskiegoHistory = (TableRow) findViewById(R.id.sensorSikorskiegoHistory);
        sensorSikorskiegoHistoryChart = (BarChart) findViewById(R.id.sensorSikorskiegoHistoryChart);

        sensorRynek = (TableRow) findViewById(R.id.sensorRynek);
        sensorRynekProgress = (NumberProgressBar) findViewById(R.id.sensorRynekProgress);
        sensorRynekDetailsPm25 = (TextView) findViewById(R.id.sensorRynekDetailsPm25);
        sensorRynekDetailsPm10 = (TextView) findViewById(R.id.sensorRynekDetailsPm10);
        sensorRynekLegend = (ImageView) findViewById(R.id.sensorRynekLegend);
        sensorRynekHistory = (TableRow) findViewById(R.id.sensorRynekHistory);
        sensorRynekHistoryChart = (BarChart) findViewById(R.id.sensorRynekHistoryChart);

        sensorKopernika = (TableRow) findViewById(R.id.sensorKopernika);
        sensorKopernikaProgress = (NumberProgressBar) findViewById(R.id.sensorKopernikaProgress);
        sensorKopernikaDetailsPm25 = (TextView) findViewById(R.id.sensorKopernikaDetailsPm25);
        sensorKopernikaDetailsPm10 = (TextView) findViewById(R.id.sensorKopernikaDetailsPm10);
        sensorKopernikaLegend = (ImageView) findViewById(R.id.sensorKopernikaLegend);
        sensorKopernikaHistory = (TableRow) findViewById(R.id.sensorKopernikaHistory);
        sensorKopernikaHistoryChart = (BarChart) findViewById(R.id.sensorKopernikaHistoryChart);

        sensorXXXlecia = (TableRow) findViewById(R.id.sensorXXXlecia);
        sensorXXXleciaProgress = (NumberProgressBar) findViewById(R.id.sensorXXXleciaProgress);
        sensorXXXleciaDetailsPm25 = (TextView) findViewById(R.id.sensorXXXleciaDetailsPm25);
        sensorXXXleciaDetailsPm10 = (TextView) findViewById(R.id.sensorXXXleciaDetailsPm10);
        sensorXXXleciaLegend = (ImageView) findViewById(R.id.sensorXXXleciaLegend);
        sensorXXXleciaHistory = (TableRow) findViewById(R.id.sensorXXXleciaHistory);
        sensorXXXleciaHistoryChart = (BarChart) findViewById(R.id.sensorXXXleciaHistoryChart);

        sensorSzpitalna = (TableRow) findViewById(R.id.sensorSzpitalna);
        sensorSzpitalnaProgress = (NumberProgressBar) findViewById(R.id.sensorSzpitalnaProgress);
        sensorSzpitalnaDetailsPm25 = (TextView) findViewById(R.id.sensorSzpitalnaDetailsPm25);
        sensorSzpitalnaDetailsPm10 = (TextView) findViewById(R.id.sensorSzpitalnaDetailsPm10);
        sensorSzpitalnaLegend = (ImageView) findViewById(R.id.sensorSzpitalnaLegend);
        sensorSzpitalnaHistory = (TableRow) findViewById(R.id.sensorSzpitalnaHistory);
        sensorSzpitalnaHistoryChart = (BarChart) findViewById(R.id.sensorSzpitalnaHistoryChart);

        sensorKrotka = (TableRow) findViewById(R.id.sensorKrotka);
        sensorKrotkaProgress = (NumberProgressBar) findViewById(R.id.sensorKrotkaProgress);
        sensorKrotkaDetailsPm25 = (TextView) findViewById(R.id.sensorKrotkaDetailsPm25);
        sensorKrotkaDetailsPm10 = (TextView) findViewById(R.id.sensorKrotkaDetailsPm10);
        sensorKrotkaLegend = (ImageView) findViewById(R.id.sensorKrotkaLegend);
        sensorKrotkaHistory = (TableRow) findViewById(R.id.sensorKrotkaHistory);
        sensorKrotkaHistoryChart = (BarChart) findViewById(R.id.sensorKrotkaHistoryChart);

        airQualityImageView = (ImageView) findViewById(R.id.airQualityImageView);
    }

    private void setListeners() {
        airQualityImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleAnalyticsAction("action", "airQualityImageView");
                fakeCAQI = ((fakeCAQI + 25) % 168) - 1;
                airQualityImageView.setImageResource(getLogoImage(fakeCAQI));
            }
        });

        sensorSikorskiego.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleAnalyticsAction("actionHistory", "sensorSikorskiegoHistory");
                changeViewVisibility(sensorSikorskiegoHistory);
                sensorSikorskiegoHistoryChart.animateY(animationTimeInMs);
//                sensorsScrollView.scrollTo(0, sensorSikorskiego.getTop());
            }
        });

        sensorRynek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleAnalyticsAction("actionHistory", "sensorRynekHistory");
                changeViewVisibility(sensorRynekHistory);
                sensorRynekHistoryChart.animateY(animationTimeInMs);
//                sensorsScrollView.scrollTo(0, sensorRynek.getTop());
            }
        });

        sensorKopernika.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleAnalyticsAction("actionHistory", "sensorKopernikaHistory");
                changeViewVisibility(sensorKopernikaHistory);
                sensorKopernikaHistoryChart.animateY(animationTimeInMs);
//                sensorsScrollView.scrollTo(0, sensorKopernika.getTop());
            }
        });

        sensorXXXlecia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleAnalyticsAction("actionHistory", "sensorXXXleciaHistory");
                changeViewVisibility(sensorXXXleciaHistory);
                sensorXXXleciaHistoryChart.animateY(animationTimeInMs);
//                sensorsScrollView.scrollTo(0, sensorXXXlecia.getTop());
            }
        });

        sensorSzpitalna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleAnalyticsAction("actionHistory", "sensorSzpitalnaHistory");
                changeViewVisibility(sensorSzpitalnaHistory);
                sensorSzpitalnaHistoryChart.animateY(animationTimeInMs);
//                sensorsScrollView.scrollTo(0, sensorSzpitalna.getTop());
            }
        });

        sensorKrotka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleAnalyticsAction("actionHistory", "sensorKrotkaHistory");
                changeViewVisibility(sensorKrotkaHistory);
                sensorKrotkaHistoryChart.animateY(animationTimeInMs);
//                sensorsScrollView.scrollTo(0, sensorKrotka.getTop());
            }
        });

    }

    private void changeViewVisibility(View v) {
        v.setVisibility(v.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
    }

    private void restartLoading(boolean forceRefresh) {
        googleAnalyticsAction("action", "restartLoading");

        if (forceRefresh)
            lastLoading = 0;

        if (System.currentTimeMillis() - lastLoading < 30 * 60 * 1000) {
            showResult();
        } else {
            lastLoading = System.currentTimeMillis();
            responseMap = new HashMap<String, SensorMeasurementsResponse>();
            httpHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listSensorsMeasurements();
                }
            }, 2 * 1000);
//            sensorInfoTextView.setVisibility(View.GONE);
            sensorLoadingProgress.setVisibility(View.VISIBLE);
            sensorResultTable.setVisibility(View.GONE);
//        setGlobalInfo(null);

        }
    }

    private void listSensorsMeasurements() {
        String url = null;
        Iterator iterator = SensorMap.getSensors().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Sensor> sensorEntry = (Map.Entry<String, Sensor>) iterator.next();
            Sensor sensor = sensorEntry.getValue();
            if (sensor.getSesnorType() == SensorMap.SensorType.REQ_MAP_POINT) {
                url = String.format(URL_REQ_MAP_POINT, sensor.getGpsLatitude(), sensor.getGpsLongitude());
            } else if (sensor.getSesnorType() == SensorMap.SensorType.REQ_SENSOR) {
                url = String.format(URL_REQ_SENSOR, sensor.getId());
            }

            makeHttpRequest(url, sensor);
        }
    }

//    private void setGlobalInfo(String info) {
//        if (info == null) {
//            globalInfo = "Odczytywanie";
//        } else {
//            globalInfo += "\n" + info + "\n------------------------------------";
//        }
//
//        resultTextView.setText(globalInfo);
//
//    }

    private void setGlobalChart() {
        Iterator iterator = responseMap.entrySet().iterator();
        double maxCAQI = 0;
        int scaledMaxCAQI = 0;
        double sumCAQI = 0;
        double avgCAQI = 0;
        int countCAQI = 0;

        while (iterator.hasNext()) {
            Map.Entry<String, SensorMeasurementsResponse> entry = (Map.Entry<String, SensorMeasurementsResponse>) iterator.next();
            double CAQI = entry.getValue().getCurrentMeasurements().getAirQualityIndex();
            maxCAQI = Math.max(maxCAQI, CAQI);
            sumCAQI += CAQI;
            countCAQI += 1;
        }
        scaledMaxCAQI = (int) (100 * maxCAQI / 50);
        avgCAQI = sumCAQI / countCAQI;

        iterator = responseMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SensorMeasurementsResponse> entry = (Map.Entry<String, SensorMeasurementsResponse>) iterator.next();
            String sensorName = entry.getKey();
            SensorMeasurementsResponse sensorValues = entry.getValue();
            NumberProgressBar progressToUpdate = null;
            TextView textViewToUpdatePm25 = null;
            TextView textViewToUpdatePm10 = null;
            BarChart chartViewToUpdate = null;

//            if (sensorName.equals(SensorMap.MIECHOW_SREDNIA)) {
//                progressToUpdate = sensorSredniaProgress;
//                textViewToUpdate = sensorSredniaDetails;
//            } else
            if (sensorName.equals(SensorMap.MIECHOW_SIKORSKIEGO)) {
                progressToUpdate = sensorSikorskiegoProgress;
                textViewToUpdatePm25 = sensorSikorskiegoDetailsPm25;
                textViewToUpdatePm10 = sensorSikorskiegoDetailsPm10;
                chartViewToUpdate = sensorSikorskiegoHistoryChart;
            } else if (sensorName.equals(SensorMap.MIECHOW_RYNEK)) {
                progressToUpdate = sensorRynekProgress;
                textViewToUpdatePm25 = sensorRynekDetailsPm25;
                textViewToUpdatePm10 = sensorRynekDetailsPm10;
                chartViewToUpdate = sensorRynekHistoryChart;
            } else if (sensorName.equals(SensorMap.MIECHOW_KOPERNIKA)) {
                progressToUpdate = sensorKopernikaProgress;
                textViewToUpdatePm25 = sensorKopernikaDetailsPm25;
                textViewToUpdatePm10 = sensorKopernikaDetailsPm10;
                chartViewToUpdate = sensorKopernikaHistoryChart;
            } else if (sensorName.equals(SensorMap.MIECHOW_XXXLECIA)) {
                progressToUpdate = sensorXXXleciaProgress;
                textViewToUpdatePm25 = sensorXXXleciaDetailsPm25;
                textViewToUpdatePm10 = sensorXXXleciaDetailsPm10;
                chartViewToUpdate = sensorXXXleciaHistoryChart;
            } else if (sensorName.equals(SensorMap.MIECHOW_SZPITALNA)) {
                progressToUpdate = sensorSzpitalnaProgress;
                textViewToUpdatePm25 = sensorSzpitalnaDetailsPm25;
                textViewToUpdatePm10 = sensorSzpitalnaDetailsPm10;
                chartViewToUpdate = sensorSzpitalnaHistoryChart;
            } else if (sensorName.equals(SensorMap.MIECHOW_KROTKA)) {
                progressToUpdate = sensorKrotkaProgress;
                textViewToUpdatePm25 = sensorKrotkaDetailsPm25;
                textViewToUpdatePm10 = sensorKrotkaDetailsPm10;
                chartViewToUpdate = sensorKrotkaHistoryChart;
            }

            double CAQI = sensorValues.getCurrentMeasurements().getAirQualityIndex();
            int scaledCAQI = (int) (100 * CAQI / 50);

            progressToUpdate.setMax((int) maxCAQI);
            progressToUpdate.setProgress((int) CAQI);
            progressToUpdate.setReachedBarColor(getProgressColor((int) CAQI));

            setDetailsInfo(entry, textViewToUpdatePm25, textViewToUpdatePm10, chartViewToUpdate);
        }

        airQualityImageView.setImageResource(getLogoImage(avgCAQI));
    }

    private void setDetailsInfo(Map.Entry<String, SensorMeasurementsResponse> entry, TextView textViewToUpdatePm25, TextView textViewToUpdatePm10, BarChart chartViewToUpdate) {
        String patternPm25 = "%1$s%% (pm 2.5)";
        String patternPm10 = "%1$s%% (pm  10)";
        double pm25 = 100 * entry.getValue().getCurrentMeasurements().getPm25() / PM25_STANDARD;
        double pm10 = 100 * entry.getValue().getCurrentMeasurements().getPm10() / PM10_STANDARD;
        String infoPm25 = String.format(patternPm25, (int) pm25);
        String infoPm10 = String.format(patternPm10, (int) pm10);
        textViewToUpdatePm25.setText(infoPm25);
        textViewToUpdatePm10.setText(infoPm10);

        showHistoryChart(chartViewToUpdate, entry);
    }

    private void showHistoryChart(BarChart chartViewToUpdate, Map.Entry<String, SensorMeasurementsResponse> entry) {
        List<BarEntry> entries = new ArrayList<BarEntry>();
        List<Integer> entriesColor = new ArrayList<>();

        String sensorName = entry.getKey();
        SensorMeasurementsResponse sensorValues = entry.getValue();
        List<MeasurementsTimeFramed> history = sensorValues.getHistory();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

        for (int i = 0; i < history.size(); ++i) {
            MeasurementsTimeFramed measurementsTimeFramed = history.get(i);
            String fromDate = measurementsTimeFramed.getFromDateTime();
            String tillDate = measurementsTimeFramed.getTillDateTime();
            AllMeasurements measurements = measurementsTimeFramed.getMeasurements();
            double pm25 = measurements.getPm25();
            double pm10 = measurements.getPm10();
            double caqi = measurements.getAirQualityIndex();
            Date date = null;
            int hour = -1;
            try {
                date = format.parse(fromDate);
                calendar.setTime(date);
                hour = calendar.get(Calendar.HOUR_OF_DAY);
            } catch (ParseException e) {
                Logger.e("Error parsing date from %s", fromDate);
            }
            entries.add(new BarEntry(i, (int) Math.round(caqi), hour));
            entriesColor.add(getProgressColor((int) caqi));
        }


        BarDataSet dataSet = new BarDataSet(entries, "CAQI");
        dataSet.setColors(entriesColor);

        BarData barData = new BarData(dataSet);
        barData.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return "" + (int) value;
            }
        });

        chartViewToUpdate.setData(barData);
        chartViewToUpdate.getAxisLeft().setAxisMinimum(0);
        chartViewToUpdate.getAxisLeft().removeAllLimitLines();
        chartViewToUpdate.getXAxis().setEnabled(false);
        chartViewToUpdate.getLegend().setEnabled(false);
        Description description = new Description();
        description.setEnabled(false);
        chartViewToUpdate.setDrawValueAboveBar(false);
        chartViewToUpdate.setDescription(description);
        chartViewToUpdate.getXAxis().setDrawGridLines(false);
        chartViewToUpdate.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        chartViewToUpdate.getXAxis().setLabelRotationAngle(90);

        chartViewToUpdate.getXAxis().setDrawGridLines(false);
        chartViewToUpdate.getAxisLeft().setDrawGridLines(false);
        chartViewToUpdate.getXAxis().setDrawLimitLinesBehindData(false);
        chartViewToUpdate.getAxisLeft().setDrawLimitLinesBehindData(false);
        chartViewToUpdate.getXAxis().setDrawAxisLine(false);
        chartViewToUpdate.getAxisLeft().setDrawAxisLine(false);
        chartViewToUpdate.getXAxis().setEnabled(false);
        chartViewToUpdate.getAxisLeft().setEnabled(false);

        chartViewToUpdate.invalidate();
    }

    private void animateProgress(NumberProgressBar progressToUpdate, int airQualityIndex) {
        /*for(int i = 0; i<= airQualityIndex / 10; ++i) {
            progressToUpdate.setProgress(i*10);
        }*/

    }

    private int getProgressColor(int airQualityIndex) {
        if (airQualityIndex >= 100)
            return getResources().getColor(R.color.caqiVeryHigh);
        else if (airQualityIndex >= 75)
            return getResources().getColor(R.color.caqiHigh);
        else if (airQualityIndex >= 50)
            return getResources().getColor(R.color.caqiMedium);
        else if (airQualityIndex >= 25)
            return getResources().getColor(R.color.caqiLow);
        else if (airQualityIndex >= 0)
            return getResources().getColor(R.color.caqiVeryLow);
        else
            return getResources().getColor(R.color.caqiUnknown);
    }

    private int getLogoImage(double airQualityIndex) {
        if (airQualityIndex >= 100)
            return R.drawable.explosion_123690_640;
        else if (airQualityIndex >= 75)
            return R.drawable.industry_611668_640;
        else if (airQualityIndex >= 50)
            return R.drawable.storm_clouds_1967017_640;
        else if (airQualityIndex >= 25)
            return R.drawable.barn_1302114_640;
        else if (airQualityIndex >= 0)
            return R.drawable.clouds_1552166_640;
        else
            return R.drawable.question_mark_1421013_640;
    }

    private String getApiKey(){


        String result = "";
        if(System.currentTimeMillis()%2==0)
            result = BuildConfig.hiddenPassword1;
        else
            result = BuildConfig.hiddenPassword2;

        googleAnalyticsAction("getApiKey", result);
        Logger.d("api key:%s", result);
        return result;
    }
    private void makeHttpRequest(String url, final Sensor sensor) {
        if (url == null)
            return;

        responseMap.put(sensor.getName(), null);
        Ion.with(MainActivity.this)
                .load(url)
                .addHeader("apikey", getApiKey())
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        parseResult(result, sensor);
                    }
                });

    }

    synchronized private void parseResult(JsonObject result, Sensor sensor) {
        try {
            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Object decoded = gson.fromJson(result, SensorMeasurementsResponse.class);
            boolean isDecoded = SensorMeasurementsResponse.class.isInstance(decoded);
            SensorMeasurementsResponse sensorMeasurementsResponse = (SensorMeasurementsResponse) decoded;
            responseMap.put(sensor.getName(), sensorMeasurementsResponse);

            tryToShowResult();
//            Log.d("parseResult", "isDecoded" + (isDecoded ? "OK" : "NOT"));
//            String sensorInfo = ">>>>>>>>>>>>\n" + "sensor id:" + sensor.getId() + ", name:" + sensor.getName() + "\n<<<<<<<<<<<<\n\n" + sensorMeasurementsResponse.toString();
//            Log.d("parseResult", sensorInfo);
//            setGlobalInfo(sensorInfo);
//            setGlobalChart();
        } catch (Exception ex) {
            Logger.d("ERROR %s", ex.toString());
        }
    }

    private void tryToShowResult() {
        int calls = responseMap.size();
        int responses = 0;

        Iterator iterator = responseMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, SensorMeasurementsResponse> responseEntry = (Map.Entry<String, SensorMeasurementsResponse>) iterator.next();
            if (responseEntry.getValue() != null) {
                responses++;
            }
        }

        if (responses < calls) {
            Log.d("SRES", "only " + responses + " available (calls=" + calls + ")");
        } else {
            Log.d("SRES", "all " + responses + " available (calls=" + calls + ")");
            showResult();
        }
    }

    private void showResult() {
        googleAnalyticsAction("action", "showResult");

        setGlobalChart();
//        sensorInfoTextView.setVisibility(View.GONE);
        sensorLoadingProgress.setVisibility(View.GONE);
        sensorResultTable.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onResume() {
        super.onResume();

        googleAnalyticsAction("action", "onResume");
        googleAnalyticsScreen("MainActivity");

        restartLoading(false);
    }

    @Override
    public void onRefresh() {
        googleAnalyticsAction("action", "onRefresh");

        httpHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                restartLoading(true);
                swipeLayout.setRefreshing(false);
            }
        }, 5000);

    }

    private void setGoogleAnalytics() {
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
    }

    private void googleAnalyticsScreen(String screenName) {

        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void googleAnalyticsAction(String category, String action) {

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .build());

    }
}
