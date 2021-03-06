package com.ais;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import com.ais.models.Pitanje;
import com.example.kviz1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;


import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button uvod,nivo1,nivo2,nivo3;
    List<Pitanje> pitanja;
    List<Pitanje> filtriranaPitanja;
    TextView rezultat;
    //initialize Firebase reference
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("pitanja");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //create instance
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if put extrea za broj poena exist ondak
        rezultat = (TextView)findViewById(R.id.rezultat);
        Intent mIntent = getIntent();
        if(mIntent.hasExtra("poeni")){
        rezultat.setText("Vas rezultat je "+  mIntent.getIntExtra("poeni",0));
        }
        //init dugmad
        uvod = (Button) findViewById(R.id.uvod);
        nivo1 = (Button) findViewById(R.id.nivo1);
        nivo2 = (Button) findViewById(R.id.nivo2);
        nivo3 = (Button) findViewById(R.id.nivo3);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //init podaci
        pitanja = new ArrayList<Pitanje>();
        filtriranaPitanja = new ArrayList<Pitanje>();


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {

                    List<String> localPitanja = new ArrayList<>();
                    for (DataSnapshot odgovor : chidSnap.child("odgovori").getChildren()) {
                        localPitanja.add(odgovor.getValue().toString());
                    }
                    pitanja.add(new Pitanje(
                            chidSnap.child("broj").getValue().toString(),
                            chidSnap.child("odgovor").getValue().toString(),
                            chidSnap.child("pitanje").getValue().toString(),
                            localPitanja,
                            chidSnap.child("tezina").getValue().toString()
                    ));
                    localPitanja.clear();
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("error", error.toString());
            }
        });

        uvod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String pitanja_json = gson.toJson(pitanja);
                startActivity(new Intent(MainActivity.this, Kviz.class).putExtra("pitanja_json",pitanja_json));
            }
        });

        nivo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                for(int i=0;i<pitanja.size();i++){
                    if(pitanja.get(i).getTezina().equals("1")){
                        filtriranaPitanja.add(pitanja.get(i));
                    }
                }
                String pitanja_json = gson.toJson(filtriranaPitanja);
                startActivity(new Intent(MainActivity.this, Kviz.class).putExtra("pitanja_json",pitanja_json));
            }
        });

        nivo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                for(int i=0;i<pitanja.size();i++){
                    if(pitanja.get(i).getTezina().equals("2")){
                        filtriranaPitanja.add(pitanja.get(i));
                    }
                }
                String pitanja_json = gson.toJson(filtriranaPitanja);
                startActivity(new Intent(MainActivity.this, Kviz.class).putExtra("pitanja_json",pitanja_json));
            }
        });

        nivo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                for(int i=0;i<pitanja.size();i++){
                    if(pitanja.get(i).getTezina().equals("3")){
                        filtriranaPitanja.add(pitanja.get(i));
                    }
                }
                String pitanja_json = gson.toJson(filtriranaPitanja);
                startActivity(new Intent(MainActivity.this, Kviz.class).putExtra("pitanja_json",pitanja_json));
            }
        });



    }
}






