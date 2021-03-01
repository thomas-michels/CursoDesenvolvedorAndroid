package com.thomas.aula103;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.thomas.aula103.R.string.loginInvalido;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ViewHolder viewHolder = new ViewHolder();
    private static final String LOGIN = "nathalia";
    private static final String PASSWORD = "senha123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getActionBar() != null) {
            getActionBar().hide();
        }

        this.viewHolder.mEditEmail = findViewById(R.id.editEmail);
        this.viewHolder.mEditPassword = findViewById(R.id.editPassword);
        this.viewHolder.mBtnLogin = findViewById(R.id.btnLogin);

        this.viewHolder.mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (verifyInformations()) {
                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), loginInvalido, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean verifyInformations (){
        String login = String.valueOf(this.viewHolder.mEditEmail.getText());
        String password = String.valueOf(this.viewHolder.mEditPassword.getText());
        if (login.equals(LOGIN) && password.equals(PASSWORD)) {
            return true;
        }
        return false;
    }

    private static class ViewHolder {
        EditText mEditEmail;
        EditText mEditPassword;
        Button mBtnLogin;
    }
}