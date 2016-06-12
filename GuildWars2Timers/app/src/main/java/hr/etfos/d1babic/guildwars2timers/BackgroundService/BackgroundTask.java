package hr.etfos.d1babic.guildwars2timers.BackgroundService;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import hr.etfos.d1babic.guildwars2timers.Database_Handler.DBHelper;
import hr.etfos.d1babic.guildwars2timers.WorldEvent;

/**
 * Created by DominikZoran on 10.06.2016..
 */
public class BackgroundTask extends Service {

    DBHelper dbHelper;
    BackgroundPresenter backgroundPresenter;
    private ArrayList<WorldEvent> eventArray;
    private Handler handler;
    private Runnable runnable;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        dbHelper = new DBHelper(getApplicationContext());
        backgroundPresenter = new BackgroundPresenter(getApplicationContext());
        eventArray = dbHelper.getSubs();

        dbHelper.close();

        if (dbHelper != null) {
            dbHelper.close();
        }

        handler = new Handler();
        runnable = new Runnable() {

            @Override
            public void run() {
                for (int i = 0; i < eventArray.size(); i++) {
                    backgroundPresenter.RunInBackground(eventArray.get(i));
                }
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);


        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        if (handler != null && runnable != null)
            handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
