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


public class dialog_delete extends AppCompatDialogFragment {
    FirebaseAuth fAuth;

    private EditText Email;
    private EditText Password;

    private dialog_deleteListener listener;



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_delete, null);
        fAuth = FirebaseAuth.getInstance();



        builder.setView(view)
                .setTitle("Confirmation")
                .setMessage("Merci de vous reconnecter avant de changer votre mot de passe")
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


                        fAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    System.out.println("authentification réussi");


                                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                    user.delete()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        System.out.println("utilisateur supprimé");
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

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (dialog_deleteListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement ExampleDialogListener");
        }
    }

    public interface dialog_deleteListener {
        void applyTexts(String username, String password);
    }
}




