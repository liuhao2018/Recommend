package com.limefamily.recommend.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.limefamily.recommend.R;
import com.limefamily.recommend.RecommendApplication;
import com.limefamily.recommend.adapter.HomeListAdapter;
import com.limefamily.recommend.model.HomeResponse;
import com.limefamily.recommend.restful.NewsService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 *
 * @author liuhao
 * @date 2018/3/28
 */

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeListAdapter adapter;

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
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new HomeListAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Retrofit retrofit = RecommendApplication.getInstance().getRetrofit();
        NewsService service = retrofit.create(NewsService.class);
        Call<HomeResponse> call = service.fetchHome();
        call.enqueue(new Callback<HomeResponse>() {
            @Override
            public void onResponse(Call<HomeResponse> call, Response<HomeResponse> response) {
                if (response.isSuccessful()) {
                    adapter.setData(response.body().getNews(), response.body().getHot());
                }
            }

            @Override
            public void onFailure(Call<HomeResponse> call, Throwable t) {

            }
        });
    }
}
