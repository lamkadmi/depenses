package com.project.depense.mvvm.ui.home.depense.dialog;

import android.view.View;
import android.widget.AdapterView;

import com.project.depense.mvvm.data.DataManager;
import com.project.depense.mvvm.data.model.db.Categorie;
import com.project.depense.mvvm.data.model.db.Depense;
import com.project.depense.mvvm.ui.base.BaseViewModel;
import com.project.depense.mvvm.utils.AppUtils;
import com.project.depense.mvvm.utils.rx.SchedulerProvider;

import java.util.List;

import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;

public class DepenseDialogViewModel extends BaseViewModel<DepenseDialogNavigator> {

    private final ObservableField<String> depenseDate = new ObservableField<>();

    private final ObservableField<String> description = new ObservableField<>();

    private final ObservableField<String> montant = new ObservableField<>();

    private final ObservableField<Categorie> categorie = new ObservableField<>();

    private final MutableLiveData<List<Categorie>> categorieListLiveData;


    public DepenseDialogViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        categorieListLiveData = new MutableLiveData<>();
        fetchCategories();
    }

    public void onLaterClick() {
        getNavigator().dismissDialog();
    }

    public void onSubmitClick() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .saveDepense(new Depense(getCategorie().get().getId(),
                        AppUtils.getDateFromString(getDepenseDate().get()),
                        Float.valueOf(getMontant().get()),
                        getDescription().get()))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        fetchCategories();
                        getNavigator().dismissDialog();
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                }));

    }

    public void fetchCategories() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getCategories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(blogResponse -> {
                    if (blogResponse != null) {
                        categorieListLiveData.setValue(blogResponse);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    //getNavigator().handleError(throwable);
                }));
    }

    public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {
        categorie.set((Categorie) parent.getSelectedItem());
        //pos                                 get selected item position
        //view.getText()                      get lable of selected item
        //parent.getAdapter().getItem(pos)    get item by pos
        //parent.getAdapter().getCount()      get item count
        //parent.getCount()                   get item count
        //parent.getSelectedItem()            get selected item
        //and other...
    }


    public ObservableField<String> getDepenseDate() {
        return depenseDate;
    }

    public ObservableField<String> getDescription() {
        return description;
    }

    public ObservableField<String> getMontant() {
        return montant;
    }

    public ObservableField<Categorie> getCategorie() {
        return categorie;
    }

    public MutableLiveData<List<Categorie>> getCategorieListLiveData() {
        return categorieListLiveData;
    }


}
