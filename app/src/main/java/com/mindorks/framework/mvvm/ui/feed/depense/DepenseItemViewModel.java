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

package com.mindorks.framework.mvvm.ui.feed.depense;

import com.mindorks.framework.mvvm.data.model.db.Depense;
import com.mindorks.framework.mvvm.data.model.others.DepenseByCategorie;

import androidx.databinding.ObservableField;

/**
 * Created by lamkadmi on 17/11/19.
 */

public class DepenseItemViewModel {

    public final ObservableField<String> mois_anne = new ObservableField<>();

    public final ObservableField<String> libelle = new ObservableField<>();

    public final ObservableField<String> montant = new ObservableField<>();

    public final ObservableField<String> categorie = new ObservableField<>();


    public DepenseItemViewModel(String mois_anne, String title, String montant, String categorie) {
        this.mois_anne.set(mois_anne);
        this.libelle.set(title);
        this.categorie.set(categorie);
        this.montant.set(montant);
    }

    public DepenseItemViewModel(DepenseByCategorie depense) {
        //this.imageUrl.set(imageUrl);
        this.mois_anne.set(depense.getMois_annee());
        this.libelle.set(depense.getLibelle());
        this.categorie.set(depense.getCategorie());
        this.montant.set(String.valueOf(depense.getMontant()));
    }
}
