package br.com.marcogorak.aceleraedu.dominio.entidades;

import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.marcogorak.aceleraedu.WebService.JSONParser;

/**
 * Created by MarcoGorak on 04/02/2016.
 */
public class Materia {

    private long codMateria;
    private String nomeMateria;
    private String descricaoCurso;
    private long codCursoRelacionado;
    private long dependenciaMateria;
    List<String> materias;


    //Declaração de Objetos
    JSONObject jsonobject;
    ArrayList<HashMap<String, String>> arrayListMaterias;
    HashMap<String, String> map;
    JSONParser jsonParser = new JSONParser();

    //http://10.76.14.145/~MarcoGorak/Projetos/kubyx/assets/php/
    public static String CONSULT_URL = "http://aceleraedu.com.br/assets/php/listarMateria.php";
    public static String REGISTER_URL = "http://aceleraedu.com.br/assets/php/cadMateria.php";


    //http://192.168.0.7/~MarcoGorak/Projetos/AceleraEdu/assets/php/
    public static final String TAG_CODMATERIA = "codmateria";
    public static final String TAG_MATERIAS = "materias";
    public static final String TAG_MATERIA = "materia";
    public static final String TAG_DESCRICAO = "descricao";
    public static final String TAG_QTDMATERIAS = "quantidade_materias";


    ArrayAdapter<String> adpMateria;



    public long getCodMateria() {
        return codMateria;
    }

    public void setCodMateria(long codMateria) {
        this.codMateria = codMateria;
    }

    public String getNomeMateria() {
        return nomeMateria;
    }

    public void setNomeMateria(String nomeMateria) {
        this.nomeMateria = nomeMateria;
    }

    public String getDescricaoCurso() {
        return descricaoCurso;
    }

    public void setDescricaoCurso(String descricaoCurso) {
        this.descricaoCurso = descricaoCurso;
    }

    public long getCodCursoRelacionado() {
        return codCursoRelacionado;
    }

    public void setCodCursoRelacionado(long codCursoRelacionado) {
        this.codCursoRelacionado = codCursoRelacionado;
    }

    public long getDependenciaMateria() {
        return dependenciaMateria;
    }

    public void setDependenciaMateria(long dependenciaMateria) {
        this.dependenciaMateria = dependenciaMateria;
    }


    public ArrayAdapter<String> listarMaterias(JSONArray jsonArrayData) throws JSONException {


        for (int i = 0; i < jsonArrayData.length(); i++) {
            JSONObject jsonobject = null;

            try {
                jsonobject = jsonArrayData.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                adpMateria.add(jsonobject.getString(TAG_MATERIA));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return adpMateria;
    }
}
