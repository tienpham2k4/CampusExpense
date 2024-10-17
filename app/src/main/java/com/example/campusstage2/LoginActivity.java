package com.example.campusstage2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campusstage2.model.Users;

public class LoginActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = findViewById(R.id.username_input);
        passwordInput = findViewById(R.id.password_input);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users users = new Users(v.getContext());
                SQLiteDatabase db = users.dbHelper.getReadableDatabase();

                String inputUsername = usernameInput.getText().toString();
                String inputPassword = HashUtil.hashPassword(passwordInput.getText().toString());

                Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?",
                        new String[]{inputUsername, inputPassword});

                if (cursor.moveToFirst()) {
                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    v.getContext().startActivity(intent);
                } else {
                    Toast.makeText(v.getContext(), "Invalid username/password", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
        TextView registerLink = findViewById(R.id.register_link);
        registerLink.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                v.getContext().startActivity(intent);
            }
        });

    }
}
