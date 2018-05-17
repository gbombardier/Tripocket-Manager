package com.gbombardier.tripocketmanager.fragments;

/**
 * Created by gabb_ on 2018-03-14.
 * Inspired by: http://www.androidbegin.com/tutorial/android-dialogfragment-tutorial/
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gbombardier.tripocketmanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class PasswordInputFragment extends DialogFragment {
    public static String email;
    private EditText pwdZoneAncient, pwdZoneNew, pwdZoneNew2;
    private Button validateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.layout_alert_password, container, false);

        getDialog().setTitle("Modifier mot de passe");

        pwdZoneAncient = (EditText) rootView.findViewById(R.id.alert_password_entry1);
        pwdZoneNew = (EditText) rootView.findViewById(R.id.alert_password_entry2);
        pwdZoneNew2 = (EditText) rootView.findViewById(R.id.alert_password_entry3);
        validateButton = (Button) rootView.findViewById(R.id.alert_password_validate);

        //Action lorsque l'on clique sur valider
        validateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                String oldPassword = "", newPassword = "";
                if (pwdZoneAncient.getText().length() == 0 || pwdZoneNew.getText().length() == 0 || pwdZoneNew2.getText().length() == 0) {

                } else {
                    oldPassword = pwdZoneAncient.getText().toString();
                    AuthCredential credential = EmailAuthProvider.getCredential(email, oldPassword);

                    if (pwdZoneNew.getText().toString().equals(pwdZoneNew2.getText().toString())) {
                        FirebaseAuth.getInstance().getCurrentUser().reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    FirebaseAuth.getInstance().getCurrentUser().updatePassword(pwdZoneNew2.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(rootView.getContext(), "Quelque chose ne fonctionne pas. Veuillez rééssayer plus tard", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(rootView.getContext(), "Le mot de passe a bien été modifié!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                } else {
                                    Toast.makeText(rootView.getContext(), "Le username et le mot de passe ne correspondent pas.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
                dismiss();
            }
        });

            return rootView;
    }
}


