package hr.etfos.d1babic.guildwars2timers.Timers;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import hr.etfos.d1babic.guildwars2timers.Database_Handler.DBHelper;
import hr.etfos.d1babic.guildwars2timers.R;
import hr.etfos.d1babic.guildwars2timers.Subs.SubscriptionsAdapter;
import hr.etfos.d1babic.guildwars2timers.WorldEvent;


public class TimerList extends Fragment implements TimerView, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private TimerAdapter adapter;
    private ListView TimersListView;
    private TimerPresenter mTimerPresenter;

    private Handler handler;
    private Runnable runnable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timer_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
        initPresenter();
    }

    @Override
    public void onStart() {
        super.onStart();
        initAdapter();
        mTimerPresenter.requestItemsFromDatabase();

        //notifyDataSetChanged() in loop, update timer display!
        handler = new Handler();
        runnable = new Runnable() {

            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(runnable);
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    private void initPresenter() {
        mTimerPresenter = new TimerPresenterImpl(this, new DBHelper(getActivity()));
    }

    private void initAdapter() {
        adapter = new TimerAdapter(getContext());
        TimersListView.setAdapter(adapter);
    }

    private void initList(View view) {
        TimersListView = (ListView) view.findViewById(R.id.idTimerList);
        TimersListView.setOnItemClickListener(this);
        TimersListView.setOnItemLongClickListener(this);
    }

    @Override
    public void fillAdapterWithItems(List<WorldEvent> mItemList) {
        adapter.setAdapterItems(mItemList);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView location = (TextView) view.findViewById(R.id.idTimerLocation);
        TextView description = (TextView) view.findViewById(R.id.idTimerDescription);
        Button hide = (Button) view.findViewById(R.id.idTimerHide);

        location.setVisibility(View.VISIBLE);
        description.setVisibility(View.VISIBLE);
        hide.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        TextView tv = (TextView) view.findViewById(R.id.idTimerTitle);
        String title = tv.getText().toString();

        DBHelper setSubs = new DBHelper(parent.getContext());
        setSubs.getWritableDatabase();
        setSubs.setSubscriptions(title);

        ListView lv = (ListView) getActivity().findViewById(R.id.idSubscriptions);
        SubscriptionsAdapter toUpdate = new SubscriptionsAdapter();
        lv.setAdapter(toUpdate);
        toUpdate.setAdapterItems(setSubs.getSubs());
        toUpdate.notifyDataSetChanged();

        setSubs.close();

        return true;
    }

}
