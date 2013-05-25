package com.w9jds.eveprofiler.Classes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import com.w9jds.eveprofiler.R;

import java.util.ArrayList;

/**
 * Created by Jeremy on 5/24/13.
 */
public class DrawerListAdapter extends ArrayAdapter<Drawable>
{
    private final Context context;
    private final ArrayList<Drawable> values;

    public DrawerListAdapter(Context context, ArrayList<Drawable> values)
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

        ImageButton imageButton = (ImageButton) rowView.findViewById(R.id.DrawerListItem);
        imageButton.setImageDrawable(values.get(position));

        return rowView;
    }
}
