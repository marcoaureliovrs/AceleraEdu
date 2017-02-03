package br.com.marcogorak.aceleraedu;


import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.com.marcogorak.aceleraedu.WebService.Conexao;
import br.com.marcogorak.aceleraedu.WebService.JSONParser;
import br.com.marcogorak.aceleraedu.dominio.entidades.Usuario;
import br.com.marcogorak.aceleraedu.util.DateUtils;


public class ActCadUsuario extends AppCompatActivity implements OnClickListener{

    //Declaração dos atributos da Classe
    private ProgressDialog pDialog;

    //Camposresponsáveis pela tela de cadastro
    private EditText edtNome;
    private EditText edtEmail;
    private EditText edtSenha;
    private EditText edtDataNascimento;

    private RadioGroup rgSexo;
    private Button btnSalvarUsuario;
    Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_cad_usuario);

        //Recuperação dos objetos da interface gráfica
        edtNome = (EditText)findViewById(R.id.edtNome);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        edtDataNascimento = (EditText)findViewById(R.id.edtDataNascimento);

        ExibeDataListener listener = new ExibeDataListener();

        edtDataNascimento.setOnClickListener(listener);
        edtDataNascimento.setOnFocusChangeListener(listener);
        edtDataNascimento.setKeyListener(null);

        rgSexo = (RadioGroup)findViewById(R.id.rgSexo);


        btnSalvarUsuario = (Button)findViewById(R.id.btnSalvarUsuario);
        btnSalvarUsuario.setOnClickListener(this);

    }

    private void exibeData() {

        Calendar calendar = Calendar.getInstance();

        int ano = calendar.get(calendar.YEAR);
        int mes = calendar.get(calendar.MONTH);
        int dia = calendar.get(calendar.DAY_OF_MONTH);

        /*
        Instanciação do objeto dlg a partir da classe DatePickerDialog passando para o método
        construtor o Activity act_cad_usuario.xml, a instanciação direta de objeto da classe
        SelecionaDataListener, as variáveis ano, mes e dia que recebem seus dados diretamente do
        objeto Calendar.
         */
        DatePickerDialog dlg = new DatePickerDialog(this, new SelecionaDataListener() , ano, mes, dia);
        dlg.show(); //Apresentando o DatePickerDialog na tela do usuário
    }


    @Override
    public void onClick(View arg0) {
        preencheUsuario();
        new Register().execute();
    }

    public void preencheUsuario () {
        usuario = new Usuario();

        if (rgSexo.getCheckedRadioButtonId() == R.id.rbFeminino) {
            usuario.setSexoUsuario("Feminino");
            Log.d("Sexo",usuario.getSexoUsuario());

        } else if (rgSexo.getCheckedRadioButtonId() == R.id.rbMasculino) {
            usuario.setSexoUsuario("Masculino");
            Log.d("Sexo",usuario.getSexoUsuario());
        }


        usuario.setNomeUsuario(edtNome.getText().toString());
        usuario.setEmailUsuario(edtEmail.getText().toString());
        usuario.setSenhaUsuario(edtSenha.getText().toString());
        usuario.setDataNascimento(edtDataNascimento.getText().toString());
    }

    class Register extends AsyncTask<String, String, String> {
        //Pré Execução
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ActCadUsuario.this);
            pDialog.setMessage("Cadastrando, Aguarde ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        //Executando enquanto é apresentndo o processdialog
        @Override
        protected String doInBackground(String... args) {
            try {
                Conexao conexao = new Conexao();
                return conexao.cadastrar(Usuario.REGISTER_URL, usuario.paramsUsuario());

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

            pDialog.dismiss();

            if (file_url != null) {
                Toast.makeText(ActCadUsuario.this, file_url, Toast.LENGTH_LONG).show();
                finish();
            }
        }

    }

    private class ExibeDataListener  implements View.OnClickListener, View.OnFocusChangeListener {

        @Override
        public void onClick(View v) {
            exibeData();
        }

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                exibeData();
            }
        }
    }

    private class SelecionaDataListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            String dt = DateUtils.dateToString(year, monthOfYear, dayOfMonth);
            Date data = DateUtils.getDate(year, monthOfYear, dayOfMonth);

            edtDataNascimento.setText(dt);
        }
    }


}
