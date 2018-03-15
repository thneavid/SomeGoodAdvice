package a.test.omertest;

import a.test.omertest.model.FeedItem;
import io.realm.RealmResults;

public interface IPresenter {

    void onItemClick(int adapterPosition);

    void attachView(IView view);

    void detachView();

    void loadFeed();

    void refreshLayoutPulled();

    RealmResults<FeedItem> getItems();
}
