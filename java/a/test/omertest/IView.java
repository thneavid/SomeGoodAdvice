package a.test.omertest;

import a.test.omertest.model.FeedItem;
import io.realm.RealmResults;

public interface IView {

    void showItems(RealmResults<FeedItem> items);

    void openItem(int position);

    void showErrorToast();

    void showCommonErrorToast();
}
