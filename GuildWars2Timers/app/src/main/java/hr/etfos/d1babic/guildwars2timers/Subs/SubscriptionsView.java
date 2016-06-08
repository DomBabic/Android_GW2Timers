package hr.etfos.d1babic.guildwars2timers.Subs;

import java.util.List;

import hr.etfos.d1babic.guildwars2timers.WorldEvent;

/**
 * Created by DominikZoran on 02.06.2016..
 */
public interface SubscriptionsView {
    void fillAdapterWithSubscriptions(List<WorldEvent> mItemList);
}
