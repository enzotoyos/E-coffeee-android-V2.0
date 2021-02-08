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
    private String password = "";
    EditText mEmail,mPassword;
    TextView forgetPassword;
    Button mLoginBtn,mSignupBtn;
    FirebaseAuth fAuth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    boolean emailVerified;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //attribution des composants graphiques
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.password);
        mLoginBtn = findViewById(R.id.loginBtn);
        mSignupBtn = findViewById(R.id.signup);
        forgetPassword = findViewById(R.id.forgotPassword);

        fAuth = FirebaseAuth.getInstance();

        //Si l'utilisateur est déjà connecté on le renvoie sur l'ecran d'accueil
        if(fAuth.getCurrentUser() != null){
            boolean emailVerified = user.isEmailVerified();
            // test si l'utilisateur a verifier son mail après l'inscription
            if(emailVerified == true){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }else{
                System.out.println("l'utilisateur n'a pas confirmer son enregistrement ");
            }
        }

        // si l'utilisateur perd son mot de passe
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("bouton forget password appuye");
                afficherPopupResetPassword();

            }
        });

        
        mLoginBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                email = mEmail.getText().toString().trim();
                password = mPassword.getText().toString().trim();

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

               }

                // Authentification du user avec email est password
                loginUser();

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

    // fonction affiche la pop-up pour la réinitalisation du MDP
    void afficherPopupResetPassword(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Merci de renseigner votre adresse mail");

        final EditText input = new EditText(this);

        builder.setView(input);

        //instruction une fois le bouton ok appuyé
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                email = input.getText().toString();

                //Obligatoire car si la méthode reçoit un paramètre vide l'appli crash
                if(email.isEmpty()){
                    input.setError("le champ est vide");
                }else {
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    String emailAddress = email;

                    //utilisation de la methode de L'API Firebase
                    auth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        System.out.println("Email sent.");
                                    }
                                }
                            });
                }
            }
            //instruction si le bouton cancel appuyé
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }// END: fonction afficherPopupReset


    public void loginUser(){
        fAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        recupererInfosUser();
                        if (task.isSuccessful()) {
                            if(emailVerified == true){
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                 System.out.println("Bienvenue");
                            }else{
                                System.out.println("vous n'avez pas vérifier votre email");
                            }
                        }else {
                            // If sign in fails, display a message to the user.
                            System.out.println("signInWithEmail:failure"+ task.getException());
                            Toast.makeText(Login.this, "Authentication échoué.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void recupererInfosUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
             emailVerified = user.isEmailVerified();
        }

    }



}// END: class login