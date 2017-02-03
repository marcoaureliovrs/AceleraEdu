package br.com.marcogorak.aceleraedu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.marcogorak.aceleraedu.WebService.Conexao;
import br.com.marcogorak.aceleraedu.adapter.CursosAdapter;
import br.com.marcogorak.aceleraedu.dominio.entidades.Aula;
import br.com.marcogorak.aceleraedu.dominio.entidades.Curso;
import br.com.marcogorak.aceleraedu.dominio.entidades.Materia;
import br.com.marcogorak.aceleraedu.dominio.entidades.Usuario;

public class ActCurso extends AppCompatActivity implements View.OnClickListener {


    //Declaração de Atributos
    JSONArray jsonArrayCursos;
    ArrayList<HashMap<String, String>> arraylistCursos;
    ListView listviewCursos;
    CursosAdapter cursosAdapter;
    public Intent it;
    ProgressBar progressBarCurso;
    Usuario usuario;

    public FloatingActionButton actionButton;
    public FloatingActionMenu actionMenu;
    public SubActionButton buttonCadAula;
    public SubActionButton buttonCadMateria;
    public SubActionButton buttonCadCurso;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_curso);

        //Recuperando o listViewAulas
        listviewCursos = (ListView)findViewById(R.id.listviewCursos);
        progressBarCurso = (ProgressBar) findViewById(R.id.progressBarCurso);

        it = getIntent();
        usuario = it.getParcelableExtra("usuario");
        int codNivel = usuario.getNivelUsuario();

        new listarCursos().execute();



        //Float botton apresentado ao administrador do aplicativo

        //Botão mãe +
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_main);

        //Cadastro de Aula
        ImageView iconCadAula = new ImageView(this);
        iconCadAula.setImageResource(R.mipmap.ic_aula);

        //Cadastro de Matéria
        ImageView iconCadMateria = new ImageView(this);
        iconCadMateria.setImageResource(R.mipmap.ic_materia);

        //Cadastro de curso
        ImageView iconCadCurso = new ImageView(this);
        iconCadCurso.setImageResource(R.mipmap.ic_curso);


        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        buttonCadAula = itemBuilder.setContentView(iconCadAula).build();
        buttonCadMateria = itemBuilder.setContentView(iconCadMateria).build();
        buttonCadCurso = itemBuilder.setContentView(iconCadCurso).build();

        buttonCadAula.setId(1);
        buttonCadCurso.setId(2);
        buttonCadMateria.setId(3);

        buttonCadAula.setOnClickListener(ActCurso.this);
        buttonCadMateria.setOnClickListener(ActCurso.this);
        buttonCadCurso.setOnClickListener(ActCurso.this);

        //Dependendo do nível do usuário é apresentado todas as opções de cadastro ou uma parte das
        //opções (Cadastro de Curso, Matéria e Aula)
        switch (codNivel) {
            case 1:
                break;
            case 2:
                actionButton = new FloatingActionButton.Builder(this)
                        .setContentView(imageView)
                        .build();
                actionMenu = new FloatingActionMenu.Builder(this)
                        .addSubActionView(buttonCadAula)
                        .attachTo(actionButton)
                        .build();
                break;
            case 3:

                actionButton = new FloatingActionButton.Builder(this)
                        .setContentView(imageView)
                        .build();

                actionMenu = new FloatingActionMenu.Builder(this)
                        .addSubActionView(buttonCadMateria)
                        .addSubActionView(buttonCadCurso)
                        .attachTo(actionButton)
                        .build();
                break;
            case 4:

                actionButton = new FloatingActionButton.Builder(this)
                        .setContentView(imageView)
                        .build();
                actionMenu = new FloatingActionMenu.Builder(this)
                        .addSubActionView(buttonCadAula)
                        .addSubActionView(buttonCadMateria)
                        .addSubActionView(buttonCadCurso)
                        .attachTo(actionButton)
                        .build();
                break;
            default:
        }


    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == buttonCadCurso.getId()) {
            it = new Intent(this, ActCadCurso.class);
            startActivity(it);
        } else if (id == buttonCadMateria.getId()) {
            it = new Intent(this, ActCadMateria.class);
            startActivity(it);
        } else if (id == buttonCadAula.getId()) {
            it = new Intent(this, ActCadAula.class);
            startActivity(it);
        }

    }

    private void exibirProgress(boolean exibir) {
        if (exibir == true) {
            progressBarCurso.setVisibility(exibir ? View.VISIBLE : View.GONE);
        } else {
            progressBarCurso.setVisibility(exibir ? View.INVISIBLE : View.GONE);
        }
    }


    public class listarCursos extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgress(true);
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
            arraylistCursos = new ArrayList<HashMap<String, String>>();
            //List<NameValuePair> parametro = new ArrayList<NameValuePair>();

            //parametro.add(new BasicNameValuePair(Materia.TAG_MATERIAS, "1"));


            try {
                Conexao conexao = new Conexao();
                jsonArrayCursos = conexao.consultar(Curso.CONSULT_URL, Curso.TAG_CURSOS);
                //JSONArray jsonSemestres = conexao.consultar(Curso.CONSULT_URL, Materia.TAG_MATERIAS, parametro);

                    for (int i = 0; i < jsonArrayCursos.length(); i++) {
                        JSONObject jsonobject = null;
                        HashMap<String, String> map = new HashMap<String, String>();

                        try {
                            jsonobject = jsonArrayCursos.getJSONObject(i);
                            map.put(Curso.TAG_CODCURSO, jsonobject.getString(Curso.TAG_CODCURSO));
                            map.put(Curso.TAG_CURSO, jsonobject.getString(Curso.TAG_CURSO));

                            arraylistCursos.add(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                return arraylistCursos;
            } catch (Exception e) {
                        e.printStackTrace();
                    }
            return null;
        }

        protected void onPostExecute(ArrayList<HashMap<String, String>> arraylistCursos) {
            exibirProgress(false);
            if (arraylistCursos !=null) {
                //Criando um ArrayAdapter
                cursosAdapter = new CursosAdapter(ActCurso.this, arraylistCursos);

                //Adaptando o listView para o ArrayAdapter
                listviewCursos.setAdapter(cursosAdapter);

                //Caso o usuário Clicar em uma das opcões do ListView será executado esse método
                listviewCursos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        HashMap<String, String> resultp = new HashMap<String, String>();
                        resultp = (HashMap<String, String>) cursosAdapter.getItem(arg2);


                        Curso curso = new Curso();
                        curso.setCodCurso(Long.parseLong(resultp.get(Curso.TAG_CODCURSO)));

                        Intent it;
                        it = new Intent(ActCurso.this, MainActivity.class);
                        // Passando parâmetros de um Activity a outro
                        it.putExtra(Curso.TAG_CURSO, curso);
                        it.putExtra("usuario", usuario);
                        //startActivityForResult(it, 0);
                        startActivity(it);
                    }

                });

            } else {
                Toast.makeText(ActCurso.this, "Falha ao carregar cursos", Toast.LENGTH_LONG).show();
            }

        }



    }
}
