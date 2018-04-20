package com.limefamily.recommend.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import com.limefamily.recommend.R;
import com.limefamily.recommend.adapter.WebAdapter;
import com.limefamily.recommend.fragment.HomeFragment;
import com.limefamily.recommend.model.News;
import com.limefamily.recommend.restful.NewsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private int newsId;
    private WebAdapter webAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private final int DEFAULT_ID = -1;
    public static final String KEY_NEWS_ID = "key_news_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        swipeRefreshLayout = findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.app_default));
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        webAdapter = new WebAdapter();
        recyclerView.setAdapter(webAdapter);

        newsId = getIntent().getIntExtra(KEY_NEWS_ID,DEFAULT_ID);

        if ( newsId == DEFAULT_ID ) {
            showError();
        }

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
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
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        showError();
                    }else {
                        if (TextUtils.isEmpty(response.body().getNews_content())) {
                            showError();
                        }else {
                            webAdapter.setData(response.body());
                            recyclerView.smoothScrollToPosition(0);
                        }
                    }
                }else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                showError();
            }
        });
    }

    private void showError() {
        finish();
        Toast.makeText(WebActivity.this,
                getString(R.string.text_fatch_news_detail_failed),Toast.LENGTH_SHORT).show();
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onRefresh() {
        fetchNewsContent();
    }
}
