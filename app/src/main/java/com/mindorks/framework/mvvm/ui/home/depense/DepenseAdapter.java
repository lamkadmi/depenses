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

package com.mindorks.framework.mvvm.ui.home.depense;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mindorks.framework.mvvm.data.model.others.DepenseByCategorie;
import com.mindorks.framework.mvvm.databinding.ItemDepenseViewBinding;
import com.mindorks.framework.mvvm.databinding.ItemOpenSourceEmptyViewBinding;
import com.mindorks.framework.mvvm.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class DepenseAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private final List<DepenseByCategorie> mDepenseResponseList;

    private DepenseAdapterListener mListener;

    public DepenseAdapter(List<DepenseByCategorie> depenses) {
        this.mDepenseResponseList = depenses;
    }

    public DepenseAdapter() {
        this.mDepenseResponseList = new ArrayList<>();
    }

    @Override
    public int getItemCount() {
        if (!mDepenseResponseList.isEmpty()) {
            return mDepenseResponseList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (!mDepenseResponseList.isEmpty()) {
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
                ItemDepenseViewBinding depenseViewBinding = ItemDepenseViewBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new DepenseViewHolder(depenseViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemOpenSourceEmptyViewBinding emptyViewBinding = ItemOpenSourceEmptyViewBinding
                        .inflate(LayoutInflater.from(parent.getContext()), parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    public void addItems(List<DepenseByCategorie> repoList) {
        mDepenseResponseList.addAll(repoList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mDepenseResponseList.clear();
    }

    public void setListener(DepenseAdapterListener listener) {
        this.mListener = listener;
    }

    public interface DepenseAdapterListener {

        void onRetryClick();
    }

    public class EmptyViewHolder extends BaseViewHolder implements DepenseEmptyItemViewModel.DepenseEmptyItemViewModelListener {

        private final ItemOpenSourceEmptyViewBinding mBinding;

        public EmptyViewHolder(ItemOpenSourceEmptyViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            DepenseEmptyItemViewModel emptyItemViewModel = new DepenseEmptyItemViewModel(this);
            mBinding.setViewModel(emptyItemViewModel);
        }

        @Override
        public void onRetryClick() {
            mListener.onRetryClick();
        }
    }

    public class DepenseViewHolder extends BaseViewHolder implements View.OnClickListener {

        private final ItemDepenseViewBinding mBinding;

        private DepenseItemViewModel mDepenseItemViewModel;


        public DepenseViewHolder(ItemDepenseViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final DepenseByCategorie mDepense = mDepenseResponseList.get(position);
            mDepenseItemViewModel = new DepenseItemViewModel(mDepense);
            mBinding.setViewModel(mDepenseItemViewModel);

            //mBinding.setViewModel(mDepenseItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }

        @Override
        public void onClick(View view) {
           /* if (mDepenseResponseList.get(0).montant.get() != null) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(mDepenseResponseList.get(0).montant.get()));
                    itemView.getContext().startActivity(intent);
                } catch (Exception e) {
                    AppLogger.d("url error");
                }
            }*/
        }
    }
}