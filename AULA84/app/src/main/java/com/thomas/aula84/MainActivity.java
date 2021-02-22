package com.thomas.aula84;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retirar a barra de ações
        if (getActionBar() != null) {
            getActionBar().hide();
        }
    }
}