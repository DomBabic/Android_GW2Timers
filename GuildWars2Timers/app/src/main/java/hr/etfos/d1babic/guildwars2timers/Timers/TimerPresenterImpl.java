package hr.etfos.d1babic.guildwars2timers.Timers;

import hr.etfos.d1babic.guildwars2timers.Database_Handler.DBHelper;

/**
 * Created by DominikZoran on 19.05.2016..
 */
public class TimerPresenterImpl implements TimerPresenter {
    private final TimerView mTimerView;
    private final DBHelper mDBHelper;

    public TimerPresenterImpl(TimerView mTimerView, DBHelper mDBHelper) {
        this.mTimerView = mTimerView;
        this.mDBHelper = mDBHelper;
    }

    @Override
    public void requestItemsFromDatabase() {
        mTimerView.fillAdapterWithItems(mDBHelper.getEvents());
    }
}
