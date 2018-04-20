package com.limefamily.recommend.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.limefamily.recommend.R;
import com.limefamily.recommend.model.News;
import com.limefamily.recommend.util.FormatUtil;

/**
 * Created by liuhao on 2018/4/20.
 */

public class WebAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private News news;
    public static final int NEWS_MAIN = 1002;
    public static final int NEWS_HEADER = 1001;

    public static final String HTML_START = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "\t<title></title>\n" +
            "\t<meta charset=\"utf-8\" name=\"viewport\" content=\"width=device-width,initial-scale=1,shrink-to-fit=no\">\n" +
            "\t<style type=\"text/css\">\n" +
            "\t\timg {\n" +
            "\t\t\twidth: 100%;\n" +
            "\t\t\theight: auto;\n" +
            "\t\t}\n" +
            "\t</style>\n" +
            "</head>\n" +
            "<body>";

    public static final String HTML_END = "</body>\n" +
            "</html>";

    public WebAdapter() {
        this.news = new News();
    }


    public void setData(News news) {
        this.news.setNews_title(news.getNews_title());
        this.news.setNews_pub_time(news.getNews_pub_time());
        this.news.setNews_img(news.getNews_img());
        this.news.setNews_content(news.getNews_content());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == NEWS_HEADER) {
            return new HeaderViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.layout_item_web_header,parent,false));
        }
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_item_web_main,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (news != null) {
            if (position == 0 ) {
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                if (!TextUtils.isEmpty(news.getNews_title())) {
                    headerViewHolder.newsTitleTextView.setText(news.getNews_title());
                }
                headerViewHolder.newsPublishTimeTextView.setText(FormatUtil.getInstance()
                        .timestamp2DateString(news.getNews_pub_time()));
                if (!TextUtils.isEmpty(news.getNews_img())) {
                    headerViewHolder.coverDraweeView.setImageURI(news.getNews_img());
                }

            }else {
                MainViewHolder mainViewHolder = (MainViewHolder) holder;
                mainViewHolder.webView.loadData(String.format(
                        "%s%s%s",HTML_START,news.getNews_content(),HTML_END),
                        "text/html; charset=UTF-8", null);
            }
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 ) {
            return NEWS_HEADER;
        }
        return NEWS_MAIN;
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView coverDraweeView;
        TextView newsTitleTextView,newsPublishTimeTextView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            newsTitleTextView = itemView.findViewById(R.id.tv_news_title);
            coverDraweeView = itemView.findViewById(R.id.iv_cover);
            newsPublishTimeTextView = itemView.findViewById(R.id.tv_news_pub_time);
        }
    }

    class MainViewHolder extends RecyclerView.ViewHolder {

        WebView webView;

        public MainViewHolder(View itemView) {
            super(itemView);
            webView = itemView.findViewById(R.id.web_view);
        }
    }

}
