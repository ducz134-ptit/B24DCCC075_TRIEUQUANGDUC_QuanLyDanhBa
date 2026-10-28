package com.example.quanlydanhba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {

    private final Context context;
    private final ArrayList<Contact> contacts;

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override public int getCount() { return contacts.size(); }
    @Override public Contact getItem(int position) { return contacts.get(position); }
    @Override public long getItemId(int position) { return position; }

    private static class ViewHolder {
        TextView tvAvatar, tvName, tvPhone, tvGroup, tvStar;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder h;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false);
            h = new ViewHolder();
            h.tvAvatar = convertView.findViewById(R.id.tvAvatar);
            h.tvName = convertView.findViewById(R.id.tvName);
            h.tvPhone = convertView.findViewById(R.id.tvPhone);
            h.tvGroup = convertView.findViewById(R.id.tvGroup);
            h.tvStar = convertView.findViewById(R.id.tvStar);
            convertView.setTag(h);
        } else {
            h = (ViewHolder) convertView.getTag();
        }

        Contact c = contacts.get(position);
        h.tvAvatar.setText(c.getInitial());
        h.tvName.setText(c.getName());
        h.tvPhone.setText(c.getPhone());
        h.tvGroup.setText(c.getGroup());
        h.tvStar.setVisibility(c.isFavorite() ? View.VISIBLE : View.GONE);
        return convertView;
    }
}
