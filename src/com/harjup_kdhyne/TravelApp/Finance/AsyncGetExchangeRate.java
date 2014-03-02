package com.harjup_kdhyne.TravelApp.Finance;

import android.os.AsyncTask;
import org.openexchangerates.oerjava.Currency;
import org.openexchangerates.oerjava.OpenExchangeRates;

import java.math.BigDecimal;

/**
 * Created by Kyle 2.1 on 3/2/14.
 */
public class AsyncGetExchangeRate extends AsyncTask<Void, Integer, BigDecimal>
{
    // App ID for Open Exchange Rates API
    private static final String APP_ID = "4b0f1a4d3ed14188b6bb2b007b695e20";

    // Find exchange from USD to this currency
    private Currency currency;

    //Constructor
    public AsyncGetExchangeRate(Currency currency)
    {
        this.currency = currency;
    }

    @Override
    protected BigDecimal doInBackground(Void... voids)
    {
        OpenExchangeRates oer = OpenExchangeRates.getClient(APP_ID);

        return oer.getCurrencyValue(currency);
    }
}
