package com.harjup_kdhyne.TravelApp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.harjup_kdhyne.TravelApp.Finance.FinancePurchaseListFragment;
import com.harjup_kdhyne.TravelApp.Finance.FinanceSummaryFragment;
import com.harjup_kdhyne.TravelApp.Notes.NotesContainerFragment;
import com.harjup_kdhyne.TravelApp.Notes.NotesListFragment;

/**
 * Created by Paul on 2/8/14.
 * TODO: Write short summary of class
 */
public class AppSectionsPagerAdapter extends FragmentPagerAdapter
{
    public AppSectionsPagerAdapter(FragmentManager fragmentManager)
    {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i)
        {
            case 0:
                return new FinanceSummaryFragment();
            case 1:
                return new NotesContainerFragment();
            default:

                break;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position)
        {
            case 0:
                return "Finances";
            case 1:
                return "Notes";
            default:
                return "";
        }
    }
}
