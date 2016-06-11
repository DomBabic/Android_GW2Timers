package hr.etfos.d1babic.guildwars2timers.Timers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hr.etfos.d1babic.guildwars2timers.BackgroundService.BackgroundPresenter;
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
        BackgroundPresenter bkgdPresenter = new BackgroundPresenter(mContext);

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

        //Sets image source, event title, location, description and timer views
        imageView.setImageResource(current.getIconID());
        title.setText(current.getEventTitle());
        location.setText("Location: " + current.getEventLocation());
        description.setText("Description:\n" + current.getEventDescription());
        counter.setText(bkgdPresenter.RunInForeground(current));

        //Sort ListView items based on time remaining till event!
        Collections.sort(eventList, new compareMilliseconds());

        return convertView;

    }

    public class compareMilliseconds implements Comparator<WorldEvent> {

        @Override
        public int compare(WorldEvent lhs, WorldEvent rhs) {
            return  lhs.getMilliseconds() < rhs.getMilliseconds() ? -1 : lhs.getMilliseconds() == rhs.getMilliseconds() ? 0 : 1;
        }
    }

}
