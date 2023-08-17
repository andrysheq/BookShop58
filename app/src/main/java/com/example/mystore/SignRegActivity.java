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
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    TextView forgotPassword;
    static FirebaseDatabase database;
    static DatabaseReference users;
    String email;
    MaterialEditText password;
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
        forgotPassword = findViewById(R.id.tvForgotPassword);
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

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showForgotPasswordDialog();
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

    private void showForgotPasswordDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Восстановить пароль");
        dialog.setMessage("Мы отправим ссылку для восстановления пароля на ваш email");
        LayoutInflater inflater = LayoutInflater.from(this);
        View reset_password_dialog = inflater.inflate(R.layout.reset_password_dialog,null);
        dialog.setView(reset_password_dialog);

        MaterialEditText edEmail = reset_password_dialog.findViewById(R.id.edResetPassword);
//        Button send = reset_password_dialog.findViewById(R.id.buttonConfirmResetPassword);
//        Button cancel = reset_password_dialog.findViewById(R.id.buttonCancelResetPassword);

        dialog.setNegativeButton(R.string.previous, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                email = edEmail.getText().toString();
                if(TextUtils.isEmpty(email)){
                    Snackbar.make(root,R.string.email_error,Snackbar.LENGTH_LONG).show();
                }else{
                    resetPassword();
                }
            }
        });

        dialog.show();
    }

    private void resetPassword(){
        auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Snackbar.make(root,R.string.password_reset_successful,Snackbar.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(root,R.string.auth_error,Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void openMainMenu() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void showSignInWindow() {
        MaterialEditText email = findViewById(R.id.emailField);
        MaterialEditText password = findViewById(R.id.passwordField);

        if(TextUtils.isEmpty(email.getText().toString())){
            hideKeyboard();
            email.setError(getString(R.string.email_error));
            //Snackbar.make(root,R.string.email_error,Snackbar.LENGTH_LONG).show();
            return;
        }

        if(password.getText().toString().length()<5){
            hideKeyboard();
            password.setError(getString(R.string.password_error));
            //Snackbar.make(root,R.string.password_error,Snackbar.LENGTH_LONG).show();
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
                email.clearFocus();
                password.clearFocus();
                email.setText(null);
                password.setText(null);
                hideKeyboard();
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

        MaterialEditText edEmail = register_window.findViewById(R.id.emailField);
        MaterialEditText password = register_window.findViewById(R.id.passwordField);
        MaterialEditText login = register_window.findViewById(R.id.loginField);
        MaterialEditText phoneNumber = register_window.findViewById(R.id.phoneNumberField);

        dialog.setNegativeButton(R.string.previous, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialog.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //boolean isErr = false;
                if (TextUtils.isEmpty(edEmail.getText().toString())) {
                    //hideKeyboard();
                    Snackbar.make(root,R.string.email_error,Snackbar.LENGTH_LONG).show();
                    return;
                    //edEmail.setError("wtfffff");
                    //isErr = true;
                }
                if (password.getText().toString().length() < 5) {
                    //hideKeyboard();
                    Snackbar.make(root,R.string.password_error,Snackbar.LENGTH_LONG).show();
                    return;
                    //password.setError("wtfffff");
                    //isErr = true;
                }
                if (TextUtils.isEmpty(login.getText().toString())) {
//                    hideKeyboard();
                    Snackbar.make(root,R.string.login_error,Snackbar.LENGTH_LONG).show();
                    return;
                    //login.setError("wtfffff");
                    //isErr = true;
                }
                if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
                    //hideKeyboard();
                    Snackbar.make(root,R.string.phone_number_error,Snackbar.LENGTH_LONG).show();
                    return;
                    //phoneNumber.setError("wtfffff");
                    //isErr = true;
                }
                auth.createUserWithEmailAndPassword(edEmail.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        User user = new User(login.getText().toString(), phoneNumber.getText().toString(), edEmail.getText().toString());
                        users.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Snackbar.make(root, R.string.user_added, Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Snackbar.make(root, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });
        dialog.show();
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
}