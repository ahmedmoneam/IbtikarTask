package com.moneam.commonlibrary.mvp.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public abstract class DefaultAdapter<T> extends RecyclerView.Adapter<BaseHolder<T>> {
    private List<T> mOriginalItems;
    private List<T> mFilteredItems;
    private List<OnRecyclerViewItemClickListener<T>> mOnItemClickListeners = new ArrayList<>();

    public DefaultAdapter(List<T> items) {
        super();
        this.mOriginalItems = items;
        this.mFilteredItems = items != null ? new ArrayList<>(items) : new ArrayList<>();
    }

    @Override
    public BaseHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
        BaseHolder<T> mHolder = getHolder(view);
        mHolder.setOnItemClickListener((view1, position) -> {
            if (mOnItemClickListeners != null && !mOnItemClickListeners.isEmpty()) {
                for (OnRecyclerViewItemClickListener<T> onItemClickListener : mOnItemClickListeners) {
                    onItemClickListener.onItemClick(view1, mFilteredItems.get(position), position);
                }
            }
        });
        return mHolder;
    }

    @Override
    public void onBindViewHolder(BaseHolder<T> holder, int position) {
        holder.setData(mFilteredItems.get(position));
    }

    @Override
    public int getItemCount() {
        if (mFilteredItems != null) {
            return mFilteredItems.size();
        }
        return 0;
    }

    public List<T> getItems() {
        return mOriginalItems;
    }

    public void replaceItems(List<T> items) {
        this.mOriginalItems.clear();
        this.mFilteredItems.clear();

        this.mOriginalItems.addAll(items);
        this.mFilteredItems.addAll(items);


        notifyDataSetChanged();
    }

    public T getItem(int position) {
        if (mFilteredItems != null && mFilteredItems.size() > position)
            return mFilteredItems.get(position);
        else if (mOriginalItems != null && mOriginalItems.size() > position)
            return mOriginalItems.get(position);
        else return null;
    }

    public abstract BaseHolder<T> getHolder(View v);

    @LayoutRes
    public abstract int getLayoutId();

    public void setOnItemClickListener(OnRecyclerViewItemClickListener<T> listener) {
        this.mOnItemClickListeners.add(listener);
    }

    public void addItems(List<T> items) {
        mOriginalItems.addAll(items);
        mFilteredItems.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    public Completable filter(Predicate<T> prediction) {
        return Observable.fromIterable(getItems())
                .filter(prediction)
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(this::replaceFilteredItems)
                .toCompletable();
    }

    public void resetFilter() {
        mFilteredItems.clear();
        mFilteredItems.addAll(mOriginalItems);
        notifyDataSetChanged();
    }

    private void replaceFilteredItems(List<T> addresses) {
        mFilteredItems.clear();
        mFilteredItems.addAll(addresses);
        notifyDataSetChanged();
    }

    public interface OnRecyclerViewItemClickListener<T> {

        void onItemClick(View view, T data, int position);
    }
}
