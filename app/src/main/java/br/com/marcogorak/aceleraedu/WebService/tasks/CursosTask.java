package br.com.marcogorak.aceleraedu.WebService.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.marcogorak.aceleraedu.ActCurso;
import br.com.marcogorak.aceleraedu.MainActivity;
import br.com.marcogorak.aceleraedu.R;
import br.com.marcogorak.aceleraedu.WebService.Conexao;
import br.com.marcogorak.aceleraedu.adapter.CursosAdapter;
import br.com.marcogorak.aceleraedu.dominio.entidades.Curso;
import br.com.marcogorak.aceleraedu.dominio.entidades.Usuario;

/**
 * Created by marco on 01/02/2017.
 */

public class CursosTask extends AsyncTask<Void, Void, ArrayList<HashMap<String, String>>> {
    //Declarando objetos da classe
    private Context context;
    private ActCurso actcurso;
    JSONArray jsonArrayCursos;
    ArrayList<HashMap<String, String>> arraylistCursos;
    ListView listviewCursos;
    CursosAdapter cursosAdapter;
    public Intent it;
    Usuario usuario;


    public CursosTask(Context context, ActCurso actcurso, Usuario usuario) {
        this.context = context;
        this.actcurso = actcurso;
        this.usuario = usuario;
    }

    private void exibirProgress(boolean exibir) {
        if (exibir == true) {
            actcurso.progressBarCurso.setVisibility(exibir ? View.VISIBLE : View.GONE);
        } else {
            actcurso.progressBarCurso.setVisibility(exibir ? View.INVISIBLE : View.GONE);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
       exibirProgress(true);
    }

    @Override
    protected ArrayList<HashMap<String, String>> doInBackground(Void... params) {
        arraylistCursos = new ArrayList<HashMap<String, String>>();
        try {
            Conexao conexao = new Conexao();
            jsonArrayCursos = conexao.consultar(Curso.CONSULT_URL, Curso.TAG_CURSOS);

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
            cursosAdapter = new CursosAdapter(context, arraylistCursos);
            listviewCursos = (ListView)actcurso.findViewById(R.id.listviewCursos);
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
                    it = new Intent(context, MainActivity.class);
                    // Passando parâmetros de um Activity a outro
                    it.putExtra(Curso.TAG_CURSO, curso);
                    it.putExtra("usuario", usuario);
                    //startActivityForResult(it, 0);
                    context.startActivity(it);
                }
            });

        } else {
            Toast.makeText(context, "Falha ao carregar cursos", Toast.LENGTH_LONG).show();
        }
    }



}