package br.com.marcogorak.aceleraedu.dominio.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MarcoGorak on 04/02/2016.
 */
public class Curso implements Parcelable {

    private long codCurso;
    private String nomeCurso;
    private String descricaoCurso;

    public static String CONSULT_URL = "http://aceleraedu.com.br/assets/php/listarCurso.php";
    public static String REGISTER_URL = "http://aceleraedu.com.br/assets/php/cadCurso.php";

    public static final String TAG_CURSOS = "cursos";
    public static final String TAG_CURSO = "curso";
    public static final String TAG_DESCRICAO = "descricao";
    public static final String TAG_CURSORELACIONADO = "cursorelacionado";
    public static final String TAG_CODCURSO = "codcurso";
    

    public Curso () {

    }


    protected Curso(Parcel in) {
        codCurso = in.readLong();
        nomeCurso = in.readString();
        descricaoCurso = in.readString();
    }

    public static final Creator<Curso> CREATOR = new Creator<Curso>() {
        @Override
        public Curso createFromParcel(Parcel in) {
            return new Curso(in);
        }

        @Override
        public Curso[] newArray(int size) {
            return new Curso[size];
        }
    };

    public long getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(long codCurso) {
        this.codCurso = codCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public String getDescricaoCurso() {
        return descricaoCurso;
    }

    public void setDescricaoCurso(String descricaoCurso) {
        this.descricaoCurso = descricaoCurso;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(codCurso);
        dest.writeString(nomeCurso);
        dest.writeString(descricaoCurso);
    }
}
