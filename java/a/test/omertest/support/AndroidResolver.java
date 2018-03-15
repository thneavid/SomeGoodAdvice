package a.test.omertest.support;


import android.content.Context;

import a.test.omertest.FeedPresenter;

public class AndroidResolver implements FeedPresenter.Resolver {

    private final Context context;

    public AndroidResolver(Context context) {
        this.context = context;
    }

    @Override
    public boolean isNetworkAvailable() {
        return !NetworkUtils.isOnline(context);
    }
}
