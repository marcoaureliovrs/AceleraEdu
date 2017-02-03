package br.com.marcogorak.aceleraedu.dominio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import br.com.marcogorak.aceleraedu.dominio.entidades.Usuario;

/**
 * Created by MarcoGorak on 18/04/16.
 */
public class RepositorioUsuario {

    private SQLiteDatabase conn;



    private ContentValues preencheContentValues(Usuario usuario) {

        ContentValues values = new ContentValues();
        values.put(Usuario.NOME, usuario.getNomeUsuario() );
        values.put(Usuario.EMAIL, usuario.getEmailUsuario() );
        values.put(Usuario.SENHA, usuario.getSenhaUsuario() );
        values.put(Usuario.NIVEL, usuario.getNivelUsuario() );

        return values;
    }

    public void inserir (Usuario usuario) {
        ContentValues values = preencheContentValues(usuario);
        conn.insertOrThrow(Usuario.TABELA, null, values);

    }

    public void alterar (Usuario usuario) {
        ContentValues values = preencheContentValues(usuario);
        conn.update(Usuario.TABELA, values, "_id = ? ", new String[]{String.valueOf(usuario.getCodUsuario())});

    }

    public void excluir(long id) {
        conn.delete(Usuario.TABELA, "cod_usuario = ? ", new String[]{String.valueOf(id)});
    }



    public RepositorioUsuario (SQLiteDatabase conn) {
        this.conn = conn;
    }

    public Usuario loginUsuario (Context context) {
        Usuario usuario = new Usuario();

        //Faz a consulta na tabela CONTATO e armazena no objeto cursor da classe Cursor
        Cursor cursor = conn.query(Usuario.TABELA, null, null, null, null, null, null);

        //Verificando se a consulta retornou algum dado
        if (cursor.getCount() > 0 ){
            cursor.moveToFirst();
            do {

                usuario.setCodUsuario(cursor.getLong(cursor.getColumnIndex(usuario.CODIGO)));
                usuario.setNomeUsuario(cursor.getString(cursor.getColumnIndex(usuario.NOME)));
                usuario.setEmailUsuario(cursor.getString(cursor.getColumnIndex(usuario.EMAIL)));
                usuario.setSenhaUsuario(cursor.getString(cursor.getColumnIndex(usuario.SENHA)));
                usuario.setNivelUsuario(cursor.getInt(cursor.getColumnIndex(usuario.NIVEL)));

            }while(cursor.moveToNext());

        } else {
            usuario = null;
        }

        return usuario;
    }
}
