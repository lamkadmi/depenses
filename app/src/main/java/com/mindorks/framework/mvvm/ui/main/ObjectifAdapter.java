/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package com.mindorks.framework.mvvm.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mindorks.framework.mvvm.data.model.others.QuestionCardData;
import com.mindorks.framework.mvvm.databinding.ItemBlogEmptyViewBinding;
import com.mindorks.framework.mvvm.databinding.ItemObjectifViewBinding;
import com.mindorks.framework.mvvm.ui.base.BaseViewHolder;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class ObjectifAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<QuestionCardData> mObjectifResponseList;

    private BlogAdapterListener mListener;

    public ObjectifAdapter(List<QuestionCardData> objectifResponseList) {
        this.mObjectifResponseList = objectifResponseList;
    }

    @Override
    public int getItemCount() {
        if (mObjectifResponseList != null && mObjectifResponseList.size() > 0) {
            return mObjectifResponseList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mObjectifResponseList != null && !mObjectifResponseList.isEmpty()) {
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
                ItemObjectifViewBinding blogViewBinding = ItemObjectifViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new ObjectifViewHolder(blogViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemBlogEmptyViewBinding emptyViewBinding = ItemBlogEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    public void addItems(List<QuestionCardData> blogList) {
        mObjectifResponseList.addAll(blogList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mObjectifResponseList.clear();
    }

    public void setListener(BlogAdapterListener listener) {
        this.mListener = listener;
    }

    public interface BlogAdapterListener {

        void onRetryClick();
    }

    public class ObjectifViewHolder extends BaseViewHolder {

        private ItemObjectifViewBinding mBinding;

        private ObjectifItemViewModel mBlogItemViewModel;

        public ObjectifViewHolder(ItemObjectifViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final QuestionCardData blog = mObjectifResponseList.get(position);
            mBlogItemViewModel = new ObjectifItemViewModel(blog);
            mBinding.setViewModel(mBlogItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }

    }

    public class EmptyViewHolder extends BaseViewHolder {

        private ItemBlogEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemBlogEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
//            ItemBlogEmptyViewBinding emptyItemViewModel = new ItemBlogEmptyViewBinding(this);
            //mBinding.setViewModel(emptyItemViewModel);
        }
    }
}