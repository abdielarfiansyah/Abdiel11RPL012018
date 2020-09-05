package com.abdiel.abdiel11rpl012018;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListData extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DataAdapter adapter;
    private ArrayList<Model> DataArrayList; //kit add kan ke adapter
    private ImageView tambah_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_list_data );
        recyclerView = (RecyclerView) findViewById(R.id.rvdata);
        addData();
    }

    void addData() {
        //offline, isi data offline dulu
        DataArrayList = new ArrayList<>();
        Model data1 = new Model();
        data1.setOriginal_title("Judul Film");
        data1.setPoster_path("https://image.tmdb.org/t/p/w500/k68nPLbIST6NP96JmTxmZijEvCA.jpg");
        data1.setAdult(false);
        data1.setOverview("Deskripsi Film disini");
        data1.setVote_count(100);
        data1.setRelease_date("01-01-2020");
        DataArrayList.add(data1);


        adapter = new DataAdapter(DataArrayList, new DataAdapter.Callback() {
            @Override
            public void onClick(int position) {

            }

            @Override
            public void test() {

            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListData.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adddataOnline();
        //get data online

    }
    void adddataOnline(){
        AndroidNetworking.get( "https://api.themoviedb.org/3/movie/now_playing?api_key=6ac7a042ac3b7599a689eb943fa0b6d0&language=en-US" )
                .setTag( "test" )
                .setPriority( Priority.LOW )
                .build()
                .getAsJSONObject( new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("hasiljson","onResponse: "+response.toString());
                        DataArrayList = new ArrayList<>();
                        Model modelku;
                        try {
                            Log.d("hasiljson","onResponse: "+response.toString());
                            JSONArray jsonArray = response.getJSONArray( "results" );
                            Log.d("hasiljson2","onResponse: "+jsonArray.toString());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                modelku = new Model();
                                JSONObject jsonObject = jsonArray.getJSONObject( i );
                                modelku.setOriginal_title( jsonObject.getString( "original_title" ) );
                                modelku.setOverview( jsonObject.getString( "overview" ) );
                                modelku.setRelease_date( jsonObject.getString( "release_date" ) );
                                modelku.setPoster_path( "https://image.tmdb.org/t/p/w500"+jsonObject.getString( "poster_path" ) );
                                modelku.setAdult( jsonObject.getBoolean( "adult" ) );
                                modelku.setVote_count( jsonObject.getInt( "vote_count" ) );
                                DataArrayList.add( modelku );

                            }
                            adapter = new DataAdapter( DataArrayList, new DataAdapter.Callback() {
                                @Override
                                public void onClick(int position) {

                                }

                                @Override
                                public void test() {

                                }
                            } );
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ListData.this);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d( "errorku","onError errorCode : "+ anError.getErrorCode() );
                        Log.d( "errorku","onError errorCode : "+ anError.getErrorBody () );
                        Log.d( "errorku","onError errorCode : "+ anError.getErrorDetail() );

                    }
                } );
    }
}