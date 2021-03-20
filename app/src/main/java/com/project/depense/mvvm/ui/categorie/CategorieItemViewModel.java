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

import com.project.depense.mvvm.data.model.db.Categorie;

import androidx.databinding.ObservableField;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class CategorieItemViewModel {

    public final ObservableField<String> categorie = new ObservableField<>();

    public final CategorieItemViewModelListener mListener;

    private final Categorie mCategorie;

    public CategorieItemViewModel(Categorie categorie, CategorieItemViewModelListener listener) {
        this.mCategorie = categorie;
        this.mListener = listener;
        this.categorie.set(categorie.getLibelle());
    }

    public void onItemClick() {
        mListener.onItemClick(mCategorie.getId());
    }

    public interface CategorieItemViewModelListener {

        void onItemClick(long id);
    }
}
