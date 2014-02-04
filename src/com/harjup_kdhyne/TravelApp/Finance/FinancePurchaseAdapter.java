package com.harjup_kdhyne.TravelApp.Finance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.harjup_kdhyne.TravelApp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kyle 2.1 on 2/2/14.
 */
public class FinancePurchaseAdapter extends ArrayAdapter<FinancePurchase>
{
    private ArrayList<FinancePurchase> purchasesList;

    public FinancePurchaseAdapter(Context context, int resource, ArrayList<FinancePurchase> objects)
    {
        super(context, android.R.layout.simple_list_item_1, objects);
        purchasesList = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.finance_purchase_item, null);
        }

        //Find the purchase at the given position
        FinancePurchase purchase = getItem(position);

        //Put the item's data into the correct components
        TextView purchaseNameTextView = (TextView)convertView.findViewById(R.id.purchaseNameTextView);
        TextView purchaseDateTextView = (TextView)convertView.findViewById(R.id.purchaseDateTextView);
        TextView purchasePriceTextView = (TextView)convertView.findViewById(R.id.purchasePriceTextView);

        purchaseNameTextView.setText(purchase.getPurchaseName());
        purchaseDateTextView.setText(purchase.getPurchaseTimeStampAsString());
        purchasePriceTextView.setText(purchase.getPurchasePrice().toString());

        return convertView;

    }
}
