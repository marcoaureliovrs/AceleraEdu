package br.com.marcogorak.aceleraedu.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.marcogorak.aceleraedu.R;
import br.com.marcogorak.aceleraedu.dominio.entidades.Aula;
import br.com.marcogorak.aceleraedu.dominio.entidades.Curso;
import br.com.marcogorak.aceleraedu.dominio.entidades.Materia;
import br.com.marcogorak.aceleraedu.dominio.entidades.Usuario;

/**
 * Created by MarcoGorak on 12/05/16.
 */
public class CursosAdapter extends BaseAdapter{

    //Declaração dos atributos da classe.
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();



    public CursosAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        //Objetos utilizados exclusivamente neste método.
        ViewHolder holder = new ViewHolder();

        resultp = data.get(position);

        if (convertView == null) {
            Log.d("NGVL", "View Nova => position: " + position);
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_curso, null);


            //Recuperando os objetos na interface grafica (item_list_curso)
            holder.txtNomeCurso = (TextView) convertView.findViewById(R.id.txtNomeCurso);
            holder.txtSemestre = (TextView) convertView.findViewById(R.id.txtSemestre);
            holder.txtNumeroAulas = (TextView) convertView.findViewById(R.id.txtNumeroAulas);
            holder.txtNumeroMaterias = (TextView) convertView.findViewById(R.id.txtNumeroMaterias);
            convertView.setTag(holder);
        } else {
            Log.d("NGVL", "View existente => position: "+ position);
            holder = (ViewHolder)convertView.getTag();
        }

        //poulando os itens do listview
        //Picasso.with(context).load(Uri.parse(String.valueOf(resultp.get(Aula.TAG_URLTHUMBNAIL)))).into(holder.thumbnail);
        holder.txtNomeCurso.setText(resultp.get(Curso.TAG_CURSO));
        //holder.txtSemestre.setText(resultp.get(Usuario.TAG_SEMESTRE));
        //holder.txtNumeroAulas.setText(resultp.get(Aula.TAG_QTDAULAS));
        //holder.txtNumeroMaterias.setText(resultp.get(Materia.TAG_QTDMATERIAS));

        return convertView;
    }

    static class ViewHolder {
        TextView txtNomeCurso;
        TextView txtSemestre;
        TextView txtNumeroAulas;
        TextView txtNumeroMaterias;
    }
}
