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

package com.mindorks.framework.mvvm.ui.home.dashboard;

import com.mindorks.framework.mvvm.ui.home.depense.DepenseAdapter;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;
import java.util.ArrayList;

/**
 * Created by lamkadmi on 17/11/19.
 */
@Module
public class DashboardFragmentModule {

    @Provides
    PrevisionAdapter providePrevisionAdapter() {
        return new PrevisionAdapter(new ArrayList<>());
    }

    @Provides
    DepenseAdapter provideDepenseAdapter() {
        return new DepenseAdapter(new ArrayList<>());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(DashboardFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
