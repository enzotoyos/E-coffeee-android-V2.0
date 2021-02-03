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


public class dialog_email extends AppCompatDialogFragment {
    FirebaseAuth fAuth;
    public String newEmail;
    private EditText Email;
    private EditText Password;
    private EditText NewEmail;
    private dialog_emailListener listener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_email, null);
        fAuth = FirebaseAuth.getInstance();



        builder.setView(view)
                .setTitle("Confirmation")
                .setMessage("Merci de vous reconnecter avant de changer votre adresse mail")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username = Email.getText().toString();
                        String password = Password.getText().toString();
                        newEmail = NewEmail.getText().toString();

                        fAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    System.out.println("authentification réussi");

                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    user.updateEmail(newEmail)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        modifierEmailFirestore();
                                                        System.out.println("email changé avec succés");

                                                    }
                                                }
                                            });

                                } else {
                                    System.out.println("ERREUR LORS DE L'AUTHENTIICATION");
                                }
                            }
                        });

                        listener.applyTexts(username, password);
                    }
                });
        Email = (EditText) view.findViewById(R.id.Email);
        Password = (EditText) view.findViewById(R.id.Password);
        NewEmail = (EditText) view.findViewById(R.id.NewEmail);
        return builder.create();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (dialog_emailListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }
    public interface dialog_emailListener {
        void applyTexts(String username, String password);
    }
    public void modifierEmailFirestore(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        Map<String, Object> email = new HashMap<>();
        email.put("email",newEmail);

        db.collection("users").document(uid)
                .set(email, SetOptions.merge());

    }

}