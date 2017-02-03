package br.com.marcogorak.aceleraedu.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import br.com.marcogorak.aceleraedu.R;
import br.com.marcogorak.aceleraedu.dominio.entidades.Materia;

/**
 * Created by MarcoGorak on 23/03/16.
 */
public class MateriasAdapter extends BaseAdapter {

    //Declaração dos atributos da classe.
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> resultp = new HashMap<String, String>();

    public MateriasAdapter(Context context, ArrayList<HashMap<String, String>> arraylist) {
        this.context = context;
        data = arraylist;

    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(--position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        resultp = data.get(position);


        if (convertView == null) {
            Log.d("NGVL", "View Nova => position: " + position);
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);

            //Recuperando os objetos na interface grafica (list_item)
            holder.materia = (TextView) convertView.findViewById(R.id.expandedListItem);
            convertView.setTag(holder);
        } else {
            Log.d("NGVL", "View existente => position: "+ position);
            holder = (ViewHolder)convertView.getTag();
        }


        //View itemView = inflater.inflate(R.layout.list_item, parent, false);
        holder.materia.setText(resultp.get(Materia.TAG_MATERIA));

        return convertView;
    }
    static class ViewHolder {
        TextView materia;
    }
}
