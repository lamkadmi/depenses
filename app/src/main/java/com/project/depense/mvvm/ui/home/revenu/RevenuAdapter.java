package com.project.depense.mvvm.ui.home.revenu;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.project.depense.mvvm.data.model.db.Revenu;
import com.project.depense.mvvm.databinding.ItemBlogEmptyViewBinding;
import com.project.depense.mvvm.databinding.ItemRevenuViewBinding;
import com.project.depense.mvvm.ui.base.BaseViewHolder;
import com.project.depense.mvvm.ui.home.dashboard.PrevisionEmptyItemViewModel;
import com.project.depense.mvvm.utils.AppLogger;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RevenuAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<Revenu> mRevenuList;

    public RevenuAdapter(List<Revenu> mRevenuList) {
        this.mRevenuList = mRevenuList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemRevenuViewBinding revenuViewBinding = ItemRevenuViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new RevenuViewHolder(revenuViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemBlogEmptyViewBinding emptyViewBinding = ItemBlogEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (mRevenuList != null && mRevenuList.size() > 0) {
            return mRevenuList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mRevenuList != null && !mRevenuList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    public void addItems(List<Revenu> revenuList) {
        mRevenuList.addAll(revenuList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mRevenuList.clear();
    }


    public class RevenuViewHolder extends BaseViewHolder implements RevenuItemViewModel.RevenuItemViewModelListener {

        private ItemRevenuViewBinding mBinding;

        private RevenuItemViewModel mBlogItemViewModel;

        public RevenuViewHolder(ItemRevenuViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final Revenu revenu = mRevenuList.get(position);
            mBlogItemViewModel = new RevenuItemViewModel(revenu, this);
            mBinding.setViewModel(mBlogItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(String blogUrl) {
            if (blogUrl != null) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(blogUrl));
                    itemView.getContext().startActivity(intent);
                } catch (Exception e) {
                    AppLogger.d("url error");
                }
            }
        }
    }

    public class EmptyViewHolder extends BaseViewHolder implements PrevisionEmptyItemViewModel.BlogEmptyItemViewModelListener {

        private ItemBlogEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemBlogEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            PrevisionEmptyItemViewModel emptyItemViewModel = new PrevisionEmptyItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onRetryClick() {
            //mListener.onRetryClick();
        }
    }
}
