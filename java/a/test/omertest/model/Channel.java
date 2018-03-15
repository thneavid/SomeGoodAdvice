package a.test.omertest.model;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import io.realm.RealmList;
import io.realm.RealmObject;

@Root(name = "channel", strict = false)
public class Channel extends RealmObject {

    @ElementList(inline = true, name = "item")
    private RealmList<FeedItem> feedItems;

    public RealmList<FeedItem> getFeedItems() {
        return feedItems;
    }
}