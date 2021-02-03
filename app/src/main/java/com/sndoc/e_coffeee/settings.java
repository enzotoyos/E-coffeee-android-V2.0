package com.sndoc.e_coffeee;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class settings extends AppCompatActivity {

    public String etatmachine;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference etatMachine = database.getReference("state_machine/etat_machine");
    TextView mEtatMachine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mEtatMachine = findViewById(R.id.etatMachine);

        ImageView settings2 = (ImageView) findViewById(R.id.settings2);
        settings2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), settings2.class));
            }

        });
        ImageView user = (ImageView) findViewById(R.id.user);
        user.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), user.class));
            }

        });
        Button deconnexion = (Button) findViewById(R.id.deconnexion);
        deconnexion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
            }

        });

        etatMachine.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                etatmachine = dataSnapshot.getValue(String.class);
                System.out.println(etatmachine);
                mEtatMachine.setText(etatmachine);

            }
            public void onCancelled(DatabaseError error) {  //erreur sur la lecture de la valeur
                System.out.println("Failed to read value." + error.toException());
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