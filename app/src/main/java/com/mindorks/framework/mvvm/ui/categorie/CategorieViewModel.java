package com.mindorks.framework.mvvm.ui.categorie;

import com.mindorks.framework.mvvm.data.DataManager;
import com.mindorks.framework.mvvm.data.model.db.Categorie;
import com.mindorks.framework.mvvm.ui.base.BaseViewModel;
import com.mindorks.framework.mvvm.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CategorieViewModel extends BaseViewModel<CategorieNavigator> {

    private final MutableLiveData<List<Categorie>> categorieItemsLiveData;

    public CategorieViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        categorieItemsLiveData =  new MutableLiveData<>();
        fetchCategories();
    }


    public LiveData<List<Categorie>> getCategorieItemsLiveData() {
        return categorieItemsLiveData;
    }


    public void fetchCategories() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCategories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categories -> {
                    if (categories != null) {
                        categorieItemsLiveData.setValue(categories);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    //getNavigator().handleError(throwable);
                }));
    }

}
