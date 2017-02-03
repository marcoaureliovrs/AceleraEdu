package br.com.marcogorak.aceleraedu.WebService.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by marco on 01/02/2017.
 */

public class AulasTask extends AsyncTask<Void, Void, String>{
    private Context context;

    public AulasTask(Context context) {
        this.context = context;
    }
    @Override
    protected String doInBackground(Void... params) {
        return null;
    }

    @Override
    protected void onPostExecute(String resposta) {

        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
