package com.harjup_kdhyne.TravelApp.Finance;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.harjup_kdhyne.TravelApp.R;

/**
 * Created by Kyle 2.1 on 1/29/14.
 */
public class FinanceSummary extends Fragment
{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View theView = inflater.inflate(R.layout.finance_summary, container, false);

        return theView;
    }
}
