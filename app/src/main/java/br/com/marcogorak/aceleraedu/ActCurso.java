package br.com.marcogorak.aceleraedu;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import br.com.marcogorak.aceleraedu.WebService.tasks.CursosTask;
import br.com.marcogorak.aceleraedu.dominio.entidades.Usuario;

public class ActCurso extends AppCompatActivity implements View.OnClickListener {

    public Intent it;
    public ProgressBar progressBarCurso;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_curso);

        //Recuperando o listViewAulas
        progressBarCurso = (ProgressBar) findViewById(R.id.progressBarCurso);

        //Recuperando os dados do usuário autenticado
        it = getIntent();
        usuario = it.getParcelableExtra("usuario");

        new CursosTask(this, this, usuario).execute();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
    }

}
