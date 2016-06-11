package hr.etfos.d1babic.guildwars2timers.BackgroundService;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import hr.etfos.d1babic.guildwars2timers.R;
import hr.etfos.d1babic.guildwars2timers.WorldEvent;

/**
 * Created by DominikZoran on 10.06.2016..
 */
public class BackgroundPresenter implements BackgroundCheck {

    private Context mContext;

    long milliseconds = 0;

    //Strings to differentiate a choice!
    String queen = "Karka Queen";
    String tequatl = "Tequatl the Sunless";
    String triple = "Triple Trouble";

    public BackgroundPresenter(Context context) {
        this.mContext = context;
    }

    @Override
    public void RunInBackground(WorldEvent current) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        long milliseconds = 0;

        if(!current.getEventTitle().equals(triple) && !current.getEventTitle().equals(tequatl) && !current.getEventTitle().equals(queen)) {

            String[] shift = current.getTimerShift().split(":");
            String[] offset = current.getTimer().split(":");
            long offsetTime = (Integer.parseInt(offset[0]) * 3600000) + (Integer.parseInt(offset[1]) * 60000);
            long shiftTime = Integer.parseInt(shift[0]) * 60 * 60 * 1000;

            milliseconds = shiftTime + 1000 - (((-offsetTime) + calendar.getTimeInMillis()) % shiftTime);

            current.setMilliseconds(milliseconds);

        }
        else if (current.getEventTitle().equals(tequatl)) {

            current.setMilliseconds(timerTequatl(calendar));
        }
        else if (current.getEventTitle().equals(triple)) {

            current.setMilliseconds(timerTriple(calendar));
        }
        else if (current.getEventTitle().equals(queen)) {

            current.setMilliseconds(timerQueen(calendar));
        }

        setNotification(current.getMilliseconds(), current);
    }

    @Override
    public String RunInForeground(WorldEvent current) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        if(!current.getEventTitle().equals(triple) && !current.getEventTitle().equals(tequatl) && !current.getEventTitle().equals(queen)) {

            String[] shift = current.getTimerShift().split(":");
            String[] offset = current.getTimer().split(":");
            long offsetTime = (Integer.parseInt(offset[0]) * 3600000) + (Integer.parseInt(offset[1]) * 60000);
            long shiftTime = Integer.parseInt(shift[0]) * 60 * 60 * 1000;

            milliseconds = shiftTime + 1000 - (((-offsetTime) + calendar.getTimeInMillis()) % shiftTime);

            current.setMilliseconds(milliseconds);

        }
        else if (current.getEventTitle().equals(tequatl)) {

            current.setMilliseconds(timerTequatl(calendar));
        }
        else if (current.getEventTitle().equals(triple)) {

            current.setMilliseconds(timerTriple(calendar));
        }
        else if (current.getEventTitle().equals(queen)) {

            current.setMilliseconds(timerQueen(calendar));
        }

        setNotification(current.getMilliseconds(), current);


        return setTimer(current.getMilliseconds());
    }

    private void setNotification(long milliseconds, WorldEvent current) {

        if((current.getMilliseconds() - (milliseconds%1000)) <= 1000 && current.getSubscribed() == 1) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                    .setContentTitle("Event starting!")
                    .setContentText(current.getEventTitle() + " event starting in " + current.getEventLocation() + "!")
                    .setSmallIcon(R.drawable.ic_action_icon)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND);

            Intent result = new Intent(mContext, this.getClass());
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, result, PendingIntent.FLAG_ONE_SHOT);
            builder.setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(current.getId(), builder.build());

            pendingIntent.cancel();
            builder.setDeleteIntent(pendingIntent);

        }
    }

    private long timerQueen(Calendar calendar) {

        long milliseconds = 0;

        long hours = calendar.getTimeInMillis() / 1000 / 3600 % 24;
        long minutes = calendar.getTimeInMillis() / 1000 / 60 % 60;
        long seconds = calendar.getTimeInMillis() / 1000 % 60;

        if(hours < 2) {                                                                                 //Countdown till 01:00
            milliseconds = 7200000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 2 && hours < 6) {                                                           //Countdown till 04:00
            milliseconds =  21600000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 6 && (hours * 3600000 + minutes * 60000) < 37800000) {                      //Countdown till 08:00
            milliseconds =  37800000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if ((hours * 3600000 + minutes * 60000) >= 37800000 && hours < 15) {                     //Countdown till 12:30
            milliseconds =  54000000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 15 && hours < 18) {                                                         //Countdown till 17:00
            milliseconds =  64800000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 18 && hours < 23) {                                                         //Countdown till 20:00
            milliseconds =  82800000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 23 && (hours * 3600000 + minutes * 60000 + seconds * 1000) < 86400000) {    //Countdown till midnight + offset
            milliseconds = 86400000 + 7200000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        }

        return milliseconds;
    }

    private long timerTriple(Calendar calendar) {

        long milliseconds = 0;

        long hours = calendar.getTimeInMillis() / 1000 / 3600 % 24;
        long minutes = calendar.getTimeInMillis() / 1000 / 60 % 60;
        long seconds = calendar.getTimeInMillis() / 1000 % 60;

        if(hours < 1) {                                                                                 //Countdown till 01:00
            milliseconds = 3600000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 1 && hours < 4) {                                                           //Countdown till 04:00
            milliseconds = 14400000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 4 && hours < 8) {                                                           //Countdown till 08:00
            milliseconds = 28800000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 8 && ((hours * 3600000 + minutes * 60000) < 45000000)) {                    //Countdown till 12:30
            milliseconds = 45000000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if ((hours * 3600000 + minutes * 60000) >= 45000000 && hours < 17) {                     //Countdown till 17:00
            milliseconds = 61200000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 17 && hours < 20) {                                                         //Countdown till 20:00
            milliseconds = 72000000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 20 && (hours * 3600000 + minutes * 60000 + seconds * 1000) < 86400000) {    //Countdown till midnight + offset
            milliseconds = 86400000 + 3600000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        }

        return milliseconds;
    }

    private long timerTequatl(Calendar calendar) {

        long milliseconds = 0;

        long hours = calendar.getTimeInMillis() / 1000 / 3600 % 24;
        long minutes = calendar.getTimeInMillis() / 1000 / 60 % 60;
        long seconds = calendar.getTimeInMillis() / 1000 % 60;

        if(hours < 3) {                                                                                 //Countdown till 03:00
            milliseconds = 10800000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if(hours >= 3 && hours < 7) {                                                            //Countdown till 07:00
            milliseconds =  25200000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if(hours >= 7 && (hours * 3600000 + minutes * 60000) < 41400000) {                       //Countdown till 11:30
            milliseconds = 41400000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if((hours * 3600000 + minutes * 60000) >= 41400000 && hours < 16) {                      //Countdown till 17:00
            milliseconds = 57600000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if(hours >= 16 && hours < 19) {                                                          //Countdown till 19:00
            milliseconds = 68400000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if(hours >= 19 && (hours * 3600000 + minutes * 60000 + seconds * 1000) < 86400000) {     //Countdown till 00:00
            milliseconds = 86400000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        }

        return milliseconds;
    }

    private String setTimer(long milliseconds) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milliseconds),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) % TimeUnit.MINUTES.toSeconds(1));

        return hms;
    }

}
