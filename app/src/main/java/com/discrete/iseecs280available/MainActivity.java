package com.discrete.iseecs280available;

import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.*;

public class MainActivity extends AppCompatActivity {

    String sectionType = "LEC";
    String termCode = "2160";
    String schoolCode = "ENG";
    String subjectCode = "";
    String catalogNum = "";

    Button submitButton;
    EditText editDepartment, editCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            submitButton = (Button)findViewById(R.id.submitCourseInfo);
            editDepartment   = (EditText)findViewById(R.id.enterDepartment);
            editCourse = (EditText)findViewById(R.id.enterCourseNumber);

            submitButton.setOnClickListener(
                    new View.OnClickListener()
                    {
                        public void onClick(View view)
                        {
                            subjectCode = editDepartment.getText().toString();
                            catalogNum = editCourse.getText().toString();

                            System.out.println(subjectCode);
                            System.out.println(catalogNum);

                            if (!subjectCode.isEmpty() && !catalogNum.isEmpty()) {
                                TextView header = (TextView) findViewById(R.id.courseDisplayHeader);
                                TextView info = (TextView) findViewById(R.id.courseInfo);
                                try {
                                    String openSections = findOpenCourses();

                                    String headerMessage = subjectCode + " " + catalogNum;
                                    header.setText(headerMessage);

                                    info.setText(openSections);
                                    info.setMovementMethod(new ScrollingMovementMethod());
                                }
                                catch (Exception e) {
                                    e.printStackTrace();
                                    // clear header
                                    String headerMessage = "";
                                    header.setText(headerMessage);

                                    String errorMessage = "Sorry, the course you specified could not be found.";
                                    info.setText(errorMessage);
                                }
                            }
                        }
                    }
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String findOpenCourses() throws Exception {

        String openClasses = "";

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        String url = "http://umich-schedule-api.herokuapp.com/v4/get_sections?" +
                "term_code=" + termCode +
                "&school=" + schoolCode +
                "&subject=" + subjectCode.toUpperCase() +
                "&catalog_num=" + catalogNum;

        JSONArray infoFromAPI = getJSONArray(url);

        // go through course info array and find open sections
        for (int i = 0; i < infoFromAPI.length(); i++) {
            JSONObject infoObject = infoFromAPI.getJSONObject(i);

            String append = "Section " + infoObject.getString("SectionNumber") + ": " +
                    String.valueOf(infoObject.getInt("AvailableSeats")) + "\n";
            openClasses += append;

                /*
                // check that type (LEC/LAB/DIS) matches
                if (!infoObject.getString("SectionType").isEmpty()) {
                    // check if it is open
                    if (infoObject.getInt("AvailableSeats") >= 0) {
                        // add the course info to openClasses string
                        String append = "Section " + infoObject.getString("SectionNumber") + ": " +
                                String.valueOf(infoObject.getInt("AvailableSeats")) + "\n";
                        openClasses += append;
                        System.out.println(openClasses);
                    }
                }
                */
        }
        // if no open classes are found...
        if (openClasses.length() == 0) {
            throw new Exception();
        }

        return openClasses;
    }

    @NonNull
    public static JSONArray getJSONArray(String url) throws IOException, JSONException {

        // Build and set timeout values for the request.
        System.out.println("HERE");
        URLConnection connection = (new URL(url)).openConnection();
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        connection.connect();

        // Read and store the result line by line then return the entire string.
        InputStream in = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        StringBuilder html = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            html.append(line.trim());
        }
        in.close();

        return new JSONArray(html.toString());
    }
}
