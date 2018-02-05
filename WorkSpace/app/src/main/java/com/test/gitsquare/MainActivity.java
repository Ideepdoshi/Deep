package com.test.gitsquare;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.test.gitsquare.adapter.RecyclerViewAdapter;
import com.test.gitsquare.pojo.Data;
import com.test.gitsquare.util.Constants;
import com.test.gitsquare.util.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    Data data = null;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    RecyclerViewAdapter recyclerViewAdapter;
    Button filterbtn;
    List<Data> dataArrayList = new ArrayList<>();
    SwipeRefreshLayout swipe_container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getText(R.string.activity_title));
        initview();
        filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PreferenceHelper.getBoolean(Constants.IS_FILTERED, false)) {
                    Collections.reverse(dataArrayList);
                    recyclerViewAdapter.notifyDataSetChanged();
                    PreferenceHelper.remove(Constants.IS_FILTERED);
                } else {

                    Collections.sort(dataArrayList);
                    recyclerViewAdapter.notifyDataSetChanged();
                    PreferenceHelper.putBoolean(Constants.IS_FILTERED, true);
                }
            }
        });

    }


    public void initview() {
        swipe_container = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerViewLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        filterbtn = (Button) findViewById(R.id.filter);
        swipe_container.setOnRefreshListener(this);
        swipe_container.post(new Runnable() {
                                 @Override
                                 public void run() {
                                     swipe_container.setRefreshing(true);

                                     callapi();
                                 }
                             }
        );

    }

    public void callapi() {


        if (isInternetAvailable(MainActivity.this)) {

            new GetMethodDemo().execute("https://api.github.com/repos/square/retrofit/contributors");

        } else {
            swipe_container.setRefreshing(false);
            AlertDialog.Builder alertbox =
                    new AlertDialog.Builder(MainActivity.this);
            String msg = "Check your Internet Connectivity";
            alertbox.setMessage(msg);
            alertbox.show();
        }
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRefresh() {
        callapi();
    }

    public class GetMethodDemo extends AsyncTask<String, Void, String> {
        String server_response;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipe_container.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... strings) {

            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int responseCode = urlConnection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    server_response = readStream(urlConnection.getInputStream());

                    Log.v("CatalogClient", server_response);
                } else {

                    server_response = "error";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Response", "" + server_response);

            // Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            try {

                if (server_response.equals("error")) {
                    swipe_container.setRefreshing(false);
                    Toast.makeText(getApplicationContext(),
                            "Something went wrong, Try it after some time",
                            Toast.LENGTH_LONG).show();
                } else {


                    if (dataArrayList.size() > 0) {

                        dataArrayList.clear();
                    }

                    JSONArray jsonArray = new JSONArray(server_response);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        data = new Data();
                        data.setAvatar_url(jsonObject.getString("avatar_url"));
                        data.setRepos_url(jsonObject.getString("repos_url"));
                        data.setLogin(jsonObject.getString("login"));
                        data.setContributions(jsonObject.getInt("contributions"));
                        dataArrayList.add(data);
                    }

                    recyclerViewAdapter = new RecyclerViewAdapter(dataArrayList, MainActivity.this);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerViewAdapter.notifyDataSetChanged();
                    swipe_container.setRefreshing(false);
                    filterbtn.setVisibility(View.VISIBLE);
                }


            } catch (Throwable t) {
                Log.e("My App", "Could not parse malformed JSON: \"" + s + "\"");
            }

        }
    }

// Converting InputStream to String

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}
