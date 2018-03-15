package a.test.omertest;

import a.test.omertest.model.Feed;
import a.test.omertest.model.FeedItem;
import io.realm.RealmResults;
import retrofit2.Response;

public interface IModel {

    RealmResults<FeedItem> getItems();

    void clear();

    void saveFeed(Response<Feed> feedResponse);

    boolean isOnline();
}