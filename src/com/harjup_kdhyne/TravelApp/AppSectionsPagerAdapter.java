package com.harjup_kdhyne.TravelApp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.harjup_kdhyne.TravelApp.Finance.FinanceContainerFragment;
import com.harjup_kdhyne.TravelApp.Notes.NotesContainerFragment;
import com.harjup_kdhyne.TravelApp.Translation.TranslationContainerFragment;

/**
 * Created by Paul on 2/8/14.
 * Holds initialization logic for each of the ViewPager's tabs
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
                return new FinanceContainerFragment();
            case 1:
                return new TranslationContainerFragment();
            case 2:
                return new NotesContainerFragment();
            default:

                break;
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position)
        {
            case 0:
                return "Finances";
            case 1:
                return "Translation";
            case 2:
                return "Notes";
            default:
                return "";
        }
    }
}
