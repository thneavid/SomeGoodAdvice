package a.test.omertest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import a.test.omertest.IPresenter;
import a.test.omertest.R;
import a.test.omertest.adapters.viewholders.ItemViewHolder;
import a.test.omertest.model.FeedItem;
import io.realm.RealmResults;

public class ItemsListAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private LayoutInflater lInflater;
    private RealmResults<FeedItem> feedItems;
    private IPresenter feedPresenter;

    public ItemsListAdapter(Context context, RealmResults<FeedItem> feedItems, IPresenter feedPresenter) {
        this.feedItems = feedItems;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.feedPresenter = feedPresenter;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = lInflater.inflate(R.layout.view_rv_item, parent, false);
        final ItemViewHolder viewHolder = new ItemViewHolder(view, view.getContext());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedPresenter.onItemClick(viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.setHolder(feedItems.get(position));
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}