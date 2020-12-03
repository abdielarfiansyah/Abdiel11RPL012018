package com.abdiel.abdiel11rpl012018;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainMenu extends AppCompatActivity {
    CardView menu1;
    CardView menu2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_mainmenu );
        menu1 = (CardView)findViewById(R.id.menu1);
        menu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListData.class));
            }
        });
        menu2 = (CardView)findViewById( R.id.menu2 );
        menu2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( getApplicationContext(),ListDataFavourite.class ) );
            }
        } );
    }

    public static class RealmHelper {
        Realm realm;

        public  RealmHelper(Realm realm){
            this.realm = realm;
        }
        // untuk menyimpan data
        public void save(final ModelMovieRealm movieModel){
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (realm != null){
                        Log.e("Created", "Database was created");
                        Number currentIdNum = realm.where(ModelMovieRealm.class).max("idTeam");
                        int nextId;
                        if (currentIdNum == null){
                            nextId = 1;
                        }else {
                            nextId = currentIdNum.intValue() + 1;
                        }
                        movieModel.setIdTeam(nextId);
                        ModelMovieRealm model = realm.copyToRealm(movieModel);
                    }else{
                        Log.e("ppppp", "execute: Database not Exist");
                    }
                }
            });
        }
        // untuk memanggil semua data
        public List<ModelMovieRealm> getAllSoccer(){
            RealmResults<ModelMovieRealm> results = realm.where(ModelMovieRealm.class).findAll();
            return results;
        }

        public void delete(Integer id){
            final RealmResults<ModelMovieRealm> model = realm.where(ModelMovieRealm.class).equalTo("idTeam", id).findAll();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    model.deleteFromRealm(0);
                }
            });
        }

    }
}