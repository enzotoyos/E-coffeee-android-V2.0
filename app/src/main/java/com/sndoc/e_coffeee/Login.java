//////////////////////////////////////////////////////////////////////
//                                                                  //
//          Programme pour l'activity Login.JAVA                    //
//              + connexion à Firebase et Log                       //
//                     d'un utilisateur                             //
//                                                                  //
//                     Auteur: Enzo Toyos                           //
//                                                                  //
//                         V 1.0.0                                  //
//                                                                  //
//                     Projet Machine à café                        //
//                                                                  //
//////////////////////////////////////////////////////////////////////



package com.sndoc.e_coffeee;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private String email = "";
    EditText mEmail,mPassword;
    TextView forgetPassword;
    Button mLoginBtn,mSignupBtn;
    FirebaseAuth fAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.loginBtn);
        mSignupBtn = findViewById(R.id.signup);
        forgetPassword = findViewById(R.id.forgotPassword);

        fAuth = FirebaseAuth.getInstance();


        if(fAuth.getCurrentUser() != null){  //Si l'utilisateur est déjà connecté on le renvoie sur l'ecran d'accueil
            boolean emailVerified = user.isEmailVerified();
            if(emailVerified == true){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }else{
                System.out.println("l'utilisateur n'a pas confirmer son enregistrement ");
            }
        }

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("bouton forget password appuye");
                afficherPopup();

            }
        });

        
        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){ // vérifie que le champ email est remplie
                    mEmail.setError("email vide");
                    return;
                }
                if (TextUtils.isEmpty(password)){  // vérifie que le champ password est remplie
                    mPassword.setError("mot de passe vide ");
                    return;
                }
                if(password.length() < 6) {  // vérifie que le password contient plus de 6 caracteres
                    mPassword.setError("le mot de passe doit contenir au minimum 6 caracteres");

                    if(fAuth.getCurrentUser() != null){
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                       finish();
                    }
               }

                // Authentification du user

                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        boolean emailVerified = user.isEmailVerified();
                        if(task.isSuccessful()) {
                            if(emailVerified == true){
                                Toast.makeText(Login.this, "Bienvenue", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        }else {
                            Toast.makeText(Login.this, "Mot de passe ou E-mail incorrect"+ task.getException() .getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        });
            // si le bouton "inscription" est appuyé go sur activity Register
        mSignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });



    }

    void afficherPopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Merci de renseigner votre adresse mail");


        final EditText input = new EditText(this);

        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                email = input.getText().toString();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    System.out.println("email de réinitailisation envoyé");
                                }
                            }
                        });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}