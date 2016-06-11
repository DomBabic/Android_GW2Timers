package hr.etfos.d1babic.guildwars2timers.Database_Handler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import hr.etfos.d1babic.guildwars2timers.R;
import hr.etfos.d1babic.guildwars2timers.WorldEvent;

/**
 * Created by DominikZoran on 19.05.2016..
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    //Database name, table name
    private static final String DATABASE_NAME = "event_base";
    private static final String TABLE_EVENTS = "events";
    //Table row elements
    private static final String KEY_ID = "_id";
    private static final String KEY_EVENT_TITLE = "event_title";
    private static final String KEY_ICON = "iconID";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_DESCRIPTION = "description";
    //Timer time interval, timer shift
    private static final String KEY_TIMER = "timer";
    private static final String KEY_SHIFT = "timer_shift";
    //Number of times event has occurred
    private static final String KEY_NO_OCCURRENCES = "occurrences";
    private static final String KEY_SUBSCRIBED = "subscribed";

    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_EVENT_TITLE + " TEXT," + KEY_ICON + " INTEGER," + KEY_LOCATION + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_TIMER + " TEXT," + KEY_SHIFT + " TEXT," + KEY_NO_OCCURRENCES + " INTEGER," + KEY_SUBSCRIBED + " INTEGER" + ")";
        db.execSQL(CREATE_EVENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_IF = "DROP TABLE IF EXISTS " + TABLE_EVENTS;
        db.execSQL(DROP_IF);
        onCreate(db);
        populateTable();
    }

    //Method used in filling ListView in main tab
    public ArrayList<WorldEvent> getEvents() {
        ArrayList<WorldEvent> events = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_EVENTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                WorldEvent event = new WorldEvent(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8));
                events.add(event);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return events;
    }

    //Methods used to populate database first time the app is run
    public void addEvent(WorldEvent event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_TITLE, event.getEventTitle());             //Displayed
        values.put(KEY_ICON, event.getIconID());                        //Displayed
        values.put(KEY_LOCATION, event.getEventLocation());             //Displays in expandable list
        values.put(KEY_DESCRIPTION, event.getEventDescription());       //Displays in expandable list
        values.put(KEY_TIMER, event.getTimer());                        //Completed a function for timer functionality
        values.put(KEY_SHIFT, event.getTimerShift());                   //Used as a basis for timer offset
        values.put(KEY_NO_OCCURRENCES, event.getOccurrence());          //TODO: Unnecessary, remove key
        values.put(KEY_SUBSCRIBED, event.getSubscribed());              //Completed functionality

        db.insert(TABLE_EVENTS, KEY_EVENT_TITLE, values);
        db.close();
    }

    public void populateTable() {
        //Taidha
        WorldEvent populate = new WorldEvent("Admiral Taidha Covington", R.drawable.taidha, "Bloodtide Coast", "Kill Admiral Taidha Covington is a level 50 group event that occurs on Laughing Gull Island. It is the final event of the area's The Campaign Against Taidha Covington meta event.", "00:00", "03:00", 0, 0);
        addEvent(populate);
        //Svanir
        populate = new WorldEvent("Svanir Shaman Chief", R.drawable.svanir, "Wayfarer Foothills", "Kill the Svanir shaman chief to break his control over the ice elemental is a level 10 event in Wayfarer Foothills. Upon completion, a large chest spawns.", "00:15", "02:00", 0, 0);
        addEvent(populate);
        //Megadestroyer
        populate = new WorldEvent("Megadestroyer", R.drawable.megadestroyer, "Mount Maelstrom", "Kill the megadestroyer before it blows everyone up is a level 66 group event in Mount Maelstrom. It is the final event of the meta event The Battle for Mount Maelstrom.", "00:30", "03:00", 0, 0);
        addEvent(populate);
        //Fire Elemental
        populate = new WorldEvent("Fire Elemental", R.drawable.elemental, "Metrica Province", "Destroy the fire elemental created from chaotic energy fusing with the C.L.E.A.N. 5000's energy core is a level 15 group event that occurs in Thaumanova Reactor.", "00:45", "02:00", 0, 0);
        addEvent(populate);
        //The Shatterer
        populate = new WorldEvent("The Shatterer", R.drawable.shatterer, "Blazeridge Steppes", "Slay the Shatterer is a group event that occurs in the Lowland Burns. It occurs as the final part of the meta event Kralkatorrik's Legacy.", "01:00", "03:00", 0, 0);
        addEvent(populate);
        //Great Jungle Wurm
        populate = new WorldEvent("Great Jungle Wurm", R.drawable.greatwurm, "Caledon Forest", "Defeat the great jungle wurm is a level 15 group event that occurs in Wychmire Swamp. It is the last part of The Battle for Wychmire Swamp, and a Swamp Chest will spawn upon completion.", "01:15", "02:00", 0, 0);
        addEvent(populate);
        //Modniir Ulgoth
        populate = new WorldEvent("Modniir Ulgoth", R.drawable.modniir, "Harathi Hinterlands", "Defeat Ulgoth the Modniir and his minions is a level 43 group event in Modniir Gorge.Defeat Ulgoth the Modniir and his minions is a level 43 group event in Modniir Gorge.", "01:30", "03:00", 0, 0);
        addEvent(populate);
        //Shadow Behemoth
        populate = new WorldEvent("Shadow Behemoth", R.drawable.behemoth, "Queensdale", "Defeat the shadow behemoth is a level 15 event that takes place in Godslost Swamp. While being fought, underworld portals appear, spawning Shades and Aatxes.", "01:45", "02:00", 0, 0);
        addEvent(populate);
        //Golem Mark II
        populate = new WorldEvent("Golem Mark II", R.drawable.golem, "Mount Maelstrom", "Defeat the Inquest's golem Mark II is a level 68 event that occurs in Whitland Flats.", "02:00", "03:00", 0, 0);
        addEvent(populate);
        //Claw of Jormag
        populate = new WorldEvent("Claw of Jormag", R.drawable.jormag, "Frostgorge Sound", "Defeat the Claw of Jormag is a level 80 event in Frostwalk Tundra. It is the last event in Breaking the Claw of Jormag where players face down the Claw of Jormag. Upon the dragon's defeat, the Frost Chest will spawn for players.", "02:30", "03:00", 0, 0);
        addEvent(populate);
        //Tequatl the Sunless
        populate = new WorldEvent("Tequatl the Sunless", R.drawable.tequatl, "Sparkfly Fen", "Defeat Tequatl the Sunless is a level 65 group event that occurs on the Splintered Coast in Sparkfly Fen. The event begins with Tequatl the Sunless flying out of the water, eventually landing in front of the player and beginning combat.", "00:00", null, 0, 0);
        addEvent(populate);
        //Evolved Jungle Wurm
        populate = new WorldEvent("Triple Trouble", R.drawable.evolvedwurm, "Bloodtide Coast", "Triple Trouble is a meta event that takes place in the Bloodtide Coast.", "01:00", null, 0, 0);
        addEvent(populate);
        //Karka Queen
        populate = new WorldEvent("Karka Queen", R.drawable.karka, "Southsun Cove", "The Legendary Karka Queen is a world boss in Southsun Cove. When its event triggers, players are given 15 minutes to reclaim 4 settlements (if those settlements are contested by karka invasions or crazed wildlife) and kill the Queen. Once all of them are controlled by the Lionguard, the Karka Queen spawns.", "02:00", null, 0, 0);
        addEvent(populate);
    }

    //Methods required for Event Subscriptions: Add and remove subscription, get subscription list
    public void setSubscriptions(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_EVENTS + " SET " + KEY_SUBSCRIBED + "=1" + " WHERE " + KEY_EVENT_TITLE + "='" + title +"'");
        db.close();
    }

    public void Unsub(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_EVENTS + " SET " + KEY_SUBSCRIBED + "=0" + " WHERE " + KEY_EVENT_TITLE + "='" + title +"'");
        db.close();
    }

    public ArrayList<WorldEvent> getSubs() {
        ArrayList<WorldEvent> subsArray = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(TABLE_EVENTS, null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
            do {
                if(cursor.getInt(8) == 1) {
                    WorldEvent subItem = new WorldEvent(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7), cursor.getInt(8));
                    subsArray.add(subItem);
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return subsArray;
    }

}
