package com.limefamily.recommend.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.limefamily.recommend.R;
import com.limefamily.recommend.RecommendApplication;
import com.limefamily.recommend.adapter.RebateListAdapter;
import com.limefamily.recommend.logic.ApplicationBus;
import com.limefamily.recommend.logic.FragmentPageChangeEvent;
import com.limefamily.recommend.model.Rebate;
import com.limefamily.recommend.restful.RebateService;
import com.limefamily.recommend.widget.GoogleCircleProgressView;
import com.squareup.otto.Subscribe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by liuhao on 2018/4/16.
 */

public class RebateFragment extends Fragment implements OnRefreshListener,OnLoadMoreListener {

    private RecyclerView recyclerView;
    private SwipeToLoadLayout swipeToLoadLayout;
    private RebateListAdapter rebateListAdapter;

    private int currentRebateType;
    private int customerPage = 0;
    private int attendantPage = 0;
    public static final int TYPE_CUSTOMER = 0;
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final String KEY_REBATE_TYPE = "key_rebate_type";

    public static RebateFragment newInstance(int rebateType) {
        RebateFragment rebateFragment = new RebateFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_REBATE_TYPE, rebateType);
        rebateFragment.setArguments(bundle);
        return rebateFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rebate_page, null);
        View headerView = view.findViewById(R.id.swipe_refresh_header);
        View footerView = view.findViewById(R.id.swipe_load_more_footer);
        GoogleCircleProgressView headerGoogleCircleProgressView = headerView.findViewById(R.id.googleProgress);
        headerGoogleCircleProgressView.setColorSchemeResources(R.color.app_default);
        GoogleCircleProgressView footerGoogleCircleProgressView = footerView.findViewById(R.id.googleProgress);
        footerGoogleCircleProgressView.setColorSchemeResources(R.color.app_default);
        swipeToLoadLayout = view.findViewById(R.id.swipeToLoadLayout);
        recyclerView = view.findViewById(R.id.swipe_target);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        rebateListAdapter = new RebateListAdapter(getActivity());
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
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ApplicationBus.getInstance().register(this);
        currentRebateType = getArguments().getInt(KEY_REBATE_TYPE);
        fetchRebateList();
    }

    @Subscribe
    public void subscibe(FragmentPageChangeEvent fragmentPageChangeEvent) {
        currentRebateType = fragmentPageChangeEvent.getPosition();
        fetchRebateList();
    }

    @Override
    public void onLoadMore() {
        if (currentRebateType == TYPE_CUSTOMER) {
            ++customerPage;
        }else {
            ++attendantPage;
        }
        fetchRebateList();
    }

    @Override
    public void onRefresh() {
        reset();
        fetchRebateList();
    }

    private void fetchRebateList() {
        String token = RecommendApplication.getInstance().getToken();
        if (TextUtils.isEmpty(token)) {
            Toast.makeText(getActivity(), getString(R.string.text_unknown_error), Toast.LENGTH_SHORT).show();
            getActivity().finish();
            return;
        }
        Retrofit retrofit = RecommendApplication.getInstance().getRetrofit();
        RebateService rebateService = retrofit.create(RebateService.class);
        Call<List<Rebate>> call;
        if (currentRebateType == TYPE_CUSTOMER) {
            call = rebateService.rebateList(String.format("%s %s", getString(R.string.text_prefix_token), token),
                    customerPage, DEFAULT_PAGE_SIZE, getString(R.string.text_from_time_default), currentRebateType);
        }else {
            call = rebateService.rebateList(String.format("%s %s", getString(R.string.text_prefix_token), token),
                    attendantPage, DEFAULT_PAGE_SIZE, getString(R.string.text_from_time_default), currentRebateType);
        }
        call.enqueue(new Callback<List<Rebate>>() {
            @Override
            public void onResponse(Call<List<Rebate>> call, Response<List<Rebate>> response) {
                completeRefresh();
                if (response.isSuccessful()) {
                    if (response.body() == null) {
                        if (rebateListAdapter.getItemCount() == 0) {
                            showError();
                        }
                        swipeToLoadLayout.setLoadingMore(false);
                        swipeToLoadLayout.setLoadMoreEnabled(false);
                    }else {
                        if (response.body().size() == 0 ) {
                            if (rebateListAdapter.getItemCount() == 0 ) {
                                showEmpty();
                            }
                            swipeToLoadLayout.setLoadingMore(false);
                            swipeToLoadLayout.setLoadMoreEnabled(false);
                        }else {
                            if (response.body().size() > 0 && response.body().size() < DEFAULT_PAGE_SIZE) {
                                rebateListAdapter.appendData(response.body());
                                swipeToLoadLayout.setLoadingMore(false);
                                swipeToLoadLayout.setLoadMoreEnabled(false);
                            }else if (response.body().size() == DEFAULT_PAGE_SIZE) {
                                rebateListAdapter.appendData(response.body());
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
            public void onFailure(Call<List<Rebate>> call, Throwable t) {
                showError();
                completeRefresh();
                swipeToLoadLayout.setLoadingMore(false);
                swipeToLoadLayout.setLoadMoreEnabled(false);
            }
        });
    }

    private void reset(){
        rebateListAdapter.clear();
        if (currentRebateType == TYPE_CUSTOMER) {
            customerPage = 0;
        }else {
            attendantPage = 0;
        }
        swipeToLoadLayout.setRefreshEnabled(true);
        swipeToLoadLayout.setLoadMoreEnabled(true);
    }

    private void completeRefresh() {
        if (customerPage == 0 || attendantPage == 0 ){
            swipeToLoadLayout.setRefreshing(false);
        }else {
            swipeToLoadLayout.setLoadingMore(false);
        }
    }

    private void showEmpty() {

    }

    private void showError() {

    }

    @Override
    public void onDetach() {
        super.onDetach();
        ApplicationBus.getInstance().unregister(this);
    }
}
