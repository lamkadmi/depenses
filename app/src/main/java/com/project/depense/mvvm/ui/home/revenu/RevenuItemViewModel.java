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

package com.project.depense.mvvm.ui.home.revenu;

import com.project.depense.mvvm.data.model.db.Revenu;

import androidx.databinding.ObservableField;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class RevenuItemViewModel {


    public final ObservableField<String> montant;

    public final ObservableField<String> date;

    public final ObservableField<String> description;

    public final RevenuItemViewModelListener mListener;


    private final Revenu mRevenu;

    public RevenuItemViewModel(Revenu revenu, RevenuItemViewModelListener listener) {
        this.mRevenu = revenu;
        this.mListener = listener;

        date = new ObservableField<>(mRevenu.getMois_annee());
        montant = new ObservableField<>(String.valueOf(mRevenu.getMontant()));
        description = new ObservableField<>(mRevenu.getDescription());
    }

    public void onItemClick() {
        //mListener.onItemClick(mRevenu.getBlogUrl());
    }

    public interface RevenuItemViewModelListener {

        void onItemClick(String blogUrl);
    }
}
