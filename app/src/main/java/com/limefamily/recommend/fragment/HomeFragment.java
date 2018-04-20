package com.limefamily.recommend.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.limefamily.recommend.BuildConfig;
import com.limefamily.recommend.R;
import com.limefamily.recommend.RecommendApplication;
import com.limefamily.recommend.adapter.HomeListAdapter;
import com.limefamily.recommend.model.HomeResponse;
import com.limefamily.recommend.restful.NewsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private HomeListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private NewsService newsService;
    public static final String NEWS_SERVER_ADDRESS = "https://oa.limefamily.cn:8894/";

    public HomeFragment() {
        super();
    }

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_page,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.app_default));
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HomeListAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initNewsRetrofit();

        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                onRefresh();
            }
        });
    }

    private void initNewsRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NEWS_SERVER_ADDRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newsService = retrofit.create(NewsService.class);
    }

    @Override
    public void onRefresh() {
        adapter.clearData();
        Call<HomeResponse> firstCall = newsService.fetchLimeNews("mb-news/list-by-tag",1,110000);
        firstCall.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getItems().size() > 0 ) {
                        adapter.setLimeNews(response.body().getItems());
                    }
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Call<HomeResponse> secondCall = newsService.fetchLimeNews("mb-news/list-by-tag",2,110000);
                            secondCall.enqueue(new Callback<HomeResponse>() {
                                @Override
                                public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body() != null && response.body().getItems().size() > 0 ) {
                                            adapter.setHotRecommend(response.body().getItems());
                                        }
                                    }else {
                                        Toast.makeText(getActivity(),getString(R.string.text_request_succeed),Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<HomeResponse> call, Throwable t) {
                                    Toast.makeText(getActivity(),getString(R.string.text_request_failed),Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();

                }else {
                    Toast.makeText(getActivity(),getString(R.string.text_request_failed),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(),getString(R.string.text_request_failed),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
