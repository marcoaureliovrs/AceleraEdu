package br.com.marcogorak.aceleraedu;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.marcogorak.aceleraedu.WebService.Conexao;
import br.com.marcogorak.aceleraedu.database.DataBase;
import br.com.marcogorak.aceleraedu.dominio.entidades.Curso;
import br.com.marcogorak.aceleraedu.dominio.entidades.Materia;
import br.com.marcogorak.aceleraedu.WebService.JSONParser;
import br.com.marcogorak.aceleraedu.util.ViewHelper;


public class ActCadMateria extends AppCompatActivity implements View.OnClickListener{

    // Declarando as variáveis
    JSONArray jsonArrayData;
    private ProgressDialog pDialog;
    private EditText edtMateria;
    private EditText edtDescricaoMateria;
    private Spinner spnCursoRelacionado;
    private Spinner spnDependenciaMateria;
    private ArrayAdapter<String> adpCursoRelacionado;
    private ArrayAdapter<String> adpDependenciaMateria;
    private Button btnSalvarMateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_materia);


        edtMateria = (EditText) findViewById(R.id.edtMateria);
        edtDescricaoMateria = (EditText) findViewById(R.id.edtDescricaoMateria);


        /**
         * Spinner: Uma lista de opções onde o usuario opta por uma a qual deseja.
         *
         */
        spnCursoRelacionado = (Spinner) findViewById(R.id.spnCursoRelacionado);
        adpCursoRelacionado = ViewHelper.createArrayAdapter(ActCadMateria.this, spnCursoRelacionado);

        spnDependenciaMateria = (Spinner) findViewById(R.id.spnDependenciaMateria);
        adpDependenciaMateria = ViewHelper.createArrayAdapter(ActCadMateria.this, spnDependenciaMateria);


        btnSalvarMateria = (Button) findViewById(R.id.btnSalvarMateria);
        btnSalvarMateria.setOnClickListener(this);

        adpCursoRelacionado.add("Seleciona um Curso");

        /**
         * Thread responsável por listar os cursos cadastrados e adiciona-los no Spinner através
         * de um objeto ArrayAdapter.
         */
        new Thread() {
            public void run() {
                Conexao conexao = new Conexao();
                jsonArrayData = conexao.consultar(Curso.CONSULT_URL, Curso.TAG_CURSOS);
                runOnUiThread(new Runnable() {
                    public void run() {

                        for (int i = 0; i < jsonArrayData.length(); i++) {
                            JSONObject jsonobject = null;
                            try {
                                jsonobject = jsonArrayData.getJSONObject(i);
                                adpCursoRelacionado.add(jsonobject.getString(Curso.TAG_CURSO));
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
    public void onClick(View arg0) {
        // TODO Auto-generated method stub
        new Register().execute();
    }

    class Register extends AsyncTask<String, String, String> {

        //Pré Execução
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActCadMateria.this);
            pDialog.setMessage("Cadastrando, Aguarde ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        //Executando enquanto é apresentando o processdialog
        @Override
        protected String doInBackground(String... args) {

            String materia = edtMateria.getText().toString();
            String descricao = edtDescricaoMateria.getText().toString();
            //TODO Arrumar a inclusão de curso relacionado para que ela insira o código do curso e não sua posição no spinner
            int cursorelacionado = spnCursoRelacionado.getSelectedItemPosition();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Materia.TAG_MATERIA, materia));
            params.add(new BasicNameValuePair(Materia.TAG_DESCRICAO, descricao));
            params.add(new BasicNameValuePair(Curso.TAG_CURSORELACIONADO, String.valueOf(cursorelacionado)));

            /**
             * Objeto conexão que chama o método cadastrar recebendo como parametro a URL a ser
             * chamada para cadastro e os parâmetros que serão passados
             */
            Conexao conexao = new Conexao();
            return conexao.cadastrar(Materia.REGISTER_URL,params);

        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            if (file_url != null) {
                Toast.makeText(ActCadMateria.this, file_url, Toast.LENGTH_LONG).show();
                finish();
            }

        }

    }


}
