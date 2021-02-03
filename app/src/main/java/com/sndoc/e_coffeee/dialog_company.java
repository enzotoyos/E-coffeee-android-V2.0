package com.sndoc.e_coffeee;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;


public class dialog_company extends AppCompatDialogFragment {
    FirebaseAuth fAuth;
    public String company_textField;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextCompany;
    private ExampleDialogListener listener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_company, null);
        fAuth = FirebaseAuth.getInstance();



        builder.setView(view)
                .setTitle("Confirmation")
                .setMessage("Merci de vous reconnecter avant de changer votre entreprise")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = editTextUsername.getText().toString();
                        String password = editTextPassword.getText().toString();
                        company_textField = editTextCompany.getText().toString();

                        fAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    System.out.println("authentification réussi");

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                    String userID = user.toString();
                                    System.out.println(userID);
                                    modifierCompanyFirestore();

                                } else {
                                    System.out.println("ERREUR LORS DE L'AUTHENTIICATION");
                                }
                            }
                        });

                        listener.applyTexts(username, password);
                    }
                });
        editTextUsername = (EditText) view.findViewById(R.id.Email);
        editTextPassword = (EditText) view.findViewById(R.id.Password);
        editTextCompany = (EditText) view.findViewById(R.id.Company);
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface ExampleDialogListener {
        void applyTexts(String username, String password);
    }

    public void modifierCompanyFirestore(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        Map<String, Object> company = new HashMap<>();
        company.put("company",company_textField);

        db.collection("users").document(uid)
                .set(company, SetOptions.merge());

    }





}