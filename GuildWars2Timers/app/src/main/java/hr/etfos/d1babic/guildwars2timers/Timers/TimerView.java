package hr.etfos.d1babic.guildwars2timers.Timers;

import java.util.List;

import hr.etfos.d1babic.guildwars2timers.WorldEvent;

/**
 * Created by DominikZoran on 19.05.2016..
 */
public interface TimerView {
    void fillAdapterWithItems(List<WorldEvent> mItemList);
}
