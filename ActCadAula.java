package br.com.marcogorak.aceleraedu;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import br.com.marcogorak.aceleraedu.WebService.Conexao;
import br.com.marcogorak.aceleraedu.dominio.entidades.Aula;
import br.com.marcogorak.aceleraedu.WebService.JSONParser;
import br.com.marcogorak.aceleraedu.dominio.entidades.Materia;
import br.com.marcogorak.aceleraedu.util.ViewHelper;


public class ActCadAula extends AppCompatActivity implements View.OnClickListener{


    //Declaração de Atributos
    JSONArray jsonArrayData;
    private ProgressDialog pDialog;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private EditText edtNomeAula;
    private EditText edtDescricaoAula;
    private Spinner spnMateria;
    private ArrayAdapter<String> adpMateria;
    private Button btnVideo;
    private Button btnApostila;
    private Button btnApresentacao;
    private Button btnExercicio;
    private Button btnSalvarAula;
    private Button btnThumbnail;
    
    private static final int REQUEST_FOTO = 1;
    private static final int REQUEST_VIDEO = 2;
    private static final int REQUEST_APOSTILA = 3;
    private static final int REQUEST_APRESENTACAO = 4;
    private static final int REQUEST_EXERCICIO = 5;

    String caminhoImagem = "teste";
    String caminhoVideo = "teste";
    String caminhoApostila = "teste";
    String caminhoApresentacao = "teste";
    String caminhoExercicio = "teste";

    String linkImagem = "teste";
    String linkVideo = "teste";
    String linkApostila = "teste";
    String linkApresentacao = "teste";
    String linkExercicios= "teste";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_aula);

        edtNomeAula= (EditText)findViewById(R.id.edtNomeAula);
        edtDescricaoAula = (EditText)findViewById(R.id.edtDescricao);

        spnMateria = (Spinner)findViewById(R.id.spnMateria);
        adpMateria = ViewHelper.createArrayAdapter(this, spnMateria);

        btnVideo = (Button)findViewById(R.id.btnVideo);
        btnApostila = (Button)findViewById(R.id.btnApostila);
        btnApresentacao = (Button)findViewById(R.id.btnApresentacao);
        btnExercicio = (Button)findViewById(R.id.btnExercicio);
        btnSalvarAula = (Button)findViewById(R.id.btnSalvarAula);
        btnThumbnail = (Button)findViewById(R.id.btnThumbnail);

        btnVideo.setOnClickListener(this);
        btnApresentacao.setOnClickListener(this);
        btnApostila.setOnClickListener(this);
        btnExercicio.setOnClickListener(this);
        btnThumbnail.setOnClickListener(this);
        btnSalvarAula.setOnClickListener(this);

        adpMateria.add("Selecione a Matéria");


        new Thread() {
            public void run() {
                Conexao conexao = new Conexao();
                jsonArrayData = conexao.consultar(Materia.CONSULT_URL, Materia.TAG_MATERIAS);
                runOnUiThread(new Runnable() {
                    public void run() {
                        for (int i = 0; i < jsonArrayData.length(); i++) {
                            JSONObject jsonobject = null;
                            try {
                                jsonobject = jsonArrayData.getJSONObject(i);
                                adpMateria.add(jsonobject.getString(Materia.TAG_MATERIA));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }.start();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_FOTO) {
            Uri selectedImageUri = data.getData();
            caminhoImagem = getPath(selectedImageUri);
            Log.d("caminhoArquivo",caminhoImagem);

            //mTxtArquivo.setText(getPath(selectedImageUri));
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_VIDEO) {
            Uri selectedImageUri = data.getData();
            caminhoVideo = getPath(selectedImageUri);
            Log.d("caminhoArquivo",caminhoVideo);

            //mTxtArquivo.setText(getPath(selectedImageUri));
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_APOSTILA) {

            caminhoApostila = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            if (caminhoApostila != null) {
                Log.d("caminhoArquivo",caminhoApostila);
            } else {
                Log.d("caminhoArquivo","null");
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_APRESENTACAO) {

            caminhoApresentacao = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            if (caminhoApresentacao != null) {
                Log.d("caminhoArquivo",caminhoApresentacao);
            } else {
                Log.d("caminhoArquivo","null");
            }
        } else if (resultCode == RESULT_OK && requestCode == REQUEST_EXERCICIO) {

            caminhoExercicio = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            if (caminhoExercicio != null) {
                Log.d("caminhoArquivo",caminhoExercicio);
            } else {
                Log.d("caminhoArquivo","null");
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

            if (btnThumbnail.getId() == id) {
                selecionarFoto();
            } else if (btnVideo.getId() == id) {
                selecionarVideo();
            } else if (btnApresentacao.getId() == id) {
                selecionarApresentacao();
            } else if (btnApostila.getId() == id) {
                selecionarApostila();
            } else if (btnExercicio.getId() == id) {
                selecionarExercicio();
            } else if (btnSalvarAula.getId() == id) {
                enviarDados();
                //Log.d("enviarFoto", "Chamada da função enviarFoto");
                //Log.d("caminhoVideo",caminhoVideo);
                //Log.d("caminhoImagem", caminhoImagem);
            }
    }


    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(
                uri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(
                MediaStore.Images.Media.DATA);

        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void selecionarFoto() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_FOTO);
    }

    private void selecionarVideo() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("video/*");
        startActivityForResult(intent, REQUEST_VIDEO);
    }

    private void selecionarApostila() {

        Intent intent = new Intent(this, FilePickerActivity.class);
        //intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*\\.pdf$"));
        intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, true);
        intent.putExtra(FilePickerActivity.ARG_SHOW_HIDDEN, true);
        startActivityForResult(intent, REQUEST_APOSTILA);


    }

    private void selecionarApresentacao() {
        Intent intent = new Intent(this, FilePickerActivity.class);
        //intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*\\.pdf$"));
        intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, true);
        intent.putExtra(FilePickerActivity.ARG_SHOW_HIDDEN, true);
        startActivityForResult(intent, REQUEST_APRESENTACAO);

    }

    private void selecionarExercicio() {
        Intent intent = new Intent(this, FilePickerActivity.class);
        //intent.putExtra(FilePickerActivity.ARG_FILE_FILTER, Pattern.compile(".*\\.pdf$"));
        intent.putExtra(FilePickerActivity.ARG_DIRECTORIES_FILTER, true);
        intent.putExtra(FilePickerActivity.ARG_SHOW_HIDDEN, true);
        startActivityForResult(intent, REQUEST_EXERCICIO);


    }

    private void enviarDados() {
        new UploadArquivoTask().execute();

    }


    class UploadArquivoTask extends AsyncTask<Void, Void, Boolean> {

        //Pré Execução
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActCadAula.this);
            pDialog.setMessage("Cadastrando, Aguarde ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            boolean sucesso = false;
            try {
                if (caminhoImagem !=null || caminhoVideo !=null || caminhoApostila !=null ||
                        caminhoApresentacao !=null || caminhoExercicio !=null) {
                    //Upload dos arquivos anexados
                    linkImagem = Conexao.enviarDados(caminhoImagem);
                    linkApostila = Conexao.enviarDados(caminhoApostila);
                    linkApresentacao = Conexao.enviarDados(caminhoApresentacao);
                    linkExercicios = Conexao.enviarDados(caminhoExercicio);
                    linkVideo = Conexao.enviarDados(caminhoVideo);
                } else {
                    Toast.makeText(ActCadAula.this, "Indexe todos os arquivos para o upload", Toast.LENGTH_SHORT).show();
                }

                sucesso = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return sucesso;
        }

        @Override
        protected void onPostExecute(Boolean sucesso) {
            super.onPostExecute(sucesso);
            pDialog.dismiss();
            if (sucesso == true) {
                new CadastrarAula().execute();
            }
            int mensagem = sucesso ?
                    R.string.msg_sucesso :
                    R.string.msg_falha ;

            Toast.makeText(ActCadAula.this, mensagem, Toast.LENGTH_SHORT).show();
        }
    }


    class CadastrarAula extends AsyncTask<String, String, String> {


        //Pré Execução
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActCadAula.this);
            pDialog.setMessage("Cadastrando, Aguarde ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        //Executando enquanto é apresentndo o processdialog
        @Override
        protected String doInBackground(String... args) {

            int success;
            String nomeAula = edtNomeAula.getText().toString();
            String descricaoAula = edtDescricaoAula.getText().toString();
            //TODO Arrumar a inclusão de curso relacionado para que ela insira o código do curso e não sua posição no spinner
            int materiarelacionada = spnMateria.getSelectedItemPosition();

            try {
                if (caminhoImagem !=null || caminhoVideo !=null || caminhoApostila !=null ||
                        caminhoApresentacao !=null || caminhoExercicio !=null) {


                    try {
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair(Aula.TAG_NOMEAULA, nomeAula));
                        params.add(new BasicNameValuePair(Aula.TAG_DESCAULA, descricaoAula));
                        params.add(new BasicNameValuePair(Aula.TAG_MATERIARELACIONADA, String.valueOf(materiarelacionada)));
                        params.add(new BasicNameValuePair(Aula.TAG_URLVIDEOAULA, linkVideo));
                        params.add(new BasicNameValuePair(Aula.TAG_URLDOCAULA, linkApostila));
                        params.add(new BasicNameValuePair(Aula.TAG_URLPPTAULA, linkApresentacao));
                        params.add(new BasicNameValuePair(Aula.TAG_URLEXERCAULA, linkExercicios));
                        params.add(new BasicNameValuePair(Aula.TAG_URLTHUMBNAIL, linkImagem));


                        Log.d("request!", "starting");
                        JSONObject json = JSONParser.makeHttpRequest(Aula.REGISTER_URL, "POST", params);
                        Log.d("Register attempt", json.toString());
                        success = json.getInt(TAG_SUCCESS);


                        if (success == 1) {
                            Log.d("Aula Cadastrada!", json.toString());
                            finish();
                            return json.getString(TAG_MESSAGE);
                        } else {
                            Log.d("Aula Erro!", json.getString(TAG_MESSAGE));
                            return json.getString(TAG_MESSAGE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                    //Senão apresente ao usuário "Indexe todos os arquivos para o upload"
                } else {
                    Toast.makeText(ActCadAula.this, "Indexe todos os arquivos para o upload", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            if (file_url != null) {
                Toast.makeText(ActCadAula.this, file_url, Toast.LENGTH_LONG).show();
            }

        }

    }




}
