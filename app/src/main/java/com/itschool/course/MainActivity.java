package com.itschool.course;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Button button;
    String jsonIn, text;
    TextView textView;
    WebView webView;
    Resources res;
    Course course;
    boolean isDataLoaded;
    boolean isConnected;
    String currCoursesURL;
    Document page = null;
    private String FLAG;
    CourseGetter cg;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.buttonLoadData);
        textView = findViewById(R.id.textView);
        jsonIn = "[{\"r030\":36,\"txt\":\"Австралійський долар\",\"rate\":19.110808,\"cc\":\"AUD\",\"exchangedate\":\"04.03.2019\"}]";
        //"{\"coord\":{\"lon\":30.73,\"lat\":46.48},\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"ясно\",\"icon\":\"01d\"}],\"base\":\"stations\",\"main\":{\"temp\":296.15,\"pressure\":1020,\"humidity\":33,\"temp_min\":296.15,\"temp_max\":296.15},\"visibility\":10000,\"wind\":{\"speed\":3,\"deg\":150},\"clouds\":{\"all\":0},\"dt\":1528381800,\"sys\":{\"type\":1,\"id\":7366,\"message\":0.0021,\"country\":\"UA\",\"sunrise\":1528337103,\"sunset\":1528393643},\"id\":698740,\"name\":\"Odessa\",\"cod\":200}";
        text = "";
        isDataLoaded = false;
        isConnected = true;
        message = "";
        //   currWeatherURL = "http://api.openweathermap.org/data/2.5/weather?id=698740&appid=dac392b2d2745b3adf08ca26054d78c4&lang=ru";
        currCoursesURL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

        cg = new CourseGetter();
        cg.execute();
    }

    public void ParseCourse(){
        boolean cont = false;
        JSONObject json = null;                       //КЛАС ДЖСОН ПУСТОЙ
        try {
            json = new JSONObject(jsonIn);          //ФОРМИРУЕМ НА ОСНОВЕ СЧИТЫВАЕМОЙ СТРОКИ
            cont = true;
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        if (cont)
            try {
                //ДОСТАЁМ ПО ИМЕНАМ

                Integer r030 = json.getInt("r030");
                String txt = json.getString("txt");
                Double rate = json.getDouble("rate");
                String cc = json.getString("cc");
                String exchangedate = json.getString("exchangedate");

                course = new Course(r030, txt, rate, cc, exchangedate);
                textView.setText(course.toString());

                isDataLoaded = true;
            } catch (JSONException e) {
                e.printStackTrace();
                //drawWeather();
            }
        chCoursePng();

    }

    public void btnLoadData(View view) {

        currCoursesURL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
        //currWeatherURL = "https://api.openweathermap.org/data/2.5/forecast/daily?lat="+Coordinates.latitude+"&lon="+Coordinates.longitude+"&appid=b1b15e88fa797225412429c1c50c122a1";
        if (cg.getStatus() == AsyncTask.Status.RUNNING)
            cg.cancel(true);

        cg = new CourseGetter();
        cg.execute();
        ParseCourse();
//        drawWeather();
    }

    public void chCoursePng() {
        if (course != null)
        {
        if (isConnected) {
            if (course.getR030() == 203) {
                imageView.setImageResource(R.drawable.p203);
            } else if (course.getR030() == 643) {
                imageView.setImageResource(R.drawable.p643);
            } else if (course.getR030() == 756) {
                imageView.setImageResource(R.drawable.p756);
            } else if (course.getR030() == 826) {
                imageView.setImageResource(R.drawable.p826);
            } else if (course.getR030() == 840) {
                imageView.setImageResource(R.drawable.p840);
            } else if (course.getR030() == 933) {
                imageView.setImageResource(R.drawable.p933);
            } else if (course.getR030() == 959) {
                imageView.setImageResource(R.drawable.p959);
            } else if (course.getR030() == 961) {
                imageView.setImageResource(R.drawable.p961);
            } else if (course.getR030() == 978) {
                imageView.setImageResource(R.drawable.p978);
            } else if (course.getR030() == 985) {
                imageView.setImageResource(R.drawable.p985);
            } else
                imageView.setBackgroundResource(R.drawable.transparent);
        }
        }
    }

    class CourseGetter extends AsyncTask<Void, Void, Void> {
        private String readAll(Reader rd) throws IOException {
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {          //ПРочитывает джсон в одну строковую переменную
                sb.append((char) cp);
            }
            return sb.toString();
        }

        public void ConnectAndGetData(String url) {
            InputStream is = null;

            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);  //Проверка подключения к сети И ПОДКЛЮЧИТЬСЯ
            NetworkInfo netInfo = cm.getActiveNetworkInfo();                                                //ПРОВЕРИТЬ СОСТОЯНИЕ СЕТИ

            if (netInfo.isConnected()) {
                try {
                    is = new URL(url).openStream(); // открываем поток
                    try {
                        BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                        try {
                            jsonIn = readAll(rd);      //Считываем
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } finally {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                isConnected = false;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            ConnectAndGetData(currCoursesURL);
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            ParseCourse();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d("", "Process canceling");
        }
    }
    }
























