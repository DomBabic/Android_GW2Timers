package hr.etfos.d1babic.guildwars2timers.BackgroundService;

import hr.etfos.d1babic.guildwars2timers.WorldEvent;

/**
 * Created by DominikZoran on 10.06.2016..
 */
public interface BackgroundCheck {
    void RunInBackground(WorldEvent current);
    String RunInForeground(WorldEvent current);

}
