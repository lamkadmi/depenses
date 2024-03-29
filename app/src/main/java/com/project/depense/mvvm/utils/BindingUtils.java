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

package com.project.depense.mvvm.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.depense.mvvm.data.model.db.Categorie;
import com.project.depense.mvvm.data.model.db.Revenu;
import com.project.depense.mvvm.data.model.others.DepenseByCategorie;
import com.project.depense.mvvm.data.model.others.PrevisionByCategorie;
import com.project.depense.mvvm.data.model.others.QuestionCardData;
import com.project.depense.mvvm.ui.categorie.CategorieAdapter;
import com.project.depense.mvvm.ui.home.dashboard.PrevisionAdapter;
import com.project.depense.mvvm.ui.home.depense.DepenseAdapter;
import com.project.depense.mvvm.ui.home.revenu.RevenuAdapter;
import com.project.depense.mvvm.ui.main.MainViewModel;
import com.project.depense.mvvm.ui.main.ObjectifAdapter;
import com.project.depense.mvvm.ui.main.QuestionCard;
import com.mindorks.placeholderview.SwipePlaceHolderView;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by lamkadmi on 17/11/19.
 */

public final class BindingUtils {

    private BindingUtils() {
        // This class is not publicly instantiable
    }

    @BindingAdapter({"adapter"})
    public static void addBlogItems(RecyclerView recyclerView, List<PrevisionByCategorie> previsions) {
        PrevisionAdapter adapter = (PrevisionAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(previsions);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addCategorieItems(RecyclerView recyclerView, List<Categorie> categories) {
        CategorieAdapter adapter = (CategorieAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(categories);
        }
    }


    @BindingAdapter({"adapter"})
    public static void addDepenseItems(RecyclerView recyclerView, List<DepenseByCategorie> depenseItems) {
        DepenseAdapter adapter = (DepenseAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(depenseItems);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addRevenuItems(RecyclerView recyclerView, List<Revenu> revenus) {
        RevenuAdapter adapter = (RevenuAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(revenus);
        }
    }

    @BindingAdapter({"adapter"})
    public static void addObjectifItems(RecyclerView recyclerView, List<QuestionCardData> objectifs) {
        ObjectifAdapter adapter = (ObjectifAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.clearItems();
            adapter.addItems(objectifs);
        }
    }


    @BindingAdapter({"adapter", "action"})
    public static void addQuestionItems(SwipePlaceHolderView mCardsContainerView, List<QuestionCardData> mQuestionList, int mAction) {
        if (mAction == MainViewModel.ACTION_ADD_ALL) {
            if (mQuestionList != null) {
                mCardsContainerView.removeAllViews();
                for (QuestionCardData question : mQuestionList) {
                    if (question != null && question.options != null && question.options.size() == 3) {
                        mCardsContainerView.addView(new QuestionCard(question));
                    }
                }
                ViewAnimationUtils.scaleAnimateView(mCardsContainerView);
            }
        }
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }

}
