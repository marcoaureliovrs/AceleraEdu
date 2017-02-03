package br.com.marcogorak.aceleraedu;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
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


public class Logo extends AppCompatActivity {

    //Criando o Objeto da classe DataBase
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private RepositorioUsuario repositorioUsuario;
    private Usuario usuario;
    JSONArray jsonArrayUsers = null;
    private final int DURACAO_DA_TELA = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);


        //Retorna ao objeto criado a referencia ao metodo construtor da respectiva classe DataBase
        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            repositorioUsuario = new RepositorioUsuario(conn);
            usuario = repositorioUsuario.loginUsuario(this);

        } catch (SQLException ex) {
            MessageBox.show(this, "Erro", "Erro ao criar o banco: " + ex.getMessage());
        }


        if (usuario != null) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    new AttemptLogin().execute();
                }
            }, DURACAO_DA_TELA);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent it = new Intent(Logo.this, ActLogin.class);
                    Logo.this.startActivity(it);
                    Logo.this.finish();
                }
            }, DURACAO_DA_TELA);
        }

    }




    class AttemptLogin extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                            //codNivelUsuario=jsonobject.getString(TAG_NIVELUSER);
                            usuario = new Usuario(
                                    jsonobject.getString(Usuario.TAG_NOMEUSUARIO),
                                    jsonobject.getString(Usuario.TAG_EMAILUSUARIO),
                                    jsonobject.getInt(Usuario.TAG_NIVELUSER));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    Intent i = new Intent(Logo.this, ActCurso.class);
                    i.putExtra("usuario", usuario);
                    startActivity(i);
                    return "Login realizado com Sucesso";

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {

            if (file_url != null) {
                Toast.makeText(Logo.this, file_url, Toast.LENGTH_LONG).show();
                //Checa se houve sucesso na autenticação e finaliza o Activity
                if (jsonArrayUsers != null) {
                    finish();
                }

            }

        }

    }


}
