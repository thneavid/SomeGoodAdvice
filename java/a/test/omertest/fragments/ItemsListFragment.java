package a.test.omertest.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import a.test.omertest.FeedPresenter;
import a.test.omertest.IPresenter;
import a.test.omertest.IView;
import a.test.omertest.R;
import a.test.omertest.adapters.ItemsListAdapter;
import a.test.omertest.model.FeedItem;
import a.test.omertest.support.AndroidResolver;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ItemsListFragment extends Fragment implements IView, SwipeRefreshLayout.OnRefreshListener {

    private Context mContext;
    private IPresenter feedPresenter;
    private RecyclerView mRecyclerView;

    public ItemsListFragment() {
    }

    public static ItemsListFragment newInstance() {
        return new ItemsListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
        this.mContext = context;
    }

    /* Deprecated on API 23
     * Use onAttachToContext instead */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    protected void onAttachToContext(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(getActivity());
        Realm.setDefaultConfiguration(new RealmConfiguration.Builder().build());

        feedPresenter = new FeedPresenter(
                Realm.getDefaultInstance(),
                new AndroidResolver(getActivity()));
        feedPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_items_list, container, false);
        mRecyclerView = v.findViewById(R.id.mRVItemsList);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void showItems(RealmResults<FeedItem> items) {
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        ItemsListAdapter listAdapter = new ItemsListAdapter(
                mContext,
                items,
                feedPresenter);
        mRecyclerView.setAdapter(listAdapter);
    }

    @Override
    public void openItem(int position) {
        ItemFragment itemFragment = ItemFragment.newInstance(position);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, itemFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showErrorToast() {
        Toast.makeText(mContext, "Network Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCommonErrorToast() {
        Toast.makeText(mContext, "Common Error", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        feedPresenter.refreshLayoutPulled();
    }
}
