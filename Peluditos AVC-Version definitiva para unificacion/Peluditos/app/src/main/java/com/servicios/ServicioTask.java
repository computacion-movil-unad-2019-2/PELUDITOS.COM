package com.servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.message.BufferedHeader;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import com.entidades.CInstituciones;

public class ServicioTask extends AsyncTask<Void, Void, String> {

    // variables de hilo

    private Context httpContext; //contexto
    ProgressDialog progressDialog;// Dialogo cargando
    public String resulatdoapi="";
    public String linkrequestAPI=""; // link para consumir el servicio rest
    public CInstituciones institucion = new CInstituciones();

    // constructor del hilo (AsyncTask)

    public ServicioTask(Context ctx, String linkAPI, CInstituciones obj){
        this.httpContext=ctx;
        this.linkrequestAPI=linkAPI;
        this.institucion = obj;
    }

    @Override
    protected String doInBackground(Void... params) {
        String result = null;

        String wsURL = linkrequestAPI;
        URL url = null;
        try{
            // se crea la conexion a la API
            url= new URL(wsURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            JSONObject parametrosPost = new JSONObject();
            parametrosPost.put("nit",this.institucion.getNit().toString());
            parametrosPost.put("razonSocial",this.institucion.getRazonSocial().toString());
            parametrosPost.put("objetivo",this.institucion.getObjetivo().toString());
            parametrosPost.put("ubicacion",this.institucion.getUbicacion().toString());
            parametrosPost.put("tipo",this.institucion.getTipo().toString());
            parametrosPost.put("cuenta",this.institucion.getCuenta().toString());
            parametrosPost.put("banco",this.institucion.getBanco().toString());

            //DEFINIR PARAMETROS DE CONEXION
            urlConnection.setReadTimeout(15000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);

            //OBTENER EL RESULTADO DEL REQUEST
            OutputStream os = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(parametrosPost));
            writer.flush();
            writer.close();
            os.close();

            int responseCode=urlConnection.getResponseCode(); // Conexion Ok!
            if(responseCode == HttpURLConnection.HTTP_OK){
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String linea="";
                while((linea = in.readLine()) != null){
                    sb.append(linea);
                    break;
                }

                in.close();
                result = sb.toString();
            }else{
                result = new String("Error:"+ responseCode);
            }

        } catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        progressDialog = progressDialog.show(httpContext, "procesando solicitud", "por favor espere");
    }

    @Override
    protected void onPostExecute(String s){
        super.onPostExecute(s);
        progressDialog.dismiss();
        resulatdoapi=s;
        //Toast.makeText(httpContext,resulatdoapi,Toast.LENGTH_LONG).show();
    }

    // Funciones
    // Transformar JSON Object a String
    public String getPostDataString(JSONObject params) throws Exception{
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itr = params.keys();

        while(itr.hasNext()){
            String key = itr.next();
            Object value = params.get(key);
            if(first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }

        return result.toString();
    }

}
