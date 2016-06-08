package hr.etfos.d1babic.guildwars2timers.Subs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import hr.etfos.d1babic.guildwars2timers.Database_Handler.DBHelper;
import hr.etfos.d1babic.guildwars2timers.R;
import hr.etfos.d1babic.guildwars2timers.WorldEvent;


public class Subscriptions extends Fragment implements SubscriptionsView{

    private SubscriptionsAdapter adapter;
    private ListView SubsListView;
    private SubscriptionPresenter mSubsPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_subscriptions, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initList(view);
        initPresenter();
    }

    private void initPresenter() {
        mSubsPresenter = new SubscriptionsPresenterImpl(this, new DBHelper(getActivity()));
    }

    @Override
    public void onStart() {
        super.onStart();
        initAdapter();
        mSubsPresenter.requestSubscriptionsFromDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initAdapter() {
        adapter = new SubscriptionsAdapter();
        SubsListView.setAdapter(adapter);
    }

    private void initList(View v) {
        SubsListView = (ListView)v.findViewById(R.id.idSubscriptions);
    }

    @Override
    public void fillAdapterWithSubscriptions(List<WorldEvent> mItemList) {
        adapter.setAdapterItems(mItemList);
    }

}
