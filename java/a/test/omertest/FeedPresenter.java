package a.test.omertest;


import android.support.annotation.NonNull;

import a.test.omertest.model.Feed;
import a.test.omertest.model.FeedItem;
import a.test.omertest.support.Constants;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class FeedPresenter implements IPresenter {

    private IView view;
    private IModel model;

    public FeedPresenter(Realm realm, Resolver resolver) {
        model = new Model(realm, resolver);
    }

    @Override
    public void onItemClick(int adapterPosition) {
        view.openItem(adapterPosition);
    }

    @Override
    public void attachView(IView view) {
        this.view = view;
        loadFeed();
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void loadFeed() {
        if (model.isOnline()) {
            setInitFinished(Constants.CODE_NETWORK_ERROR);
        } else {
            Retrofit restAdapter = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();

            Service service = restAdapter.create(Service.class);
            Call<Feed> call = service.getFeed();
            call.enqueue(new Callback<Feed>() {
                @Override
                public void onResponse(@NonNull Call<Feed> call, @NonNull Response<Feed> response) {
                    model.saveFeed(response);
                    setInitFinished(Constants.CODE_SUCCESS);
                }

                @Override
                public void onFailure(@NonNull Call<Feed> call, @NonNull Throwable t) {
                    setInitFinished(Constants.CODE_COMMON_ERROR);
                }
            });
        }
    }

    public RealmResults<FeedItem> getItems() {
        return model.getItems();
    }

    private void setInitFinished(int resultCode) {
        if (view != null) {
            if (resultCode == Constants.CODE_SUCCESS) view.showItems(getItems());
            if (resultCode == Constants.CODE_NETWORK_ERROR) view.showErrorToast();
            if (resultCode == Constants.CODE_COMMON_ERROR) view.showCommonErrorToast();
        }
    }

    @Override
    public void refreshLayoutPulled() {
        model.clear();
        loadFeed();
    }

    public interface Resolver {
        boolean isNetworkAvailable();
    }
}
