package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText user_Input, user_Longitude, user_Latitude, user_Cnt, user_Zip;
    private TextView userOutput;
    DecimalFormat decimalFormat = new DecimalFormat("#.#");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user_Input = findViewById(R.id.userInput);
        Button go = findViewById(R.id.GO);
        user_Longitude = findViewById(R.id.inputLongitude);
        user_Latitude = findViewById(R.id.inputLatitude);
        user_Cnt = findViewById(R.id.inputCnt);
        user_Zip = findViewById(R.id.inputZipCode);
        userOutput = findViewById(R.id.weatherOutput);

        go.setOnClickListener(v -> getWeatherDetails());
    }

    private void getWeatherDetails() {
        String tempUrl = "";
        String _userInput = user_Input.getText().toString().trim();
        String _longitude = user_Longitude.getText().toString().trim();
        String _latitude = user_Latitude.getText().toString().trim();
        String _userCnt = user_Cnt.getText().toString().trim();
        String _userZip = user_Zip.getText().toString().trim();

        if(_userInput.equals("") && _longitude.equals("") && _latitude.equals("") && _userCnt.equals("") && _userZip.equals("")){
            user_Input.setError("Enter any of the field");
            user_Longitude.setError("Enter any of the field");
            user_Latitude.setError("Enter any of the field");
            user_Cnt.setError("Enter any of the field");
            user_Zip.setError("Enter any of the field");
        }
        else{
            String url = "http://api.openweathermap.org/data/2.5/weather";
            String app_id = "347c7fdd5a332826faf32abf7df7d058";
            if((!_userInput.equals("")) &&_latitude.equals("") && _longitude.equals("") && _userCnt.equals("") && _userZip.equals("")) {

                tempUrl = url + "?q=" + _userInput + "&appid=" + app_id;
            }
            else if ((!(_longitude.equals("") && _latitude.equals(""))) && _userZip.equals("") && _userCnt.equals("") && _userInput.equals("")){

                tempUrl = url + "?lat=" + _latitude + "&lon=" + _longitude + "&appid=" + app_id;
            }
            else if((!_userZip.equals("")) && _latitude.equals("") && _longitude.equals("") && _userCnt.equals("") && _userInput.equals("")){

                tempUrl = url + "?zip=" + _userZip + "&appid=" + app_id;
            }
            else if((!(_latitude.equals("") && _longitude.equals("") && _userCnt.equals(""))) && _userZip.equals("") && _userInput.equals("")){

                tempUrl = url + "?lat=" + _latitude + "&lon="+ _longitude + "&cnt=" +_userCnt + "&appid=" + app_id;
            }
            else{
                user_Input.setError("Enter valid fields");
                user_Longitude.setError("Enter valid fields");
                user_Latitude.setError("Enter valid fields");
                user_Cnt.setError("Enter valid fields");
                user_Zip.setError("Enter valid fields");
            }

            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, response -> {
                String _output = "";
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                    String _description = jsonObjectWeather.getString("description");
                    JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                    double temp = jsonObjectMain.getDouble("temp") ;
                    double feels_like = jsonObjectMain.getDouble("feels_like") ;
                    double temp_min = jsonObjectMain.getDouble("temp_min") ;
                    double temp_max = jsonObjectMain.getDouble("temp_max") ;
                    double pressure = jsonObjectMain.getDouble("pressure");
                    double humidity = jsonObjectMain.getDouble("humidity");
                    String visibility = jsonObject.getString("visibility");
                    JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                    String  speed = jsonObjectWind.getString("speed");
                    String deg = jsonObjectWind.getString("deg");
                    JSONObject jsonObjectCloud = jsonObject.getJSONObject("clouds");
                    String all = jsonObjectCloud.getString("all");
                    _output +="\nDescription = " + _description
                            + "\nTemprature = " + decimalFormat.format(temp) + "°F"
                            +"\nFeels Like = " + decimalFormat.format(feels_like) + "°F"
                            + "\nHumidity = " + decimalFormat.format(humidity)
                            +"\nSpeed = " + speed
                            +"\nDeg = " + deg
                            +"\nMin Temp = " + decimalFormat.format(temp_min) + "°F"
                            + "\nMax Temp = " + decimalFormat.format(temp_max) + "°F"
                            +"\nVisibility = " + visibility
                            +"\nPressure = " + pressure
                            +"\nAll = " + all;
                    userOutput.setText(_output);
                }
                catch(JSONException e){
                    e.printStackTrace();
                }
            }, error -> Toast.makeText(MainActivity.this, error.toString().trim(), Toast.LENGTH_LONG).show());

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }
}