package com.project.depense.mvvm.ui.home.revenu;

import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

@Module
public class RevenuFragmentModule {

    @Provides
    LinearLayoutManager provideLinearLayoutManager(RevenuFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }

    @Provides
    RevenuAdapter proRevenuAdapter(){
        return new RevenuAdapter(new ArrayList<>());
    }

}
