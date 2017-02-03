package br.com.marcogorak.aceleraedu;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import br.com.marcogorak.aceleraedu.WebService.Conexao;
import br.com.marcogorak.aceleraedu.dominio.entidades.Curso;
import br.com.marcogorak.aceleraedu.WebService.JSONParser;


public class ActCadCurso extends AppCompatActivity implements OnClickListener{

    private ProgressDialog pDialog;
    private EditText edtCurso;
    private EditText edtDescricao;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_curso);

        //Recuperação dos objetos declarados no front-end
        edtCurso = (EditText)findViewById(R.id.edtCurso);
        edtDescricao = (EditText)findViewById(R.id.edtDescricao);
        btnSalvar = (Button)findViewById(R.id.btnSalvarCurso);

        //Caso o botão btnSalvar for pressionado nesso activity ele retorna um evento a ao método onClick
        btnSalvar.setOnClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        new Register().execute();
    }


    class Register extends AsyncTask<String, String, String> {

        //Pré Execução
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActCadCurso.this);
            pDialog.setMessage("Cadastrando, Aguarde ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        //Executando enquanto é apresentndo o processdialog
        @Override
        protected String doInBackground(String... args) {

            String curso = edtCurso.getText().toString();
            String descricao = edtDescricao.getText().toString();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Curso.TAG_CURSO, curso));
            params.add(new BasicNameValuePair(Curso.TAG_DESCRICAO, descricao));

            Conexao conexao = new Conexao();
            return conexao.cadastrar(Curso.REGISTER_URL,params);

        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            if (file_url != null) {
                Toast.makeText(ActCadCurso.this, file_url, Toast.LENGTH_LONG).show();
                finish();
            }

        }

    }
}
