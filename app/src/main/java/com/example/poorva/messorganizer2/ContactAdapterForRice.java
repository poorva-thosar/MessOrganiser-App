package com.example.poorva.messorganizer2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapterForRice  extends ArrayAdapter{


    List list=new ArrayList();

    public ContactAdapterForRice(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public void add(ContactsForRice object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row;
        row=convertView;
        ContactAdapterForRice.ContactHolder contactHolder;
        /////
//        row.setFocusable(false);
//        row.setFocusableInTouchMode(false);
//        row.setClickable(false);
        if(row==null)
        {
            LayoutInflater layoutInflater= (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.activity_row_layout,parent,false);
            contactHolder=new ContactAdapterForRice.ContactHolder();
            contactHolder.tx_srno=(TextView) row.findViewById(R.id.tx_srno);
            contactHolder.tx_itemName=(TextView) row.findViewById(R.id.tx_name);
            contactHolder.tx_taste=(TextView) row.findViewById(R.id.tx_taste);
            row.setTag(contactHolder);
        }
        else
        {
            contactHolder=(ContactAdapterForRice.ContactHolder)row.getTag();
        }
        ContactsForRice contactsForRice=(ContactsForRice) this.getItem(position);
        contactHolder.tx_srno.setText(contactsForRice.getSrno());
        contactHolder.tx_itemName.setText(contactsForRice.getItemName());
        contactHolder.tx_taste.setText(contactsForRice.getTaste());
        return row;
    }

    static  class ContactHolder
    {
        TextView tx_srno,tx_itemName,tx_taste;

    }


}
