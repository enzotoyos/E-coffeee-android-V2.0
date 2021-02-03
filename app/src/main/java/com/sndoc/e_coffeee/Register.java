
package com.sndoc.e_coffeee;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    EditText mFullName,mEmail,mPassword,mCompany;
    Button mRegisterBtn;
    ImageButton mReturnBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public String uid;
    public String email_textField;
    public String username_textfield;
    public String company_textfield;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName = findViewById(R.id.fullName);
        mCompany  =  findViewById(R.id.company);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mRegisterBtn = findViewById(R.id.registerBtn);
        mReturnBtn = findViewById(R.id.returnBtn);

        fAuth = FirebaseAuth.getInstance();


        mRegisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                email_textField = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                username_textfield = mFullName.getText().toString().trim();
                company_textfield = mCompany.getText().toString().trim();


                if (TextUtils.isEmpty(email_textField)){ // vérifie que le champ email est remplie
                    mEmail.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){  // vérifie que le champ password est remplie
                    mPassword.setError("password is required");
                    return;
                }
                if(password.length() < 6) {  // vérifie que le password contient plus de 6 caracteres
                    mPassword.setError("password must be 6 characters");
                }
                //Enregistrement du client aupres de firebase
                fAuth.createUserWithEmailAndPassword(email_textField,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){ //enregistrement ok
                            Toast.makeText(Register.this, "user created", Toast.LENGTH_SHORT).show();
                            getIDuser();
                            System.out.println("l'uid unique est: " + uid);
                            System.out.println("la societe est: " + company_textfield);
                            System.out.println("l'email est: " + email_textField);
                            System.out.println("l'username est: " + username_textfield);
                            envoyerMail();
                            enregistrementFirestore();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));

                        }else{ // enregistrement échoué
                            Toast.makeText(Register.this, "error"+ task.getException() .getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        mReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });



    }
    public void getIDuser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            uid = user.getUid();
        }
    }

    public void enregistrementFirestore(){
        Map<String, Object> docData = new HashMap<>();
        docData.put("company", company_textfield);
        docData.put("email", email_textField);
        docData.put("nb_cafe", 0);
        docData.put("username",username_textfield);


        db.collection("users").document(uid)
                .set(docData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        System.out.println("le document à bien été écrit ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Error writing document");
                    }
                });
    }

    public void envoyerMail(){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    System.out.println("email envoye");
                }
            }
        });
    }
}