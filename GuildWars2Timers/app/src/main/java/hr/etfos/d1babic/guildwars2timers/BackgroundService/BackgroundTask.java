package hr.etfos.d1babic.guildwars2timers.BackgroundService;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;

import java.util.ArrayList;

import hr.etfos.d1babic.guildwars2timers.Database_Handler.DBHelper;
import hr.etfos.d1babic.guildwars2timers.WorldEvent;

/**
 * Created by DominikZoran on 10.06.2016..
 */
public class BackgroundTask extends IntentService {

    private Handler handler;
    Runnable runnable;
    private ArrayList<WorldEvent> eventArray;
    private BackgroundPresenter backgroundPresenter;

    public BackgroundTask() {
        super("BackgroundTask");
    }

    //TODO: Jebat mu mater i skontat koji kurac se događa

    @Override
    protected void onHandleIntent(Intent intent) {
        DBHelper helper = new DBHelper(getApplicationContext());
        eventArray = helper.getEvents();
        helper.close();

        backgroundPresenter = new BackgroundPresenter(getApplicationContext());

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

    }
}
