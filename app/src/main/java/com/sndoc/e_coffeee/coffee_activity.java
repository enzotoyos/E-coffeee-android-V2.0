package com.sndoc.e_coffeee;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class coffee_activity extends AppCompatActivity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee_activity);

        ImageView chiapas = (ImageView) findViewById(R.id.chiapas);
        chiapas.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), chiapas.class));
            }

        });
        ImageView montecillos = (ImageView) findViewById(R.id.montecillos);
        montecillos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), montecillos.class));
            }

        });
        ImageView chiapasPasco = (ImageView) findViewById(R.id.chiapasPasco);
        chiapasPasco.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), chiapasPasco.class));
            }

        });
        ImageView risaralda = (ImageView) findViewById(R.id.risaralda);
        risaralda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), risaralda.class));
            }

        });
    }










    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.coffee){
            startActivity(new Intent(this, coffee_activity.class));
        }
        else if(id==R.id.reglages){
            startActivity(new Intent(this, settings.class));
        }
        else if(id==R.id.machine){
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


}