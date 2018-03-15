package a.test.omertest;


import a.test.omertest.model.Feed;
import retrofit2.Call;
import retrofit2.http.GET;

public interface Service {
    @GET("/rss/hubs/all")
    Call<Feed> getFeed();
}

