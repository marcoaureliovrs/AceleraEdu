package br.com.marcogorak.aceleraedu;


import android.content.Intent;
import android.content.res.Configuration;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import br.com.marcogorak.aceleraedu.adapter.AulasAdapter;
import br.com.marcogorak.aceleraedu.adapter.MateriasAdapter;
import br.com.marcogorak.aceleraedu.database.DataBase;
import br.com.marcogorak.aceleraedu.dominio.RepositorioUsuario;
import br.com.marcogorak.aceleraedu.dominio.entidades.Aula;
import br.com.marcogorak.aceleraedu.dominio.entidades.Curso;
import br.com.marcogorak.aceleraedu.dominio.entidades.Materia;
import br.com.marcogorak.aceleraedu.dominio.entidades.Usuario;
import br.com.marcogorak.aceleraedu.util.MessageBox;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{



    //Declaração de Atributos
    JSONArray jsonArrayData;
    ArrayList<HashMap<String, String>> arraylistMaterias;
    ListView listview;
    MateriasAdapter adapterListViewMaterias;

    JSONArray jsonArrayAulas;
    ArrayList<HashMap<String, String>> arraylistAulas;
    ListView listviewAulas;
    AulasAdapter adapterListViewAulas;

    public FloatingActionButton actionButton;
    public FloatingActionMenu actionMenu;
    public SubActionButton buttonCadAula;
    public SubActionButton buttonCadMateria;
    public SubActionButton buttonCadCurso;

    public Intent it;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    View listHeaderView;

    ProgressBar progressBar;

    Usuario usuario;
    RepositorioUsuario repositorioUsuario;

    //Criando o Objeto da classe DataBase
    private DataBase dataBase;
    private SQLiteDatabase conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent it = getIntent();
        Usuario usuario = it.getParcelableExtra("usuario");

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //Recuperação do drawer_layout para a classe Java
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        //Recuperando o objeto navList do tipo List view para a classe Java
        listview = (ListView) findViewById(R.id.navList);

        //Recuperando o listViewAulas
        listviewAulas = (ListView)findViewById(R.id.listviewAulas);

        LayoutInflater inflater = getLayoutInflater();
        listHeaderView = inflater.inflate(R.layout.nav_header, null, false);
        listview.addHeaderView(listHeaderView, null, false);

        new listarMaterias().execute();
        //new listarAulas().execute();



        //Execução do Drawer que a Thread popula
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);




        int codNivel = usuario.getNivelUsuario();

        TextView txtwebsite = (TextView)findViewById(R.id.website);

        txtwebsite.setText(usuario.getNomeUsuario());



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

        buttonCadAula.setOnClickListener(MainActivity.this);
        buttonCadMateria.setOnClickListener(MainActivity.this);
        buttonCadCurso.setOnClickListener(MainActivity.this);

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


    }//Fim do método onCreate

    private void exibirProgress(boolean exibir) {
        if (exibir == true) {
            progressBar.setVisibility(exibir ? View.VISIBLE : View.GONE);
        } else {
            progressBar.setVisibility(exibir ? View.INVISIBLE : View.GONE);
        }
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.film_genres);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_sair) {
            sair();
        }

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public class listarAulas extends AsyncTask<String, Void, ArrayList<HashMap<String, String>>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            exibirProgress(true);
        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(String... codigo) {
            arraylistAulas = new ArrayList<HashMap<String, String>>();

            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair(Materia.TAG_CODMATERIA, codigo[0]));


            try {
                Conexao conexao = new Conexao();
                jsonArrayAulas = conexao.consultar(Aula.CONSULT_URL, Aula.TAG_AULAS, params);

                for (int i = 0; i < jsonArrayAulas.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject jsonobject = null;
                    try {
                        jsonobject = jsonArrayAulas.getJSONObject(i);
                        map.put(Aula.TAG_CODAULA, jsonobject.getString(Aula.TAG_CODAULA));
                        map.put(Aula.TAG_NOMEAULA, jsonobject.getString(Aula.TAG_NOMEAULA));
                        map.put(Aula.TAG_DESCAULA, jsonobject.getString(Aula.TAG_DESCAULA));
                        map.put(Aula.TAG_URLVIDEOAULA, jsonobject.getString(Aula.TAG_URLVIDEOAULA));
                        map.put(Aula.TAG_URLDOCAULA, jsonobject.getString(Aula.TAG_URLDOCAULA));
                        map.put(Aula.TAG_URLPPTAULA, jsonobject.getString(Aula.TAG_URLPPTAULA));
                        map.put(Aula.TAG_URLEXERCAULA, jsonobject.getString(Aula.TAG_URLEXERCAULA));
                        map.put(Aula.TAG_URLTHUMBNAIL, jsonobject.getString(Aula.TAG_URLTHUMBNAIL));

                        arraylistAulas.add(map);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return arraylistAulas;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(ArrayList<HashMap<String, String>> arraylistAulas) {
            exibirProgress(false);
            if (arraylistAulas !=null) {
                //Criando um ArrayAdapter
                adapterListViewAulas = new AulasAdapter(MainActivity.this, arraylistAulas);


                //Adaptando o listView para o ArrayAdapter
                listviewAulas.setAdapter(adapterListViewAulas);

                //Caso o usuário Clicar em uma das opcões do ListView será executado esse método
                listviewAulas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        HashMap<String, String> resultp = new HashMap<String, String>();
                        resultp = (HashMap<String, String>) adapterListViewAulas.getItem(arg2);


                        Aula aula = new Aula(Long.parseLong(resultp.get(Aula.TAG_CODAULA)),
                                resultp.get(Aula.TAG_NOMEAULA),
                                resultp.get(Aula.TAG_DESCAULA),
                                resultp.get(Aula.TAG_URLVIDEOAULA),
                                resultp.get(Aula.TAG_URLDOCAULA),
                                resultp.get(Aula.TAG_URLPPTAULA),
                                resultp.get(Aula.TAG_URLEXERCAULA),
                                resultp.get(Aula.TAG_URLTHUMBNAIL));
                        Intent it;
                        it = new Intent(MainActivity.this, ActAula.class);
                        // Passando parâmetros de um Activity a outro
                        it.putExtra(Aula.TAG_AULAS, aula);
                        //startActivityForResult(it, 0);
                        startActivity(it);
                    }

                });

            } else {
                Toast.makeText(MainActivity.this, "Aulas não encontradas", Toast.LENGTH_LONG).show();

            }

        }

    }





    public class listarMaterias extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected ArrayList<HashMap<String, String>> doInBackground(Void... paramss) {

            try {
                Intent itMaterias = getIntent();
                Curso curso = itMaterias.getParcelableExtra(Curso.TAG_CURSO);
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                arraylistMaterias = new ArrayList<HashMap<String, String>>();

                String cod_curso = String.valueOf(curso.getCodCurso());
                params.add(new BasicNameValuePair(Curso.TAG_CODCURSO, cod_curso));

                Conexao conexao = new Conexao();
                jsonArrayData = conexao.consultar(Materia.CONSULT_URL, Materia.TAG_MATERIAS, params);

                for (int i = 0; i < jsonArrayData.length(); i++) {
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject jsonobject = null;
                    try {
                        jsonobject = jsonArrayData.getJSONObject(i);
                        map.put(Materia.TAG_CODMATERIA, jsonobject.getString(Materia.TAG_CODMATERIA));
                        map.put(Materia.TAG_MATERIA, jsonobject.getString(Materia.TAG_MATERIA));
                        arraylistMaterias.add(map);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                return arraylistMaterias;

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(ArrayList<HashMap<String, String>> arraylistMaterias) {
            if (arraylistMaterias !=null) {


                //Criando um ArrayAdapter
                adapterListViewMaterias = new MateriasAdapter(MainActivity.this, arraylistMaterias);
                //Adaptando o listView para o ArrayAdapter
                listview.setAdapter(adapterListViewMaterias);

                HashMap<String, String> testPosition = new HashMap<String, String>();
                testPosition = (HashMap<String, String>) adapterListViewMaterias.getItem(1);
                new listarAulas().execute(testPosition.get(Materia.TAG_CODMATERIA));


                //Caso o usuário Clicar em uma das opcões do ListView será executado esse método
                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                        HashMap<String, String> resultpo = new HashMap<String, String>();
                        resultpo = (HashMap<String, String>) adapterListViewMaterias.getItem(arg2);

                        Log.d("CODMATERIA",resultpo.get(Materia.TAG_CODMATERIA));
                        new listarAulas().execute(resultpo.get(Materia.TAG_CODMATERIA));
                        Toast.makeText(MainActivity.this, "Matéria selecionada: " + resultpo.get(Materia.TAG_MATERIA), Toast.LENGTH_LONG).show();
                        Log.d("Position", resultpo.get(Materia.TAG_MATERIA));

                    }

                });

            } else {
                Toast.makeText(MainActivity.this, "Matérias não encontradas", Toast.LENGTH_LONG).show();
            }
        }

    }




    public void sair () {

        try {
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();
            repositorioUsuario = new RepositorioUsuario(conn);
            usuario = repositorioUsuario.loginUsuario(this);
            repositorioUsuario.excluir(usuario.getCodUsuario());
            Intent it = new Intent(MainActivity.this, ActLogin.class);
            startActivity(it);
            finish();
        } catch (SQLException ex) {
            MessageBox.show(this, "Erro", "Erro ao criar o banco: " + ex.getMessage());
        }
    }


}

