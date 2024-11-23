package com.example.fundacioncuatro;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegChildrenActivity extends AppCompatActivity {

     EditText etNumeroIdentificacion, etNombre, etApellidos, etEdad;
     Button btnEditar, btnActualizar, btnEliminar, btnRegresar;

     FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reg_children);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

     db = FirebaseFirestore.getInstance();

        etNumeroIdentificacion = findViewById(R.id.etNumeroIdentificacion);
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etEdad = findViewById(R.id.etEdad);
        btnEditar = findViewById(R.id.btnEditar);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnRegresar = findViewById(R.id.btnRegresar);

        btnActualizar.setOnClickListener(v -> guardarDatos());
        btnEditar.setOnClickListener(v -> editarDatos());
        btnEliminar.setOnClickListener(v -> eliminarDatos());
    }
    private void guardarDatos() {
        String id = etNumeroIdentificacion.getText().toString();
        String nombre = etNombre.getText().toString();
        String apellidos = etApellidos.getText().toString();
        String edad = etEdad.getText().toString();

        if (id.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || edad.isEmpty()) {
            // Muestra un mensaje de error si algún campo está vacío
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear un mapa de datos
        Map<String, Object> niño = new HashMap<>();
        niño.put("numeroIdentificacion", id);
        niño.put("nombre", nombre);
        niño.put("apellidos", apellidos);
        niño.put("edad", edad);

        // Guardar en Firestore
        db.collection("niños").document(id)
                .set(niño)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show());
    }
    private void editarDatos() {
        String id = etNumeroIdentificacion.getText().toString();

        db.collection("niños").document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        etNombre.setText(documentSnapshot.getString("nombre"));
                        etApellidos.setText(documentSnapshot.getString("apellidos"));
                        etEdad.setText(documentSnapshot.getString("edad"));
                    } else {
                        Toast.makeText(this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_SHORT).show());
    }
    private void eliminarDatos() {
        String id = etNumeroIdentificacion.getText().toString();

        db.collection("niños").document(id)
                .delete()
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Datos eliminados", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show());
    }


}