package com.limefamily.recommend.activity;

import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.limefamily.recommend.R;
import com.limefamily.recommend.RecommendApplication;
import com.limefamily.recommend.adapter.RecommendAttendantAdapter;
import com.limefamily.recommend.adapter.RecommendCustomerAdapter;
import com.limefamily.recommend.model.Attendant;
import com.limefamily.recommend.model.Customer;
import com.limefamily.recommend.restful.RecommendService;
import com.limefamily.recommend.widget.GoogleCircleProgressView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RecommendListActivity extends AppCompatActivity implements OnRefreshListener, OnLoadMoreListener {

    private int recommendType;
    private int currentPage = 0;
    private RecyclerView recyclerView;
    private SwipeToLoadLayout swipeToLoadLayout;
    private final int DEFAULT_RECOMMEND_TYPE = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final String FROM_TIME = "1970-01-01";
    private RecommendCustomerAdapter recommendCustomerAdapter;
    private RecommendAttendantAdapter recommendAttendantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_list);
        initData();
        initView();
    }

    private void initData() {
        recommendType = getIntent().getIntExtra(RecommendNavigationActivity.KEY_RECOMMEND_TYPE,DEFAULT_RECOMMEND_TYPE);
        if (recommendType == DEFAULT_RECOMMEND_TYPE) {
            Toast.makeText(this,getString(R.string.text_unknown_error),Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void initView() {
        View headerView = findViewById(R.id.swipe_refresh_header);
        View footerView = findViewById(R.id.swipe_load_more_footer);
        GoogleCircleProgressView headerGoogleCircleProgressView = headerView.findViewById(R.id.googleProgress);
        headerGoogleCircleProgressView.setColorSchemeResources(R.color.app_default);
        GoogleCircleProgressView footerGoogleCircleProgressView = footerView.findViewById(R.id.googleProgress);
        footerGoogleCircleProgressView.setColorSchemeResources(R.color.app_default);
        swipeToLoadLayout = findViewById(R.id.swipeToLoadLayout);
        recyclerView = findViewById(R.id.swipe_target);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (recommendType == RecommendNavigationActivity.TYPE_CUSTOMER) {
            recommendCustomerAdapter = new RecommendCustomerAdapter(this);
            recyclerView.setAdapter(recommendCustomerAdapter);
        }else if (recommendType == RecommendNavigationActivity.TYPE_ATTENDANT) {
            recommendAttendantAdapter = new RecommendAttendantAdapter(this);
            recyclerView.setAdapter(recommendAttendantAdapter);
        }else {
            Toast.makeText(this,getString(R.string.text_unknown_error),Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }
            }
        });
        swipeToLoadLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(true);
            }
        });
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onLoadMore() {
        ++currentPage;
        fetchRecommendList();
    }

    @Override
    public void onRefresh() {
        reset();
        fetchRecommendList();
    }

    private void reset(){
        if (recommendType == RecommendNavigationActivity.TYPE_CUSTOMER) {
            recommendCustomerAdapter.clearData();
        }else {
            recommendAttendantAdapter.clearData();
        }
        currentPage = 0;
        swipeToLoadLayout.setRefreshEnabled(true);
        swipeToLoadLayout.setLoadMoreEnabled(true);
    }

    private void completeRefresh() {
        if (currentPage == 0){
            swipeToLoadLayout.setRefreshing(false);
        }else {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    private void fetchRecommendList() {
        String token = RecommendApplication.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            Toast.makeText(this,getString(R.string.text_unknown_error),Toast.LENGTH_SHORT).show();
            return;
        }
        String tokenPrefix = getString(R.string.text_prefix_token);
        Retrofit retrofit = RecommendApplication.getInstance().getRetrofit();
        RecommendService recommendService = retrofit.create(RecommendService.class);
        if (recommendType == RecommendNavigationActivity.TYPE_CUSTOMER) {
            Call<List<Customer>> call = recommendService.customerList(String.format("%s %s",tokenPrefix,token),currentPage,DEFAULT_PAGE_SIZE,FROM_TIME);
            call.enqueue(new Callback<List<Customer>>() {
                @Override
                public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                    completeRefresh();
                    if (response.isSuccessful()) {
                        if (response.body() == null) {
                            if (recommendCustomerAdapter.getItemCount() == 0) {
                                showError();
                            }
                            swipeToLoadLayout.setLoadingMore(false);
                            swipeToLoadLayout.setLoadMoreEnabled(false);
                        }else {
                            if (response.body().size() == 0 ) {
                                if (recommendCustomerAdapter.getItemCount() == 0 ) {
                                    showEmpty();
                                }
                                swipeToLoadLayout.setLoadingMore(false);
                                swipeToLoadLayout.setLoadMoreEnabled(false);
                            }else {
                                if (response.body().size() > 0 && response.body().size() < DEFAULT_PAGE_SIZE) {
                                    recommendCustomerAdapter.appendData(response.body());
                                    swipeToLoadLayout.setLoadingMore(false);
                                    swipeToLoadLayout.setLoadMoreEnabled(false);
                                }else if (response.body().size() == DEFAULT_PAGE_SIZE) {
                                    recommendCustomerAdapter.appendData(response.body());
                                    swipeToLoadLayout.setLoadingMore(true);
                                    swipeToLoadLayout.setLoadMoreEnabled(true);
                                }
                            }
                        }
                    }else {
                        showError();
                        swipeToLoadLayout.setLoadingMore(false);
                        swipeToLoadLayout.setLoadMoreEnabled(false);
                    }
                }

                @Override
                public void onFailure(Call<List<Customer>> call, Throwable t) {
                    completeRefresh();
                    showError();
                    swipeToLoadLayout.setLoadingMore(false);
                    swipeToLoadLayout.setLoadMoreEnabled(false);
                }
            });
        }else if (recommendType == RecommendNavigationActivity.TYPE_ATTENDANT) {
            Call<List<Attendant>> call = recommendService.attendantList(String.format("%s %s",tokenPrefix,token),currentPage,DEFAULT_PAGE_SIZE,FROM_TIME);
            call.enqueue(new Callback<List<Attendant>>() {
                @Override
                public void onResponse(Call<List<Attendant>> call, Response<List<Attendant>> response) {
                    completeRefresh();
                    if (response.isSuccessful()) {
                        if (response.body() == null) {
                            if (recommendAttendantAdapter.getItemCount() == 0) {
                                showError();
                            }
                            swipeToLoadLayout.setLoadingMore(false);
                            swipeToLoadLayout.setLoadMoreEnabled(false);
                        }else {
                            if (response.body().size() == 0 ) {
                                if (recommendAttendantAdapter.getItemCount() == 0 ) {
                                    showEmpty();
                                }
                                swipeToLoadLayout.setLoadingMore(false);
                                swipeToLoadLayout.setLoadMoreEnabled(false);
                            }else {
                                if (response.body().size() > 0 && response.body().size() < DEFAULT_PAGE_SIZE) {
                                    recommendAttendantAdapter.appendData(response.body());
                                    swipeToLoadLayout.setLoadingMore(false);
                                    swipeToLoadLayout.setLoadMoreEnabled(false);
                                }else if (response.body().size() == DEFAULT_PAGE_SIZE) {
                                    recommendAttendantAdapter.appendData(response.body());
                                    swipeToLoadLayout.setLoadingMore(true);
                                    swipeToLoadLayout.setLoadMoreEnabled(true);
                                }
                            }
                        }
                    }else {
                        showError();
                        swipeToLoadLayout.setLoadingMore(false);
                        swipeToLoadLayout.setLoadMoreEnabled(false);
                    }
                }

                @Override
                public void onFailure(Call<List<Attendant>> call, Throwable t) {
                    completeRefresh();
                    showError();
                    swipeToLoadLayout.setLoadingMore(false);
                    swipeToLoadLayout.setLoadMoreEnabled(false);
                }
            });
        }
    }

    private void showEmpty() {

    }

    private void showError() {

    }

}
