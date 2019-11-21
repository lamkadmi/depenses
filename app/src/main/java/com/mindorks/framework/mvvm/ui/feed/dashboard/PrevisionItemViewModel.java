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

package com.mindorks.framework.mvvm.ui.feed.dashboard;

import androidx.databinding.ObservableField;
import com.mindorks.framework.mvvm.data.model.api.BlogResponse;
import com.mindorks.framework.mvvm.data.model.others.PrevisionByCategorie;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class PrevisionItemViewModel {

    public final ObservableField<String> categorie;

    public final ObservableField<String> montant;

    public final ObservableField<String> date;

    //public final ObservableField<String> imageUrl;

    public final BlogItemViewModelListener mListener;

    //public final ObservableField<String> title;

    private final PrevisionByCategorie mBlog;

    public PrevisionItemViewModel(PrevisionByCategorie blog, BlogItemViewModelListener listener) {
        this.mBlog = blog;
        this.mListener = listener;
        //imageUrl = new ObservableField<>(mBlog.getCoverImgUrl());
        categorie = new ObservableField<>(mBlog.getCategorie());
        //author = new ObservableField<>(mBlog.getAuthor());
        date = new ObservableField<>(mBlog.getDate());
        montant = new ObservableField<>(String.valueOf(mBlog.getMontant()));
    }

    public void onItemClick() {
        //mListener.onItemClick(mBlog.getBlogUrl());
    }

    public interface BlogItemViewModelListener {

        void onItemClick(String blogUrl);
    }
}
