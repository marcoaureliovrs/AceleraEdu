package br.com.marcogorak.aceleraedu.dominio.entidades;

import android.os.Parcel;
import android.os.Parcelable;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MarcoGorak on 04/02/2016.
 */
public class Usuario implements Parcelable {
    private long codUsuario;
    private String nomeUsuario;
    private String sexoUsuario;
    private String rgUsuario;
    private long cpfUsuario;
    private String emailUsuario;
    private String senhaUsuario;
    private int nivelUsuario;
    private String dataNascimento;

    public static final String TAG_USERNAME = "email";
    public static final String TAG_PASSWORD = "password";
    public static final String TAG_DISPLAYNAME = "nomeusuario";
    public static final String TAG_DATANASCIMENTO = "datanascimento";
    public static final String TAG_SEXO = "sexo";
    public static final String TAG_NIVELUSER = "cod_nivel_usuario";
    public static final String TAG_NOMEUSUARIO = "nome_usuario";
    public static final String TAG_EMAILUSUARIO = "email_usuario";
    public static final String TAG_SEMESTRE = "semestre";

    //Implementando constantes para uso de outras classes
    public static String TABELA = "tbl_usuario";
    public static String CODIGO = "cod_usuario";
    public static String NOME = "nome_usuario";
    public static String EMAIL = "email_usuario";
    public static String SENHA = "senha_usuario";
    public static String NIVEL = "cod_nivel_usuario";


    public static String LOGIN_URL = "http://aceleraedu.com.br/assets/php/login.php";
    public static String REGISTER_URL = "http://aceleraedu.com.br/assets/php/register.php";

    //http://192.168.0.7/~MarcoGorak/Projetos/AceleraEdu/assets/php/
    public static final String TAG_USUARIO = "usuario";
    //public static String  = "";

    public Usuario () {

    }


    public Usuario (String nomeUsuario, String emailUsuario, int nivelUsuario) {
        this.nomeUsuario = nomeUsuario;
        this.emailUsuario = emailUsuario;
        this.nivelUsuario = nivelUsuario;
    }

    protected Usuario(Parcel in) {
        codUsuario = in.readLong();
        nomeUsuario = in.readString();
        sexoUsuario = in.readString();
        rgUsuario = in.readString();
        cpfUsuario = in.readLong();
        emailUsuario = in.readString();
        senhaUsuario = in.readString();
        nivelUsuario = in.readInt();
        dataNascimento = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public long getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(long codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSexoUsuario() {
        return sexoUsuario;
    }

    public void setSexoUsuario(String sexoUsuario) {
        this.sexoUsuario = sexoUsuario;
    }

    public String getRgUsuario() {
        return rgUsuario;
    }

    public void setRgUsuario(String rgUsuario) {
        this.rgUsuario = rgUsuario;
    }

    public long getCpfUsuario() {
        return cpfUsuario;
    }

    public void setCpfUsuario(long cpfUsuario) {
        this.cpfUsuario = cpfUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public int getNivelUsuario() {
        return nivelUsuario;
    }

    public void setNivelUsuario(int nivelUsuario) {
        this.nivelUsuario = nivelUsuario;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(codUsuario);
        dest.writeString(nomeUsuario);
        dest.writeString(sexoUsuario);
        dest.writeString(rgUsuario);
        dest.writeLong(cpfUsuario);
        dest.writeString(emailUsuario);
        dest.writeString(senhaUsuario);
        dest.writeInt(nivelUsuario);
        dest.writeString(dataNascimento);
    }

    public List<NameValuePair> paramsUsuario() {

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair(TAG_USERNAME, emailUsuario));
        params.add(new BasicNameValuePair(TAG_PASSWORD, senhaUsuario));
        params.add(new BasicNameValuePair(TAG_DISPLAYNAME, nomeUsuario));
        params.add(new BasicNameValuePair(TAG_DATANASCIMENTO, dataNascimento));
        params.add(new BasicNameValuePair(TAG_SEXO, sexoUsuario));

        return params;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
