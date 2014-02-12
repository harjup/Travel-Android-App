package com.harjup_kdhyne.TravelApp.Finance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.harjup_kdhyne.TravelApp.R;

import java.util.ArrayList;

/**
 * Created by Kyle 2.1 on 2/2/14.
 */
public class PurchaseAdapter extends ArrayAdapter<Purchase>
{
    private ArrayList<Purchase> purchasesList;

    private TextView purchaseNameTextView;
    private TextView purchaseDateTextView;
    private TextView purchasePriceTextView;

    public PurchaseAdapter(Context context, int resource, ArrayList<Purchase> objects)
    {
        super(context, android.R.layout.simple_list_item_1, objects);
        purchasesList = objects;

    }

    //TODO: This doesn't quite match up with what noteAdapter is doing. Might want to look into this
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.finance_purchase_item, null);
        }

        //Find the purchase at the given position
        Purchase purchase = getItem(position);

        //Put the item's data into the correct components
        if(convertView!= null)
        {
            purchaseNameTextView = (TextView)convertView.findViewById(R.id.purchaseNameTextView);
            purchaseDateTextView = (TextView)convertView.findViewById(R.id.purchaseDateTextView);
            purchasePriceTextView = (TextView)convertView.findViewById(R.id.purchasePriceTextView);

            if (purchaseNameTextView != null)
                purchaseNameTextView.setText(purchase.getPurchaseName());

            if (purchaseDateTextView != null)
                purchaseDateTextView.setText(purchase.getPurchaseDateAsString());

            if (purchasePriceTextView != null)
                purchasePriceTextView.setText(purchase.getPurchasePrice().toString());
        }

        return convertView;

    }
}
