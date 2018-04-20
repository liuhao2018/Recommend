package com.limefamily.recommend.activity;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.limefamily.recommend.R;
import com.limefamily.recommend.fragment.HomeFragment;
import com.limefamily.recommend.model.News;
import com.limefamily.recommend.restful.NewsService;
import com.limefamily.recommend.util.FormatUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EasyWebActivity extends BaseAgentWebActivity {

    private int newsId;
    private SimpleDraweeView coverDraweeView;
    private TextView titleTextView;
    private TextView newsTitleTextView,newsPublishTimeTextView;
    private final int DEFAULT_ID = -1;
    public static final String KEY_NEWS_ID = "key_news_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("");
        titleTextView = findViewById(R.id.toolbar_title);
        newsTitleTextView = findViewById(R.id.tv_news_title);
        coverDraweeView = findViewById(R.id.iv_cover);
        newsPublishTimeTextView = findViewById(R.id.tv_news_pub_time);
        this.setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyWebActivity.this.finish();
            }
        });

        newsId = getIntent().getIntExtra(KEY_NEWS_ID,DEFAULT_ID);

        if ( newsId == DEFAULT_ID ) {

            showError();
        }

        fetchNewsContent();
    }

    private void fetchNewsContent() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HomeFragment.NEWS_SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NewsService newsService = retrofit.create(NewsService.class);
        Call<News> call = newsService.view("mb-news/view",newsId);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        showError();
                    }else {
                        if (TextUtils.isEmpty(response.body().getNews_content())) {
                            showError();
                        }else {
                            updateView(response.body());
                        }
                    }
                }else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                showError();
            }
        });
    }

    private void updateView(News news) {
        if (!TextUtils.isEmpty(news.getNews_title())) {
            newsTitleTextView.setText(news.getNews_title());
        }
        newsPublishTimeTextView.setText(FormatUtil.getInstance().timestamp2DateString(news.getNews_pub_time()));
        if (!TextUtils.isEmpty(news.getNews_img())) {
            coverDraweeView.setImageURI(news.getNews_img());
        }
        if (!TextUtils.isEmpty(news.getNews_content())) {
            mAgentWeb.getUrlLoader().loadData(news.getNews_content(),
                    "text/html; charset=UTF-8", null);
        }
    }

    private void showError() {
        finish();
        Toast.makeText(EasyWebActivity.this,
                getString(R.string.text_fatch_news_detail_failed),Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    protected ViewGroup getAgentWebParent() {
        return (ViewGroup) findViewById(R.id.container);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }

        if (mAgentWeb != null && mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void setTitle(WebView view, String title) {
        super.setTitle(view, title);
        titleTextView.setText(title);
    }

    @Override
    protected int getIndicatorColor() {
        return getResources().getColor(R.color.app_default);
    }

    @Override
    protected int getIndicatorHeight() {
        return 1;
    }

    @Nullable
    @Override
    protected String getUrl() {
        return "http://www.limefamily.com";
    }

}
