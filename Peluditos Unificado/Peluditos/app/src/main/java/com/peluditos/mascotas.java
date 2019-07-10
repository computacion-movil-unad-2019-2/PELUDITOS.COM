package com.peluditos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.entidades.tipo;
import com.entidades.tipoRespuesta;
import com.servicios.PostServiceMascota;
import com.servicios.PostTipo;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class mascotas extends AppCompatActivity {

    private String UPLOAD_URL ="http://peluditos.online/rest/index.php/SubirImagen/cargar/";

    private String KEY_IMAGEN = "foto";
    private String KEY_NOMBRE = "nombres";
    private Bitmap bitmap;
    private String imageFileName;

    private static final String TAG = "PELUDITOS";
    private EditText txtNombres;
    private EditText txtTamano;
    private EditText txtEdad;
    private EditText txtControlMedico;
    private EditText txtCualidadesMascota;
    private EditText txtUbicacion;
    private EditText txtEstado;
    private EditText txtTipo;
    private Button btnGuardar;
    private Retrofit retrofit;
    private ImageView imagen;
    private String id;
    private Spinner spTipo;
    private String [] lstLista;
    private ArrayAdapter<String> lstMascota;
    private Spinner spEstado;
    private ArrayAdapter<String> estado;
    private Button btnFoto;

    int num_aleatorio = (int) (Math.random()*10);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mascotas);

        txtNombres = (EditText) findViewById(R.id.txtNombreMascota);
        txtTamano = (EditText) findViewById(R.id.txtTamanoMascota);
        txtEdad = (EditText) findViewById(R.id.txtEdadMascota);
        txtControlMedico = (EditText) findViewById(R.id.txtControlMedico);
        txtCualidadesMascota = (EditText) findViewById(R.id.txtCualidadesMascota);
        txtUbicacion = (EditText) findViewById(R.id.txtUbicacionMascota);
        spTipo = (Spinner) findViewById(R.id.spTipo);
        txtTipo = (EditText) findViewById(R.id.txtTipo);
        imagen = (ImageView) findViewById(R.id.imagenMascota);
        spEstado = (Spinner) findViewById(R.id.spEstado);
        txtEstado = (EditText) findViewById(R.id.txtEstado);

        String [] lstEstado =  {"Disponible","En Proceso", "Adoptado"};
        estado = new ArrayAdapter<String>(getApplication(),android.R.layout.simple_spinner_item, lstEstado);
        spEstado.setAdapter(estado);

        btnFoto = findViewById(R.id.btnFoto);
        btnGuardar = findViewById(R.id.btnGuardarMascota);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://peluditos.online/rest/index.php/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String estado = getIntent().getStringExtra("actualizar");
        if(estado.equals("1")){
            id = getIntent().getStringExtra("id");
            String nombres = getIntent().getStringExtra("nombres");
            String edad = getIntent().getStringExtra("edad");
            String tamano = getIntent().getStringExtra("tamano");
            String controlMedico = getIntent().getStringExtra("controlMedico");
            String ciudadReferencia = getIntent().getStringExtra("ciudadReferencia");
            String ubicacion = getIntent().getStringExtra("ubicacion");
            String tipo = getIntent().getStringExtra("tipo");
            String foto = getIntent().getStringExtra("foto");
            String strEstado = getIntent().getStringExtra("estado");

            txtNombres.setEnabled(false);
            txtTamano.setEnabled(false);
            txtTamano.setEnabled(false);
            txtEdad.setEnabled(false);
            spTipo.setEnabled(false);
            spEstado.setEnabled(false);

            txtControlMedico.setEnabled(false);
            txtCualidadesMascota.setEnabled(false);
            txtUbicacion.setEnabled(false);
            txtTipo.setEnabled(false);
            txtEstado.setEnabled(false);

            txtNombres.setText(nombres);
            txtTamano.setText(tamano);
            txtEdad.setText(edad);
            txtTipo.setText(tipo);
            txtControlMedico.setText(controlMedico);
            txtCualidadesMascota.setText(ciudadReferencia);
            txtUbicacion.setText(ubicacion);
            txtEstado.setText(strEstado);
            
            //imagen.setImageBitmap(DownloadFullFromUrl("http://peluditos.online/img/toby.jpg"));
            //imagen.setImageURI(foto);
            btnGuardar.setVisibility(View.INVISIBLE);
            btnFoto.setVisibility(View.INVISIBLE);
            Glide.with(this).load(foto).into(imagen);
            spEstado.setVisibility(View.INVISIBLE);
            spTipo.setVisibility(View.INVISIBLE);

        }else{
            id =  "0";
            txtTipo.setVisibility(View.INVISIBLE);
            txtEstado.setVisibility(View.INVISIBLE);
            btnFoto.setVisibility(View.VISIBLE);
        }
        cargarTipo();


        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //|| txtTipo.getText().toString().trim().isEmpty()
                try {
                    if (txtNombres.getText().toString().trim().isEmpty()
                            || txtEdad.getText().toString().trim().isEmpty()
                            || txtTamano.getText().toString().trim().isEmpty()
                            || txtControlMedico.getText().toString().trim().isEmpty()
                            || txtCualidadesMascota.getText().toString().trim().isEmpty()
                            || imageFileName.toString().trim().isEmpty()
                            || txtUbicacion.getText().toString().trim().isEmpty()) {

                        Toast.makeText(getBaseContext(), "un campo esta vacio, y no se pudo guardar la mascota", Toast.LENGTH_LONG).show();

                    } else {

                        String nombres = txtNombres.getText().toString().trim();
                        String edad = txtEdad.getText().toString().trim();
                        String tamano = txtTamano.getText().toString().trim();
                        String controlMedico = txtControlMedico.getText().toString().trim();
                        String ciudadReferencia = txtCualidadesMascota.getText().toString().trim();
                        String ubicacion = txtUbicacion.getText().toString().trim();
                        String tipo = spTipo.getSelectedItem().toString().trim();
                        String estado = spEstado.getSelectedItem().toString().trim();
                        String foto = "http://peluditos.online/img/fotos/"+imageFileName.toString().trim()+".png";

                        sendPost(id, nombres, edad,
                                tamano, controlMedico, ciudadReferencia, ubicacion, tipo, estado, foto);

                        txtNombres.setText("");
                        txtTamano.setText("");
                        txtEdad.setText("");
                        txtControlMedico.setText("");
                        txtCualidadesMascota.setText("");
                        txtUbicacion.setText("");
                        uploadImage();

                        Toast.makeText(getBaseContext(), "Guardado con Exito", Toast.LENGTH_LONG).show();
                        onBackPressed();

                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getBaseContext(), "Error "+ e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomarFoto();
            }
        });

    }

    public void sendPost(String id, String nombres, String edad, String tamano,
                         String controlMedico, String ciudadReferencia, String ubicacion, String tipo,
                         String estado, String foto) {

        PostServiceMascota service = retrofit.create(PostServiceMascota.class);
        Call<String> call = service.savePost(id, nombres, edad,
                tamano, controlMedico, ciudadReferencia, ubicacion, tipo, estado, foto );

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.i(TAG, "Responsestring :"+ response.body().toString());
                if(response.isSuccessful()) {
                    if(response.body() != null) {
                        Log.i(TAG, "onSuccess." + response.body().toString());
                    }else{
                        Log.i(TAG, "Returned empty response");
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    private void cargarTipo(){
        PostTipo service = retrofit.create(PostTipo.class);
        Call<tipoRespuesta> listRespuestaCall = service.obtenerLista();

        listRespuestaCall.enqueue(new Callback<tipoRespuesta>() {
            @Override
            public void onResponse(Call<tipoRespuesta> call, retrofit2.Response<tipoRespuesta> response) {

                if(response.isSuccessful()){
                    tipoRespuesta lstRespuesta = response.body();
                    ArrayList<tipo> lstAdop = lstRespuesta.getResult();
                    lstLista = new String[lstAdop.size()];
                    int i = 0;
                    for(tipo item : lstAdop)
                    {
                        lstLista[i] = item.getNombres();
                        i += 1;
                    }
                    lstMascota = new ArrayAdapter<String>(getApplication(),android.R.layout.simple_spinner_item, lstLista);
                    spTipo.setAdapter(lstMascota);

                }else{
                    Log.e(TAG, "onResponse:"+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<tipoRespuesta> call, Throwable t) {
                Log.e(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }

    //Método para crear un nombre único de cada fotografia
    String mCurrentPhotoPath;
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "Mascota_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".png", storageDir);

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    //Método para tomar foto y crear el archivo
    static final int REQUEST_TAKE_PHOTO = 1;
    public void tomarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.d("Error tomarFoto ",ex.getMessage().toString());
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,"com.peluditos.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI.toString());
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    //Método para mostrar vista previa en un imageview de la foto tomada
    static final int REQUEST_IMAGE_CAPTURE = 1;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            //Bitmap imageBitmap = (Bitmap) extras.get("data");
            //img.setImageBitmap(imageBitmap);
            bitmap =  (Bitmap) extras.get("data");
            imagen.setImageBitmap(bitmap);
        }
    }

    private void uploadImage(){
        //Mostrar el diálogo de progreso
        //final ProgressDialog loading = ProgressDialog.show(this,"Subiendo...","Espere por favor...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Descartar el diálogo de progreso
                        //loading.dismiss();
                        //Mostrando el mensaje de la respuesta
                        Toast.makeText(getBaseContext(), s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        //loading.dismiss();

                        //Showing toast
                        Toast.makeText(getBaseContext(), volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String imagen = getStringImagen(bitmap);

                //Obtener el nombre de la imagen
                //String nombre = editTextName.getText().toString().trim();
                String nombre = imageFileName.toString().trim();

                //Creación de parámetros
                Map<String,String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put(KEY_IMAGEN, imagen);
                params.put(KEY_NOMBRE, nombre);

                //Parámetros de retorno
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);
    }

    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


}
