package com.w9jds.eveprofiler.ListAdapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.w9jds.eveprofiler.Objects.MailInfo;
import com.w9jds.eveprofiler.R;

import java.util.ArrayList;

/**
 * Created by Jeremy on 5/26/13.
 */
public class MailHeaderListAdapter extends ArrayAdapter<MailInfo>
{
    private final Context context;
    private final ArrayList<MailInfo> values;

    public MailHeaderListAdapter(Context context, ArrayList<MailInfo> values)
    {
        super(context, R.layout.mail_header_item, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.mail_header_item, parent, false);

        ImageView senderPic = (ImageView) rowView.findViewById(R.id.senderPortrait);
        senderPic.setImageBitmap(BitmapFactory.decodeByteArray(values.get(position).getSenderPortrait(), 0, values.get(position).getSenderPortrait().length));
        TextView senderName = (TextView) rowView.findViewById(R.id.senderName);
        senderName.setText(values.get(position).getSenderName());
        TextView mailTitle = (TextView) rowView.findViewById(R.id.MailTitle);
        mailTitle.setText(values.get(position).getTitle());
        TextView sentDate = (TextView) rowView.findViewById(R.id.Sent);
        sentDate.setText(values.get(position).getSentDate());

        return rowView;
    }
}
