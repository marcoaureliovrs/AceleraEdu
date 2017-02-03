package br.com.marcogorak.aceleraedu.util;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by MarcoGorak on 06/02/16.
 */
public class MessageBox {

    public static void showInfo (Context ctx, String title, String msg) {
        show(ctx, title, msg, android.R.drawable.ic_dialog_info);
    }

    public static void showAlert (Context ctx, String title, String msg) {
        show(ctx, title, msg, android.R.drawable.ic_dialog_alert);
    }

    public static void show (Context ctx, String title, String msg) {
        show(ctx, title, msg, 0);
    }

    public static void show (Context ctx, String title, String msg, int idIcon) {

        //Cria uma mensagem informando que houve um erro ao criar a base de dados
        AlertDialog.Builder dlg = new AlertDialog.Builder(ctx);
        dlg.setIcon(idIcon);
        dlg.setTitle(title);
        dlg.setMessage(msg);
        dlg.setNeutralButton("Ok", null);
        dlg.show();

    }
}
