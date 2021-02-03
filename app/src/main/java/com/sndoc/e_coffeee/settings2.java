package com.sndoc.e_coffeee;

import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class settings2 extends AppCompatActivity {

    public Integer MarcQuantity;
    public Integer Water_Level;
    public Integer Water_Quality;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference marcQuantity = database.getReference("state_machine/marc_quantity");
    DatabaseReference waterQuantity = database.getReference("state_machine/water_level");
    DatabaseReference waterQuality = database.getReference("state_machine/water_quality");
    TextView marc_Quantity,water_Quantity,water_Quality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);

        marc_Quantity = findViewById(R.id.quantityMarc);
        water_Quantity = findViewById(R.id.water_level);
        water_Quality = findViewById(R.id.water_quality);

        marcQuantity.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                MarcQuantity = dataSnapshot.getValue(Integer.class);
                System.out.println(MarcQuantity);
                String marcQuantityFinal = MarcQuantity.toString();
                marc_Quantity.setText(marcQuantityFinal);

            }
            public void onCancelled(DatabaseError error) {  //erreur sur la lecture de la valeur
                System.out.println("Failed to read value." + error.toException());
            }
        });
        waterQuantity.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Water_Level = dataSnapshot.getValue(Integer.class);
                System.out.println(Water_Level);
                String waterQuantityFinal = Water_Level.toString();
                water_Quantity.setText(waterQuantityFinal);
            }
            public void onCancelled(DatabaseError error) {  //erreur sur la lecture de la valeur
                System.out.println("Failed to read value." + error.toException());
            }
        });
        waterQuality.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                Water_Quality = dataSnapshot.getValue(Integer.class);
                System.out.println(Water_Quality);
                String waterQualityFinal = Water_Quality.toString();
                water_Quality.setText(waterQualityFinal);
            }
            public void onCancelled(DatabaseError error) {  //erreur sur la lecture de la valeur
                System.out.println("Failed to read value." + error.toException());
            }
        });
    }
}