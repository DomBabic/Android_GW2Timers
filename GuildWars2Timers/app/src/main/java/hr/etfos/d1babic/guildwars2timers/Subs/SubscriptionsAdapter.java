package hr.etfos.d1babic.guildwars2timers.Subs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hr.etfos.d1babic.guildwars2timers.Database_Handler.DBHelper;
import hr.etfos.d1babic.guildwars2timers.R;
import hr.etfos.d1babic.guildwars2timers.WorldEvent;

/**
 * Created by DominikZoran on 02.06.2016..
 */
public class SubscriptionsAdapter extends BaseAdapter {

    private ArrayList<WorldEvent> subsList = new ArrayList<>();

    public void setAdapterItems(List<WorldEvent> mDataSource){
        subsList.clear();
        subsList.addAll(mDataSource);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return subsList.size();
    }

    @Override
    public Object getItem(int position) {
        return subsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final Context mContext = parent.getContext();
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.subscriptions_list_items, parent, false);
        }
        final WorldEvent current = subsList.get(position);

        if(current.getSubscribed() == 1)
        {
            ImageView icon = (ImageView)convertView.findViewById(R.id.idSubsIcon);
            TextView title = (TextView)convertView.findViewById(R.id.idSubsTitle);
            Button unsub = (Button)convertView.findViewById(R.id.idItemUnsubscribeButton);

            unsub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHelper setUnsub = new DBHelper(mContext);
                    setUnsub.getWritableDatabase();
                    setUnsub.Unsub(current.getEventTitle());

                    subsList.clear();
                    subsList = setUnsub.getSubs();
                    notifyDataSetChanged();

                    setUnsub.close();
                }
            });

            icon.setImageResource(current.getIconID());
            title.setText(current.getEventTitle());
        }

        return convertView;
    }
}
