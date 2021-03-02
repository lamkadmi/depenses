package com.mindorks.framework.mvvm.ui.categorie;

import com.mindorks.framework.mvvm.ui.home.depense.DepenseAdapter;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class CategorieActivityModule {

    @Provides
    LinearLayoutManager provideLinearLayoutManager(CategorieActivity categorieActivity) {
        return new LinearLayoutManager(categorieActivity);
    }

    @Provides
    CategorieAdapter provideCategorieAdapter() {
        return new CategorieAdapter(new ArrayList<>());
    }
}
