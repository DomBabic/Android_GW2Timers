package hr.etfos.d1babic.guildwars2timers.Timers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import hr.etfos.d1babic.guildwars2timers.R;
import hr.etfos.d1babic.guildwars2timers.WorldEvent;

/**
 * Created by DominikZoran on 19.05.2016..
 */
public class TimerAdapter extends BaseAdapter {

    private final ArrayList<WorldEvent> eventList = new ArrayList<>();
    private final Context mContext;

    public TimerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setAdapterItems(List<WorldEvent> mDataSource){
        eventList.clear();
        eventList.addAll(mDataSource);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return eventList.size();
    }

    @Override
    public Object getItem(int position) {
        return eventList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.timers_list_items, parent, false);
        }

        WorldEvent current = eventList.get(position);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.idTimerImage);
        TextView title = (TextView)convertView.findViewById(R.id.idTimerTitle);
        final TextView counter = (TextView)convertView.findViewById(R.id.idTimerCounter);
        final TextView location = (TextView)convertView.findViewById(R.id.idTimerLocation);
        final TextView description = (TextView)convertView.findViewById(R.id.idTimerDescription);
        final Button hide = (Button) convertView.findViewById(R.id.idTimerHide);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location.setVisibility(View.GONE);
                description.setVisibility(View.GONE);
                hide.setVisibility(View.GONE);
            }
        });

        //Sets image source, event title, location and description
        imageView.setImageResource(current.getIconID());
        title.setText(current.getEventTitle());
        location.setText("Location: " + current.getEventLocation());
        description.setText("Description:\n" + current.getEventDescription());

        //Strings to differentiate a choice!
        String queen = "Karka Queen";
        String tequatl = "Tequatl the Sunless";
        String triple = "Triple Trouble";

        //Calendar instance based upon Coordinate Universal Time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

        //Time unit holder
        long milliseconds = 0;

        //Functionality for setting countdown value of each timer within list
        if(!current.getEventTitle().equals(triple) && !current.getEventTitle().equals(tequatl) && !current.getEventTitle().equals(queen)) {

            String[] shift = current.getTimerShift().split(":");
            String[] offset = current.getTimer().split(":");
            long offsetTime = (Integer.parseInt(offset[0]) * 3600000) + (Integer.parseInt(offset[1]) * 60000);
            long shiftTime = Integer.parseInt(shift[0]) * 60 * 60 * 1000;

            milliseconds = shiftTime - (((-offsetTime) + calendar.getTimeInMillis()) % shiftTime);

            current.setMilliseconds(milliseconds);
            counter.setText(setTimer(milliseconds));

        }
        else if (current.getEventTitle().equals(tequatl)) {

            current.setMilliseconds(timerTequatl(calendar));
            counter.setText(setTimer(timerTequatl(calendar)));

        }
        else if (current.getEventTitle().equals(triple)) {

            current.setMilliseconds(timerTriple(calendar));
            counter.setText(setTimer(timerTriple(calendar)));

        }
        else if (current.getEventTitle().equals(queen)) {

            current.setMilliseconds(timerQueen(calendar));
            counter.setText(setTimer(timerQueen(calendar)));

        }

        //Sort ListView items by remaining time, check notification status!
        Collections.sort(eventList, new compareMilliseconds());
        setNotification(counter, current);

        return convertView;

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
            milliseconds =  37799000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if ((hours * 3600000 + minutes * 60000) >= 37800000 && hours < 15) {                     //Countdown till 12:30
            milliseconds =  54000000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 15 && hours < 18) {                                                         //Countdown till 17:00
            milliseconds =  64800000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 18 && hours < 23) {                                                         //Countdown till 20:00
            milliseconds =  82799000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 23 && (hours * 3600000 + minutes * 60000 + seconds * 1000) < 86400000) {    //Countdown till midnight + offset
            milliseconds = 86399000 + 7200000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        }

        return milliseconds;
    }

    private long timerTriple(Calendar calendar) {

        long milliseconds = 0;

        long hours = calendar.getTimeInMillis() / 1000 / 3600 % 24;
        long minutes = calendar.getTimeInMillis() / 1000 / 60 % 60;
        long seconds = calendar.getTimeInMillis() / 1000 % 60;

        if(hours < 1) {                                                                                 //Countdown till 01:00
            milliseconds = 3599000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 1 && hours < 4) {                                                           //Countdown till 04:00
            milliseconds = 14399000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 4 && hours < 8) {                                                           //Countdown till 08:00
            milliseconds = 28799000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 8 && ((hours * 3600000 + minutes * 60000) < 45000000)) {                    //Countdown till 12:30
            milliseconds = 44999000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if ((hours * 3600000 + minutes * 60000) >= 48600000 && hours < 17) {                     //Countdown till 17:00
            milliseconds = 61199000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 17 && hours < 20) {                                                         //Countdown till 20:00
            milliseconds = 71999000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if (hours >= 20 && (hours * 3600000 + minutes * 60000 + seconds * 1000) < 86400000) {    //Countdown till midnight + offset
            milliseconds = 86399000 + 3600000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        }

        return milliseconds;
    }

    private long timerTequatl(Calendar calendar) {

        long milliseconds = 0;

        long hours = calendar.getTimeInMillis() / 1000 / 3600 % 24;
        long minutes = calendar.getTimeInMillis() / 1000 / 60 % 60;
        long seconds = calendar.getTimeInMillis() / 1000 % 60;

        if(hours < 3) {                                                                                 //Countdown till 03:00
            milliseconds = 10799000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if(hours >= 3 && hours < 7) {                                                            //Countdown till 07:00
            milliseconds =  25199000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if(hours >= 7 && (hours * 3600000 + minutes * 60000) < 41400000) {                       //Countdown till 11:30
            milliseconds = 41399000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if((hours * 3600000 + minutes * 60000) >= 41400000 && hours < 16) {                      //Countdown till 17:00
            milliseconds = 57599000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if(hours >= 16 && hours < 19) {                                                          //Countdown till 19:00
            milliseconds = 68399000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        } else if(hours >= 19 && (hours * 3600000 + minutes * 60000 + seconds * 1000) < 86400000) {     //Countdown till 00:00
            milliseconds = 86399000 - (hours * 3600000 + minutes * 60000 + seconds * 1000);
        }

        return milliseconds;
    }

    private String setTimer(long milliseconds) {
        String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(milliseconds),
                TimeUnit.MILLISECONDS.toMinutes(milliseconds) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) % TimeUnit.MINUTES.toSeconds(1));

        return hms;
    }

    private void setNotification(TextView counter, WorldEvent current) {

        if(counter.getText().equals("00:00:01") && current.getSubscribed() == 1) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext)
                    .setContentTitle("Event starting!")
                    .setContentText(current.getEventTitle() + " event starting in " + current.getEventLocation() + "!")
                    .setSmallIcon(R.drawable.ic_action_icon)
                    .setDefaults(NotificationCompat.DEFAULT_SOUND);

            Intent result = new Intent(mContext, this.getClass());
            PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, result, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(current.getId(), builder.build());

            Log.i("Notified!", " " + current.getEventTitle());
        }

    }

    public class compareMilliseconds implements Comparator<WorldEvent> {

        @Override
        public int compare(WorldEvent lhs, WorldEvent rhs) {
            return  lhs.getMilliseconds() < rhs.getMilliseconds() ? -1 : lhs.getMilliseconds() == rhs.getMilliseconds() ? 0 : 1;
        }
    }

}
