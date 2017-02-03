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

/**
 * Created by MarcoGorak on 03/04/16.
 */

public class AulasAdapter extends BaseAdapter {

    //Declaração dos atributos da classe.
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();



    public AulasAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
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

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View itemView = inflater.inflate(R.layout.item_list_thumbnail_aula, parent, false);
        resultp = data.get(position);

        if (convertView == null) {
            Log.d("NGVL", "View Nova => position: " + position);
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_thumbnail_aula, null);


            //Recuperando os objetos na interface grafica (item_list_thumbnail_aula)
            holder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnail);
            holder.txtNomeAula = (TextView) convertView.findViewById(R.id.txtNomeAulaThumb);
            holder.txtDescricaoAula = (TextView) convertView.findViewById(R.id.descricaoAula);
            convertView.setTag(holder);
        } else {
            Log.d("NGVL", "View existente => position: "+ position);
            holder = (ViewHolder)convertView.getTag();
        }

        //poulando os itens do listview
        Picasso.with(context).load(Uri.parse(String.valueOf(resultp.get(Aula.TAG_URLTHUMBNAIL)))).into(holder.thumbnail);
        holder.txtNomeAula.setText(resultp.get(Aula.TAG_NOMEAULA));
        holder.txtDescricaoAula.setText(resultp.get(Aula.TAG_DESCAULA));


        return convertView;
    }

    static class ViewHolder {
        TextView txtNomeAula;
        TextView txtDescricaoAula;
        ImageView thumbnail;
    }
}
