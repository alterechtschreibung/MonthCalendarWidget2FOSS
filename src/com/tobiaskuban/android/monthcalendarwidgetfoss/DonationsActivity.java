package com.tobiaskuban.android.monthcalendarwidgetfoss;

/*
 * Copyright (C) 2011-2013 Dominik Schürmann <dominik@dominikschuermann.de>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/*
   Edited by Tobias Kuban www.tobiaskuban.com
 */

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import com.tobiaskuban.android.monthcalendarwidgetfoss.donation.DonationsFragment;

/**
 * import com.tobiaskuban.android.monthcalendarwidgetfoss.R;
 * import com.tobiaskuban.android.monthcalendarwidgetfoss.BuildConfig;
 */

public class DonationsActivity extends Activity {

    /**
     * Google
     */
    public final static boolean DONATIONS_GOOGLE = false;
    private static final String GOOGLE_PUBKEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjsDEWSn3gOkX/WIF4pteag9V0fSkiOTKaYyYlad5I+h3TqwDK7D3VCoJnbRUUE/JQZ27kmzhA+whG+JnL8fxLC0uj+hxyo0AAFwlFMzpcaB7rcSylQbRL2y1CVvlaO2iAxdivJSZCAOfJ8vqipRPcxKX//UvAE5Qq8Zzz7om9pIv73hbrDJsKdhoYHLSUJveRlNOzgrL1nch0xsE7gIkRcCNWakSMJHV3RBygWoA39sM89LH5lGMAgvyfrzu0q3HK/8KC9XB0X66sTVSNYM0vWzl0hNkgfrL4RrKs01A8gcFAAYmNwY9FCYndgbD1jANXwmdvgJZiVFI9e0MjTQStwIDAQAB";
    private static final String[] GOOGLE_CATALOG = new String[]{"ntpsync.donation.1",
            "ntpsync.donation.2", "ntpsync.donation.3", "ntpsync.donation.5", "ntpsync.donation.8",
            "ntpsync.donation.13"};

    /**
     * PayPal
     */
    private static final String PAYPAL_USER = "alterechtschreibung@googlemail.com";
    private static final String PAYPAL_CURRENCY_CODE = "EUR";

    /**
     * Flattr
     */
    private static final String FLATTR_PROJECT_URL = "https://github.com/alterechtschreibung";
    // FLATTR_URL without http:// !
    private static final String FLATTR_URL = "flattr.com/profile/alterechtschreibung";

    /**
     * Called when the activity is first created.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.donations_activity);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        DonationsFragment donationsFragment;
//if (BuildConfig.DONATIONS_GOOGLE) {
        if (DONATIONS_GOOGLE) {
            donationsFragment = DonationsFragment.newInstance(BuildConfig.DEBUG, true, GOOGLE_PUBKEY, GOOGLE_CATALOG,
                    getResources().getStringArray(R.array.donation_google_catalog_values), false, null, null,
                    null, false, null, null);
        } else {
            donationsFragment = DonationsFragment.newInstance(BuildConfig.DEBUG, false, null, null, null, true, PAYPAL_USER,
                    PAYPAL_CURRENCY_CODE, getString(R.string.donation_paypal_item), true, FLATTR_PROJECT_URL, FLATTR_URL);
        }

        ft.replace(R.id.donations_activity_container, donationsFragment, "donationsFragment");
        ft.commit();
    }

    /**
     * Needed for Google Play In-app Billing. It uses startIntentSenderForResult(). The result is not propagated to
     * the Fragment like in startActivityForResult(). Thus we need to propagate manually to our Fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("donationsFragment");
        if (fragment != null) {
            ((DonationsFragment) fragment).onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_about:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.action_about);
                builder.setView(View.inflate(this, R.layout.dialog_about, null));
                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
