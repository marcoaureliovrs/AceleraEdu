package br.com.marcogorak.aceleraedu.convert;

import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

import br.com.marcogorak.aceleraedu.dominio.entidades.Usuario;

/**
 * Created by marco on 01/02/2017.
 *
 * Classe responsável por converser um objeto java para json para ser feito seu envio
 */

public class UsuarioConverter {
    public String converteParaJSON(Usuario usuario) {
        JSONStringer js = new JSONStringer();

        try {
            js.object().key("usuario").array();
                js.object();
                js.key("nome_usuario").value(usuario.getNomeUsuario());
                js.key("email_usuario").value(usuario.getEmailUsuario());
                js.endObject();
            js.endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return js.toString();
    }
}
