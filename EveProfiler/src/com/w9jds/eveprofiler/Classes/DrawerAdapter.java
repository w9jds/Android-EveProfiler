package com.w9jds.eveprofiler.Classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import com.w9jds.eveprofiler.R;

import java.util.ArrayList;

/**
 * Created by Jeremy on 5/24/13.
 */
public class DrawerAdapter extends ArrayAdapter<Bitmap>
{
    private final Context context;
    private final ArrayList<Bitmap> values;

    public DrawerAdapter(Context context, ArrayList<Bitmap> values)
    {
        super(context, R.layout.drawer_button_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.drawer_button_item, parent, false);

//          ImageButton imageButton = (ImageButton) rowView.findViewById(R.id.DrawerListItem);

        return rowView;
    }
}
