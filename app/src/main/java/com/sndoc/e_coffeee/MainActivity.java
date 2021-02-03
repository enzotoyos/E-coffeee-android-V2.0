package com.sndoc.e_coffeee;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    public Integer niveauEau;
    public Integer etatTasse;
    public Integer etatFileAttente;

    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference etat = database.getReference("request_coffee/create_coffee/state");
    DatabaseReference type = database.getReference("request_coffee/create_coffee/type");
    DatabaseReference quantity = database.getReference("request_coffee/create_coffee/quantity");
    DatabaseReference tasse = database.getReference("state_machine/presence_tasse");

    private CheckBox espresso;
    private CheckBox cafeCorse;
    SeekBar seekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnChkEspresso();

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                niveauEau = progress;
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        etat.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                etatFileAttente = dataSnapshot.getValue(Integer.class);
                System.out.println("le champ etat est: "+etatFileAttente);
            }
            public void onCancelled(DatabaseError error) {  //erreur sur la lecture de la valeur
                System.out.println("Failed to read value." + error.toException());
            }
        });


        tasse.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                etatTasse = dataSnapshot.getValue(Integer.class);
                System.out.println("le champ presence tasse est: "+ tasse);
            }

            public void onCancelled(DatabaseError error) {
                System.out.println("Failed to read value." + error.toException());//erreur sur la lecture de la valeur
            }
        });


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(espresso.isChecked() && cafeCorse.isChecked()){
                    Toast.makeText(MainActivity.this, "Erreur veuillez choisir 1 seul café", Toast.LENGTH_SHORT).show();
                }else {

                    if ((etatTasse == 1) && (etatFileAttente == 0)) {
                        if (espresso.isChecked()) {
                            type.setValue("Espresso Classique");
                            quantity.setValue(niveauEau);
                        } else if (cafeCorse.isChecked()) {
                            type.setValue("cafe corse");
                            quantity.setValue(niveauEau);
                        } else {
                            Toast.makeText(MainActivity.this, "Erreur choisir un café", Toast.LENGTH_SHORT).show();
                        }
                        etat.setValue(1);
                        Toast.makeText(MainActivity.this, "café envoyé", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Désolé un café est déjà en création veuillez réessayer dans 1 minute", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }


    @Override //Création du menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override //test pour le menu
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.coffee) {
            startActivity(new Intent(this, coffee_activity.class));
        } else if (id == R.id.reglages) {
            startActivity(new Intent(this, settings.class));
        } else if (id == R.id.machine) {
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

        //affectation des widgets checkboxs
    public void addListenerOnChkEspresso() {
        espresso = (CheckBox) findViewById(R.id.checkBoxEspresso);
        cafeCorse = (CheckBox) findViewById(R.id.checkBoxCorse);
    }


}
