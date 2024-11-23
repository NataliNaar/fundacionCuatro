package com.example.fundacioncuatro;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuActivity extends AppCompatActivity {
    Button btnChildren, btnAdult , btnDonate, btnVoluntary, btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnChildren = findViewById(R.id.btnRegistrationChildren);
        btnAdult = findViewById(R.id.btnRegistrationAdult);
        btnDonate = findViewById(R.id.btnRegistrationDonate);
        btnVoluntary = findViewById(R.id.btnRegistrationVoluntary);
        btnBack = findViewById(R.id.btnBack);


        //Botón Registro de niños
        btnChildren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, RegChildrenActivity.class);
            }
        });

        //Botón Registro de acudientes
        btnAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, RegAdultActivity.class);
                startActivity(intent);
            }
        });

        //Botón Registro de Donates
        btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, RegEventActivity.class);
                startActivity(intent);
            }
        });

        // Acción para el botón "Regresar"
        //Botón "Regresar" ( btnBack): Cuando se hace clic en este botón, se llama al método onBackPressed(), que retrocede a la actividad anterior. Esto simula la acción de presionar el botón físico "Atrás" en Android.
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}