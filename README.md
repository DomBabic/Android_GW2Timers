# Android_GW2Timers

After signing up for the course of Mobile Applications Development we were informed that, in order to get a passing grade
everyone has to develop a native mobile application that combines some of the possibilities of the mobile platform we had chosen.
As a result I have developed this application which implements some of the core mechanics that Android provide. 

App was ment as a tool to track "Meta Events" in a game "Guild Wars 2". It is not an original idea but it seemed as a fitting one
as it would provide a certain challenge while developing it. Most of the events are periodically repeating and in order to obtain
the time remaining before the event is triggered is just a matter of simple math. Those events that are not periodic were a bit of a
challenge and the only solution to the problem was to hardcode the time of day during which those events are active.

App uses ViewPager to display two fragments - one containing a ListView of all the events available in the game, other containing a
ListView of the events user has subscribed to. Note: by subscribing to an event, app is instructed to inform the user once the tracked
event is about to start.
All data is stored within local database which is created upon starting the application for the first time. Though it most certainly
is not the best aproach to the data storing problem it works as intended, perhaps in the future revisions of the code I will include
external database.
App was programmed so that it would continue working in the background, tracking only subscribed events. Again, the solution might
not be resource friendly per se and the alternative would be to use AlarmManager to trigger notifications when prompted.

All in all, with the time I was given to develop an app, the result is satisfactory. I did pass the course with flying colors and most
importantly working wwith an Android has sparked an interesting in improving my skills with the aformentioned platform.
