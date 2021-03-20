package com.project.depense.mvvm.ui.home.dashboard;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.project.depense.mvvm.data.model.others.PrevisionByCategorie;
import com.mindorks.framework.mvvm.databinding.ItemBlogEmptyViewBinding;
import com.mindorks.framework.mvvm.databinding.ItemPrevevisionViewBinding;
import com.project.depense.mvvm.ui.base.BaseViewHolder;
import com.project.depense.mvvm.utils.AppLogger;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class PrevisionAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<PrevisionByCategorie> mPrevisionList;

    private PrevisionAdapterListener mListener;

    public PrevisionAdapter(List<PrevisionByCategorie> blogResponseList) {
        this.mPrevisionList = blogResponseList;
    }

    @Override
    public int getItemCount() {
        if (mPrevisionList != null && mPrevisionList.size() > 0) {
            return mPrevisionList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mPrevisionList != null && !mPrevisionList.isEmpty()) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                ItemPrevevisionViewBinding blogViewBinding = ItemPrevevisionViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new PrevisionViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemBlogEmptyViewBinding emptyViewBinding = ItemBlogEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    public void addItems(List<PrevisionByCategorie> blogList) {
        mPrevisionList.addAll(blogList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mPrevisionList.clear();
    }

    public void setListener(PrevisionAdapterListener listener) {
        this.mListener = listener;
    }

    public interface PrevisionAdapterListener {

        void onRetryClick();
    }

    public class PrevisionViewHolder extends BaseViewHolder implements PrevisionItemViewModel.BlogItemViewModelListener {

        private ItemPrevevisionViewBinding mBinding;

        private PrevisionItemViewModel mBlogItemViewModel;

        public PrevisionViewHolder(ItemPrevevisionViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final PrevisionByCategorie blog = mPrevisionList.get(position);
            mBlogItemViewModel = new PrevisionItemViewModel(blog, this);
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
            mListener.onRetryClick();
        }
    }
}