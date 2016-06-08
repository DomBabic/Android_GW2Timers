package hr.etfos.d1babic.guildwars2timers.Subs;

import hr.etfos.d1babic.guildwars2timers.Database_Handler.DBHelper;

/**
 * Created by DominikZoran on 02.06.2016..
 */
public class SubscriptionsPresenterImpl implements SubscriptionPresenter {
    private final SubscriptionsView mSubsView;
    private final DBHelper mDBHelper;

    public SubscriptionsPresenterImpl(SubscriptionsView mSubsView, DBHelper mDBHelper) {
        this.mSubsView = mSubsView;
        this.mDBHelper = mDBHelper;
    }

    @Override
    public void requestSubscriptionsFromDatabase() {
        mSubsView.fillAdapterWithSubscriptions(mDBHelper.getSubs());
    }
}
