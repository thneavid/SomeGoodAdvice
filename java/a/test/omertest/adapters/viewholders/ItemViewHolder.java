package a.test.omertest.adapters.viewholders;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import a.test.omertest.R;
import a.test.omertest.model.FeedItem;

public class ItemViewHolder extends com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder {
    private TextView pubDate;
    private TextView title;
    private TextView link;
    private ImageView img;
    private TextView description;
    private Context context;

    public ItemViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        title = itemView.findViewById(R.id.tvTitle);
        pubDate = itemView.findViewById(R.id.tvPubDate);
        link = itemView.findViewById(R.id.tvLink);
        img = itemView.findViewById(R.id.ivImage);
        description = itemView.findViewById(R.id.tvDescription);
    }

    public void setHolder(FeedItem item) {
        title.setText(item.getTitle());
        pubDate.setText(item.getPubDate());
        link.setText(item.getLink());
        description.setText(item.getDescription());

        Pattern p = Pattern.compile("<img\\ssrc\\s=\\s.*?");
        Matcher m = p.matcher(item.getDescription());
        while (m.find()) {
            String src = m.group().substring(11);
            Log.e("|||||||||||", m.group());
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

            RequestOptions ro = new RequestOptions();
            ro.override(width, height);
            Glide.with(context)
                    .load(src)
                    .apply(ro)
                    .into(img);
        }
    }
}