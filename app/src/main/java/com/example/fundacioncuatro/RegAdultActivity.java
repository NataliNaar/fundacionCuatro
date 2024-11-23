package com.example.fundacioncuatro;

import android.annotation.SuppressLint;
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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegAdultActivity extends AppCompatActivity {

    EditText etNumeroIdentificacion, etNombre, etApellidos, etprofesion;
    Button btnEditar, btnGuardar, btnEliminar, btnRegresar;

    FirebaseFirestore db = FirebaseFirestore.getInstance();;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reg_adult);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //db = FirebaseFirestore.getInstance();

        // Conectar los elementos
        etNumeroIdentificacion = findViewById(R.id.etNumeroIdentificacion);
        etNombre = findViewById(R.id.etNombre);
        etApellidos = findViewById(R.id.etApellidos);
        etprofesion = findViewById(R.id.etProfesion);

        btnEditar = findViewById(R.id.btnEditar);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnRegresar = findViewById(R.id.btnRegresar);

        // Agregar funcionalidad a los botones
       btnGuardar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String id = etNumeroIdentificacion.getText().toString();
               String nombre = etNombre.getText().toString();
               String apellidos = etApellidos.getText().toString();
               String profesion = etprofesion.getText().toString();

               if (id.isEmpty() || nombre.isEmpty() || apellidos.isEmpty() || profesion.isEmpty()) {
                   // Muestra un mensaje de error si algún campo está vacío
                   Toast.makeText(getApplicationContext(), "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                   return;
               }

               // Crear un mapa de datos
               Map<String, Object> macudientes = new HashMap<>();
               macudientes.put("numeroIdentificacion", id);
               macudientes.put("nombre", nombre);
               macudientes.put("apellidos", apellidos);
               macudientes.put("Profesion", profesion);

               // Guardar en Firestore
               db.collection("acudientes")
                       .add(macudientes)
                       .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                           @Override
                           public void onSuccess(DocumentReference documentReference) {
                               Toast.makeText(getApplicationContext(), "Acudiente guardado ....", Toast.LENGTH_SHORT).show();
                           }
                       });

           }
       });

    }


    private void editarDatos() {
        String id = etNumeroIdentificacion.getText().toString();

        db.collection("acudientes").document(id)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        etNombre.setText(documentSnapshot.getString("nombre"));
                        etApellidos.setText(documentSnapshot.getString("apellidos"));
                        etprofesion.setText(documentSnapshot.getString("edad"));
                    } else {
                        Toast.makeText(this, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al obtener datos", Toast.LENGTH_SHORT).show());
    }
    private void eliminarDatos() {
        String id = etNumeroIdentificacion.getText().toString();

        db.collection("acudientes").document(id)
                .delete()
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Datos eliminados", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show());
    }
    }
