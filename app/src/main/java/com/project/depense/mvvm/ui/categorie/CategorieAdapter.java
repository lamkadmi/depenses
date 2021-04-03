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

package com.project.depense.mvvm.ui.categorie;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.project.depense.mvvm.data.model.db.Categorie;
import com.project.depense.mvvm.databinding.ItemBlogEmptyViewBinding;
import com.project.depense.mvvm.databinding.ItemCategorieViewBinding;
import com.project.depense.mvvm.ui.base.BaseViewHolder;
import com.project.depense.mvvm.ui.home.dashboard.PrevisionEmptyItemViewModel;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class CategorieAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_EMPTY = 0;

    public static final int VIEW_TYPE_NORMAL = 1;

    private List<Categorie> mCategorieList;

    private CategorieAdapterListener mListener;

    public CategorieAdapter(List<Categorie> categorieList) {
        this.mCategorieList = categorieList;
    }

    @Override
    public int getItemCount() {
        if (mCategorieList != null && mCategorieList.size() > 0) {
            return mCategorieList.size();
        } else {
            return 1;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mCategorieList != null && !mCategorieList.isEmpty()) {
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
                ItemCategorieViewBinding categorieViewBinding = ItemCategorieViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new CategorieViewHolder(categorieViewBinding);
            case VIEW_TYPE_EMPTY:
            default:
                ItemBlogEmptyViewBinding emptyViewBinding = ItemBlogEmptyViewBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent, false);
                return new EmptyViewHolder(emptyViewBinding);
        }
    }

    public void addItems(List<Categorie> categorieList) {
        mCategorieList.addAll(categorieList);
        notifyDataSetChanged();
    }

    public void clearItems() {
        mCategorieList.clear();
    }

    public void setListener(CategorieAdapterListener listener) {
        this.mListener = listener;
    }

    public interface CategorieAdapterListener {

        void onRetryClick();
    }

    public class CategorieViewHolder extends BaseViewHolder implements CategorieItemViewModel.CategorieItemViewModelListener {

        private ItemCategorieViewBinding mBinding;

        private CategorieItemViewModel mCategorieItemViewModel;

        public CategorieViewHolder(ItemCategorieViewBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        @Override
        public void onBind(int position) {
            final Categorie categorie = mCategorieList.get(position);
            mCategorieItemViewModel = new CategorieItemViewModel(categorie, this);
            mBinding.setViewModel(mCategorieItemViewModel);

            // Immediate Binding
            // When a variable or observable changes, the binding will be scheduled to change before
            // the next frame. There are times, however, when binding must be executed immediately.
            // To force execution, use the executePendingBindings() method.
            mBinding.executePendingBindings();
        }

        @Override
        public void onItemClick(long id) {

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