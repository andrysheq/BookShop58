package com.example.mystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mystore.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SignRegActivity extends AppCompatActivity {

    ImageButton signIn, register, mainMenu;
    FirebaseAuth auth;
    static FirebaseDatabase database;
    static DatabaseReference users;
    MaterialEditText email, password;
    RelativeLayout root;
    ConstraintLayout home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_reg);

        //getSupportActionBar().hide();

        signIn = findViewById(R.id.btnSignIn);
        register = findViewById(R.id.btnRegister);
        mainMenu = findViewById(R.id.btn_back);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");
        root = findViewById(R.id.signRegLayout);
        home = findViewById(R.id.fragment_home);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterWindow();
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSignInWindow();
            }
        });

        mainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMainMenu();
            }
        });

    }

    private void openMainMenu() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showSignInWindow() {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        dialog.setTitle("Войти");
//        dialog.setMessage("Введите ваши данные");
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View sign_in_window = inflater.inflate(R.layout.sign_in_window,null);
//        dialog.setView(sign_in_window);

        MaterialEditText email = findViewById(R.id.emailField);
        MaterialEditText password = findViewById(R.id.passwordField);

//        dialog.setNegativeButton(R.string.negativeButtonText, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });

        if(TextUtils.isEmpty(email.getText().toString())){
            Snackbar.make(root,R.string.email_error,Snackbar.LENGTH_LONG).show();
            return;
        }

        if(password.getText().toString().length()<5){
            Snackbar.make(root,R.string.password_error,Snackbar.LENGTH_LONG).show();
            return;
        }

        auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                openMainMenu();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(root,R.string.auth_error,Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void showRegisterWindow() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Зарегистрироваться");
        dialog.setMessage("Введите ваши данные");
        LayoutInflater inflater = LayoutInflater.from(this);
        View register_window = inflater.inflate(R.layout.register_window,null);
        dialog.setView(register_window);

        MaterialEditText email = register_window.findViewById(R.id.emailField);
        MaterialEditText password = register_window.findViewById(R.id.passwordField);
        MaterialEditText login = register_window.findViewById(R.id.loginField);
        MaterialEditText phoneNumber = register_window.findViewById(R.id.phoneNumberField);

        dialog.setNegativeButton(R.string.negativeButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton(R.string.positiveButtonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(TextUtils.isEmpty(email.getText().toString())){
                    Snackbar.make(root,R.string.email_error,Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(password.getText().toString().length()<5){
                    Snackbar.make(root,R.string.password_error,Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(login.getText().toString())){
                    Snackbar.make(root,R.string.login_error,Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(phoneNumber.getText().toString())){
                    Snackbar.make(root,R.string.phone_number_error,Snackbar.LENGTH_LONG).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User(login.getText().toString(),phoneNumber.getText().toString(),password.getText().toString(),email.getText().toString());
                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Snackbar.make(root, R.string.positive_user_add,Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root,e.getMessage(),Snackbar.LENGTH_LONG).show();
                    }
                });

            }
        });

        dialog.show();
    }
}