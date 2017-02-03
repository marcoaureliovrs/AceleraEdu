package br.com.marcogorak.aceleraedu.dominio.entidades;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by MarcoGorak on 04/02/2016.
 */

public class Aula implements Parcelable {
    private long codAula;
    private String nomeAula;
    private String descricaoAula;
    private String urlVideoAula;
    private String urlDocumentoAula;
    private String urlApresentacaoAula;
    private String urlexercaula;
    private String urlthumbnail;
    private long codMateriaRelacionada;

    //Declaração das constantes que serão usadas no WebService
    public static final String TAG_AULAS = "aulas";

    public static final String TAG_CODAULA = "codaula";

    public static final String TAG_NOMEAULA = "nomeaula";
    public static final String TAG_DESCAULA = "descaula";
    public static final String TAG_URLVIDEOAULA = "urlvideoaula";
    public static final String TAG_URLDOCAULA = "urldocaula";
    public static final String TAG_URLPPTAULA = "urlpptaula";
    public static final String TAG_URLEXERCAULA = "urlexercaula";
    public static final String TAG_URLTHUMBNAIL = "urlthumbnail";

    public static final String TAG_AULA = "aula";
    public static final String TAG_MATERIARELACIONADA = "materiarelacionada";
    public static final String TAG_QTDAULAS = "quantidade_aulas";

    public static String REGISTER_URL = "http://aceleraedu.com.br/assets/php/cadAula.php";
    public static String CONSULT_URL = "http://aceleraedu.com.br/assets/php/listarAulas.php";

    //http://aceleraedu.com.br/assets/php/listarMateria.php

    //public static final String TAG_ = "";

    public Aula (long codAula, String nomeAula, String descricaoAula, String urlVideoAula, String urlDocumentoAula, String urlApresentacaoAula, String urlexercaula, String urlthumbnail) {

        this.codAula = codAula;
        this.nomeAula = nomeAula;
        this.descricaoAula = descricaoAula;
        this.urlVideoAula = urlVideoAula;
        this.urlDocumentoAula = urlDocumentoAula;
        this.urlApresentacaoAula = urlApresentacaoAula;
        this.urlexercaula = urlexercaula;
        this.urlthumbnail = urlthumbnail;
    }


    protected Aula(Parcel in) {
        codAula = in.readLong();
        nomeAula = in.readString();
        descricaoAula = in.readString();
        urlVideoAula = in.readString();
        urlDocumentoAula = in.readString();
        urlApresentacaoAula = in.readString();
        codMateriaRelacionada = in.readLong();
    }

    public static final Creator<Aula> CREATOR = new Creator<Aula>() {
        @Override
        public Aula createFromParcel(Parcel in) {
            return new Aula(in);
        }

        @Override
        public Aula[] newArray(int size) {
            return new Aula[size];
        }
    };

    public long getCodAula() {
        return codAula;
    }

    public void setCodAula(long codAula) {
        this.codAula = codAula;
    }

    public String getNomeAula() {
        return nomeAula;
    }

    public void setNomeAula(String nomeAula) {
        this.nomeAula = nomeAula;
    }

    public String getDescricaoAula() {
        return descricaoAula;
    }

    public void setDescricaoAula(String descricaoAula) {
        this.descricaoAula = descricaoAula;
    }

    public String getUrlVideoAula() {
        return urlVideoAula;
    }

    public void setUrlVideoAula(String urlVideoAula) {
        this.urlVideoAula = urlVideoAula;
    }

    public String getUrlDocumentoAula() {
        return urlDocumentoAula;
    }

    public void setUrlDocumentoAula(String urlDocumentoAula) {
        this.urlDocumentoAula = urlDocumentoAula;
    }

    public String getUrlApresentacaoAula() {
        return urlApresentacaoAula;
    }

    public void setUrlApresentacaoAula(String urlApresentacaoAula) {
        this.urlApresentacaoAula = urlApresentacaoAula;
    }

    public long getCodMateriaRelacionada() {
        return codMateriaRelacionada;
    }

    public void setCodMateriaRelacionada(long codMateriaRelacionada) {
        this.codMateriaRelacionada = codMateriaRelacionada;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(codAula);
        dest.writeString(nomeAula);
        dest.writeString(descricaoAula);
        dest.writeString(urlVideoAula);
        dest.writeString(urlDocumentoAula);
        dest.writeString(urlApresentacaoAula);
        dest.writeLong(codMateriaRelacionada);
    }

    public String getUrlexercaula() {
        return urlexercaula;
    }

    public void setUrlexercaula(String urlexercaula) {
        this.urlexercaula = urlexercaula;
    }

    public String getUrlthumbnail() {
        return urlthumbnail;
    }

    public void setUrlthumbnail(String urlthumbnail) {
        this.urlthumbnail = urlthumbnail;
    }
}
