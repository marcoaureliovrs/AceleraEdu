package br.com.marcogorak.aceleraedu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.marcogorak.aceleraedu.WebService.Conexao;
import br.com.marcogorak.aceleraedu.WebService.JSONParser;
import br.com.marcogorak.aceleraedu.database.DataBase;
import br.com.marcogorak.aceleraedu.dominio.RepositorioUsuario;
import br.com.marcogorak.aceleraedu.dominio.entidades.Usuario;
import br.com.marcogorak.aceleraedu.util.MessageBox;

public class ActLogin extends ActionBarActivity implements OnClickListener {

    private EditText user, pass;
    private Button login, register;
    JSONArray jsonArrayUsers;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    Usuario usuario;
    String email;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioUsuario repositorioUsuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actlogin);

        //Recuperação dos objetos do Front-End
        user = (EditText) findViewById(R.id.edtUsername);
        pass = (EditText) findViewById(R.id.edtPassword);
        login = (Button) findViewById(R.id.btnLogin);
        register = (Button) findViewById(R.id.btnRegisterUser);

        //Introduzindo o evento OnClickListener
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            repositorioUsuario = new RepositorioUsuario(conn);

        } catch (SQLException ex) {
            MessageBox.show(this, "Erro", "Erro ao criar o banco: " + ex.getMessage());
        }

    }

    //Evento onClick, caso o usuário clique no botão Login ou Cadastrar uma das opções do case
    //Abaixo serão executadas
    @Override
    public void onClick(View v) {
        email = user.getText().toString();

        switch (v.getId()) {
            case R.id.btnLogin:
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    user.setError(getString(R.string.error_invalid_email));
                } else {
                    usuario = new Usuario ();
                    usuario.setEmailUsuario(user.getText().toString());
                    usuario.setSenhaUsuario(pass.getText().toString());
                    new AttemptLogin().execute();
                }
                break;
            case R.id.btnRegisterUser:
                Intent i = new Intent(ActLogin.this, ActCadUsuario.class);
                startActivity(i);
                break;

            default:
                break;
        }
    }


    class AttemptLogin extends AsyncTask<String, String, String> {
        boolean failure = false;
        int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActLogin.this);
            pDialog.setMessage("Carregando, aguarde ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {

            String username = usuario.getEmailUsuario();
            String password = usuario.getSenhaUsuario();


            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                Conexao conexao = new Conexao();

                params.add(new BasicNameValuePair(Usuario.TAG_USERNAME, username));
                params.add(new BasicNameValuePair(Usuario.TAG_PASSWORD, password));

                jsonArrayUsers = conexao.consultar(Usuario.LOGIN_URL,Usuario.TAG_USUARIO,params);

                for (int i = 0; i < jsonArrayUsers.length(); i++) {
                    JSONObject jsonobject = null;

                    try {
                        jsonobject = jsonArrayUsers.getJSONObject(i);
                        usuario = new Usuario(
                                jsonobject.getString(Usuario.TAG_NOMEUSUARIO),
                                jsonobject.getString(Usuario.TAG_EMAILUSUARIO),
                                jsonobject.getInt(Usuario.TAG_NIVELUSER));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                usuario.setSenhaUsuario(password);
                repositorioUsuario.inserir(usuario);
                Intent i = new Intent(ActLogin.this, ActCurso.class);
                i.putExtra("usuario", usuario);
                startActivity(i);
                return "Login realizado com Sucesso";

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();
            if (file_url != null) {
                Toast.makeText(ActLogin.this, file_url, Toast.LENGTH_LONG).show();
                //Checa se houve sucesso na autenticação e finaliza o Activity
                if (jsonArrayUsers != null) {
                    finish();
                }

            }

        }

    }
}