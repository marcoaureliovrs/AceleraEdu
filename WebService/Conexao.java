package br.com.marcogorak.aceleraedu.WebService;


import android.util.Log;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.marcogorak.aceleraedu.adapter.MateriasAdapter;

/**
 * Created by MarcoGorak on 25/03/16.
 */

public class Conexao  {

    //Declaração de Objetos
    JSONObject jsonobject;
    JSONArray jsonArrayData;

    //Constantes contendo os nomes das Tags usadas no WebService
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    int status;


    public Conexao() {
    }

    //Consulta sem passagem de parametros
    public JSONArray consultar(final String urlConsulta, final String arrayJson){

                try {
                    List<NameValuePair> param = new ArrayList<NameValuePair>();
                    Log.d("request!", "Starting");
                    //Conexão via protocolo HTTP recuperando um objeto json
                    jsonobject = JSONParser.makeHttpRequest(urlConsulta,"POST",param);
                    Log.d("Loding Restaurant", jsonobject.toString());

                    //Caso a conexão for realizada com sucesso o status recebe um valor diferente de 0
                    status = jsonobject.getInt(TAG_SUCCESS);

                    //Valida se o status é diferente de zero
                    if(status != 0) {
                       return jsonArrayData = jsonobject.getJSONArray(arrayJson);
                    }else{
                        Log.d("Loding Restaurant Failure!", jsonobject.getString(TAG_MESSAGE));
                    }
                } catch (JSONException e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }


        return null;
    }

//Consulta com passagem de parametros
    public JSONArray consultar(final String urlConsulta, final String arrayJson, List<NameValuePair> params){

        try {
            //List<NameValuePair> param = new ArrayList<NameValuePair>();
            Log.d("request!", "Starting");
            //Conexão via protocolo HTTP recuperando um objeto json
            jsonobject = JSONParser.makeHttpRequest(urlConsulta,"POST",params);
            Log.d("Loding Restaurant", jsonobject.toString());

            //Caso a conexão for realizada com sucesso o status recebe um valor diferente de 0
            status = jsonobject.getInt(TAG_SUCCESS);

            //Valida se o status é diferente de zero
            if(status != 0) {
                return jsonArrayData = jsonobject.getJSONArray(arrayJson);
            }else{
                Log.d("Loding Restaurant Failure!", jsonobject.getString(TAG_MESSAGE));
            }
        } catch (JSONException e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }


        return null;
    }



    public String cadastrar (final String urlConsulta, List<NameValuePair> params) {
        int success;
        try {
            Log.d("request!", "starting");

            JSONObject json = JSONParser.makeHttpRequest(urlConsulta,"POST", params);
            Log.d("Register attempt", json.toString());
            success = json.getInt(TAG_SUCCESS);

            if (success == 1) {
                Log.d("User Created!", json.toString());
                return json.getString(TAG_MESSAGE);
            } else {
                Log.d("Register Failure!", json.getString(TAG_MESSAGE));
                return json.getString(TAG_MESSAGE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String enviarDados(String path) throws Exception {

        String caminhoDoArquivoNoDispositivo = path;
        final String titulo = "teste";
        final String urlDoServidor = "http://aceleraedu.com.br/assets/upload_foto/upload_file.php";

        //final String urlDoServidor = "http://kubyx.fabriquinha.com/admin/assets/upload_foto/upload_file.php";
        final String fimDeLinha = "\r\n";
        final String menosMenos = "--";
        final String delimitador = "*****";

        FileInputStream fileInputStream = null;
        DataOutputStream outputStream = null;
        HttpURLConnection conexao = null;
        InputStream is = null;
        String retorno = null;

        try {
            URL url = new URL(urlDoServidor);
            conexao = (HttpURLConnection) url.openConnection();
            conexao.setDoInput(true);
            conexao.setDoOutput(true);
            conexao.setUseCaches(false);
            conexao.setRequestMethod("POST");

            // Adicionando cabeçalhos
            conexao.setRequestProperty("Connection", "Keep-Alive");
            conexao.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + delimitador);
            conexao.connect();

            outputStream = new DataOutputStream(conexao.getOutputStream());

            // Adicionando campo titulo
            outputStream.writeBytes(menosMenos + delimitador + fimDeLinha);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"titulo\"");
            outputStream.writeBytes(fimDeLinha);
            outputStream.writeBytes(fimDeLinha);
            outputStream.writeBytes(titulo);
            outputStream.writeBytes(fimDeLinha);

            // Adicionando arquivo
            outputStream.writeBytes(menosMenos + delimitador + fimDeLinha);
            outputStream.writeBytes("Content-Disposition: form-data; "+
                    "name=\"arquivo\";filename=\""+
                    caminhoDoArquivoNoDispositivo + "\"" + fimDeLinha);
            outputStream.writeBytes(fimDeLinha);

            // Stream para ler o arquivo
            fileInputStream = new FileInputStream(
                    new File(caminhoDoArquivoNoDispositivo));

            byte[] buffer;
            int bytesRead, bytesAvailable, bufferSize;
            int maxBufferSize = 1 * 1024 * 1024; // 1MB

            // Preparando para escrever arquivo
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // Lendo arquivo e escrevendo na conexão
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }
            outputStream.writeBytes(fimDeLinha);
            outputStream.writeBytes(menosMenos + delimitador + menosMenos + fimDeLinha);

            int serverResponseCode = conexao.getResponseCode();



            try {
                is = conexao.getInputStream();
                int ch;
                StringBuffer sb = new StringBuffer();
                while ((ch = is.read()) != -1) {
                    sb.append((char) ch);
                }
               retorno = sb.toString();
            } catch (IOException e) {
                throw e;
            } finally {
                if (is != null) {
                    is.close();
                }
            }


            String serverResponseMessage = conexao.getResponseMessage();

            if (serverResponseCode != HttpURLConnection.HTTP_OK){
                Log.d("NGVL", serverResponseCode + " = " + serverResponseMessage);
                throw new RuntimeException(
                        "Error "+ serverResponseCode +": "+ serverResponseMessage);
            }

        } finally {
            if (fileInputStream != null) fileInputStream.close();
            if (outputStream != null){
                outputStream.flush();
                outputStream.close();
            }
        }

        if (retorno != null) {
            Log.d("Retorno", retorno);
        }
        return retorno;
    }



}
