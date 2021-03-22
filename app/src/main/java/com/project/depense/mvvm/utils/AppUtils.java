
package com.project.depense.mvvm.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.mindorks.framework.mvvm.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by lamkadmi on 17/11/19.
 */

public final class AppUtils {

    private AppUtils() {
        // This class is not publicly instantiable
    }

    public static void openPlayStoreForApp(Context context) {
        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_market_link) + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context
                            .getResources()
                            .getString(R.string.app_google_play_store_link) + appPackageName)));
        }
    }

    public static String getDate(int pYear, int pMonthOfYear, int pDayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(5, pDayOfMonth);
        calendar.set(2, pMonthOfYear);
        calendar.set(1, pYear);
        Date datejour = new Date();
        Calendar c2 = GregorianCalendar.getInstance();
        c2.setTime(datejour);
        calendar.set(11, c2.get(11));
        calendar.set(12, c2.get(12));
        calendar.set(13, c2.get(13));
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String data = df.format(calendar.getTime());
        return data;
    }

    public static Date getDateFromString(String pDate) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = format.parse(pDate);
            return date;
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * Retourne le texte d'une date au format standard dd/MM/yyyy
     * @param pDate : java.util.Date
     * @return le texte de la date
     */
    public static String getDateToString(Date pDate) {
        return pDate == null ? null : new SimpleDateFormat("dd/MM/yyyy").format(pDate);
    }
}
