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
 * Created by Kyle 2.1 on 2/2/14
 * A purchase adapter for the items in the purchases list
 */
public class PurchaseAdapter extends ArrayAdapter<Purchase>
{
    public PurchaseAdapter(Context context, int resource, ArrayList<Purchase> objects)
    {
        super(context, resource, objects);
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
        Purchase purchase = getItem(position);

        //Put the item's data into the correct components
        if(convertView!= null)
        {
            TextView purchaseNameTextView = (TextView)convertView.findViewById(R.id.purchaseNameTextView);
            TextView purchaseDateTextView = (TextView)convertView.findViewById(R.id.purchaseDateTextView);
            TextView purchasePriceTextView = (TextView)convertView.findViewById(R.id.purchasePriceTextView);
            TextView purchaseCurrencyTextView = (TextView)convertView.findViewById(R.id.purchaseCurrencyTextView);

            if (purchaseNameTextView != null)
                purchaseNameTextView.setText(purchase.getPurchaseName());

            if (purchaseDateTextView != null)
                purchaseDateTextView.setText(purchase.getDateString());

            if (purchasePriceTextView != null)
                purchasePriceTextView.setText(purchase.getPurchasePrice());

            if (purchaseCurrencyTextView != null)
                purchaseCurrencyTextView.setText(purchase.getPaidCurrency().toString());
        }

        return convertView;

    }
}
