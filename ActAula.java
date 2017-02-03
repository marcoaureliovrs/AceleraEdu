package br.com.marcogorak.aceleraedu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import br.com.marcogorak.aceleraedu.dominio.entidades.Aula;

public class ActAula extends AppCompatActivity implements View.OnClickListener{

    private MediaController media;
    private VideoView videoview;
    private TextView txtNomeAula;
    private TextView txtDescricaoAula;
    Button btnApostila;
    Button btnApresentacao;
    Button btnExercicios;

    Uri link;
    Aula aula;
    Intent chamaNavegador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_aula);

        txtNomeAula = (TextView) findViewById(R.id.txtNomeAulaThumb);
        txtDescricaoAula = (TextView) findViewById(R.id.descricaoAulaThumb);
        btnApostila =(Button) findViewById(R.id.btnApostila);
        btnApresentacao =(Button) findViewById(R.id.btnApresentacao);
        btnExercicios =(Button) findViewById(R.id.btnExericios);

        //Reprodução do vídeo no aplicativo
        videoview = (VideoView)findViewById(R.id.vvAula);

        Intent it = getIntent();
        aula  = it.getParcelableExtra(Aula.TAG_AULAS);

        //setando o nome da aula
        txtNomeAula.setText(aula.getNomeAula());
        txtDescricaoAula.setText(aula.getDescricaoAula());

        btnApostila.setOnClickListener(this);
        btnApresentacao.setOnClickListener(this);
        btnExercicios.setOnClickListener(this);

        new BackgroundAsyncTask().execute(aula.getUrlVideoAula());

    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (btnApostila.getId() == id) {
            link = Uri.parse(aula.getUrlDocumentoAula());
            chamaNavegador = new Intent(Intent.ACTION_VIEW, link);
            startActivity(chamaNavegador);

        } else if (btnApresentacao.getId() == id) {
            link = Uri.parse(aula.getUrlApresentacaoAula());
            chamaNavegador = new Intent(Intent.ACTION_VIEW, link);
            startActivity(chamaNavegador);

        } else if (btnExercicios.getId() == id) {
            link = Uri.parse(aula.getUrlexercaula());
            chamaNavegador = new Intent(Intent.ACTION_VIEW, link);
            startActivity(chamaNavegador);
        }
    }


    public class BackgroundAsyncTask extends AsyncTask<String, Uri, Void> {
        ProgressDialog dialog;

        protected void onPreExecute() {
           dialog = new ProgressDialog(ActAula.this);
            dialog.setMessage("Carregando, aguarde...");
            dialog.setCancelable(true);
            dialog.show();
        }

        protected void onProgressUpdate(final Uri... uri) {

            try {

                media=new MediaController(ActAula.this);
                videoview.setMediaController(media);
                media.setPrevNextListeners(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // next button clicked

                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                media.show(10000);

                videoview.setVideoURI(uri[0]);
                videoview.requestFocus();
                videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    public void onPrepared(MediaPlayer arg0) {
                        videoview.start();
                        dialog.dismiss();
                    }
                });


            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }

        @Override
        protected Void doInBackground(String... params) {
            try {
                Uri uri = Uri.parse(params[0]);

                publishProgress(uri);
            } catch (Exception e) {
                e.printStackTrace();

            }

            return null;
        }


    }
}
