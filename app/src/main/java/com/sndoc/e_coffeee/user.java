package com.sndoc.e_coffeee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;


public class user extends AppCompatActivity implements dialog_company.ExampleDialogListener , dialog_password.dialog_passwordListener, dialog_email.dialog_emailListener{

    Button modifycompany,modifyemail,deleteaccount,modifypassword;
    private String email;
    private String motPasse;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        modifycompany = findViewById(R.id.modifyCompany);
        modifyemail = findViewById(R.id.modifyEmail);
        modifypassword = findViewById(R.id.modifyPassword);
        deleteaccount = findViewById(R.id.deleteAccount);
        fAuth = FirebaseAuth.getInstance();

        modifycompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCompany();
            }
        });

        modifyemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("bouton modify email appuyé");
                openDialogEmail();
            }
        });
        modifypassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("bouton modify passwd appuyé");
                openDialogPassword();

            }
        });
        deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("bouton delete appuyé");
                openDeleter();
            }
        });

        if(fAuth.getCurrentUser() != null){
            System.out.println("utilisateur actuellement connecté");
        }else{
            startActivity(new Intent(getApplicationContext(), Login.class));
        }

    }

    public void openDialogCompany() {
        dialog_company exampleDialog = new dialog_company();
        exampleDialog.show(getSupportFragmentManager(), "example dialog");
    }
    @Override
    public void applyTexts(String username, String password) {
         email = username;
         motPasse= password;
    }
    public void openDialogPassword() {
        dialog_password changePassword = new dialog_password();
        changePassword.show(getSupportFragmentManager(), "pop up changement de mot de passe");
    }
    public void openDialogEmail() {
        dialog_email changeEmail = new dialog_email();
        changeEmail.show(getSupportFragmentManager(), "pop up changement d'adresse mail");
    }
    public void openDeleter(){
        dialog_delete deleteUser = new dialog_delete();
        deleteUser.show(getSupportFragmentManager(), "pop up delete user ouverte");
    }


}