package br.com.marcogorak.aceleraedu.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MarcoGorak on 04/02/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    //Método Construtor
    public DataBase (Context context) {super(context, "bdApkEdu", null, 1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Chamada do método que contém o script para acriação das tabelas
        db.execSQL(ScriptSQL.getCreateDataBase());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
