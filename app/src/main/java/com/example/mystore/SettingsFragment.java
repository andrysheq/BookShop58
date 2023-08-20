package com.example.mystore;

import static com.example.mystore.MainActivity.currentUser;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SettingsFragment extends Fragment {

    CardView aboutUsCard;
    CardView contactsCard;
    ImageButton buttonBack;
    NestedScrollView scrollView;
    ProgressBar progressBar;
    CardView changePassword;
    CardView changeLogin;
    FirebaseAuth auth;
    String password;
    TextView userLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        aboutUsCard = view.findViewById(R.id.about_us_card);
        contactsCard = view.findViewById(R.id.contacts_card);
        buttonBack = view.findViewById(R.id.btn_back_settings);
        scrollView = view.findViewById(R.id.settingsScrollView);
        progressBar = view.findViewById(R.id.settingsProgressBar);
        changePassword = view.findViewById(R.id.password_change_card);
        changeLogin = view.findViewById(R.id.login_change_card);
        auth = FirebaseAuth.getInstance();
        userLogin = view.findViewById(R.id.login_settings);

        scrollView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.bringToFront();

        userLogin.setText(currentUser.getLogin());

        progressBar.setVisibility(View.INVISIBLE);
        scrollView.setVisibility(View.VISIBLE);

        aboutUsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settings_fragment_to_about_us_fragment);
            }
        });

        contactsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_settings_fragment_to_contacts_fragment);
            }
        });

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).popBackStack();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangePasswordDialog();
            }
        });

        changeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeLoginDialog();
            }
        });
    }

    private void showChangePasswordDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage("Мы отправим ссылку для смены пароля на ваш email");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View reset_password_dialog = inflater.inflate(R.layout.reset_password_dialog,null);
        dialog.setView(reset_password_dialog);

        MaterialEditText edPassword = reset_password_dialog.findViewById(R.id.edResetPassword);

        dialog.setNegativeButton(R.string.previous, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                password = edPassword.getText().toString();
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getContext(),R.string.email_error,Toast.LENGTH_LONG).show();
                }else{
                    resetPassword();
                }
            }
        });

        dialog.show();
    }

    private void resetPassword(){
        auth.sendPasswordResetEmail(password).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(),R.string.password_reset_successful,Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(),R.string.auth_error,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showChangeLoginDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setMessage("Введите новый логин");
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View change_login_dialog = inflater.inflate(R.layout.change_login_dialog,null);
        dialog.setView(change_login_dialog);

        MaterialEditText edLogin = change_login_dialog.findViewById(R.id.edChangeLogin);

        dialog.setNegativeButton(R.string.previous, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String newLogin = edLogin.getText().toString();
                if(TextUtils.isEmpty(newLogin)){
                    Toast.makeText(getContext(),R.string.login_error,Toast.LENGTH_LONG).show();
                }else{
                    changeLogin(newLogin);
                }
            }
        });

        dialog.show();
    }

    private void changeLogin(String newLogin){
        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("login").setValue(newLogin);
        Navigation.findNavController(getView()).popBackStack();
    }
}