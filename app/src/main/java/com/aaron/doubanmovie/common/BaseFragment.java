package com.aaron.doubanmovie.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by aaronchan on 16/5/1.
 */
public abstract class BaseFragment extends Fragment {
    /**
     * RxJava 队列
     */
    private List<Subscription> mSubscriptionList;

    /**
     * Fragment 中生成的所有 RxJava Subscription 都需要调用此方法添加到队列
     * @param subscription
     */
    protected void addSubscription(Subscription subscription) {
        mSubscriptionList.add(subscription);
    }

    protected abstract int getLayoutResId();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initData();

        initDi();
    }

    /**
     * 依赖注入
     */
    protected void initDi() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);

        initView();

        return view;
    }

    /**
     * Fragment 销毁前取消订阅，避免内存泄漏
     */
    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);

        unSubscribeAll();

        super.onDestroy();
    }

    protected void initData() {
        mSubscriptionList = new ArrayList<>();
    }

    protected void initView() {

    }

    /**
     * 对所有 Subscription 取消订阅
     */
    private void unSubscribeAll() {
        for (Subscription subscription : mSubscriptionList) {
            if (!subscription.isUnsubscribed()) {
                subscription.unsubscribe();
            }
        }
    }
}
