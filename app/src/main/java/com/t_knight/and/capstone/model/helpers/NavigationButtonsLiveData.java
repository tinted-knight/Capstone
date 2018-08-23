package com.t_knight.and.capstone.model.helpers;

import android.arch.lifecycle.LiveData;
import android.util.Pair;

// Buttons Previous and Next in Read Screen
public class NavigationButtonsLiveData extends LiveData<Pair<Boolean, Boolean>> {

    public NavigationButtonsLiveData() {
        setValue(new Pair<>(true, true));
    }

    public void setPreviousState(Boolean value) {
        Pair<Boolean, Boolean> current = getValue();
        setValue(new Pair<>(value, current.second));
    }

    public void setNextState(Boolean value) {
        Pair<Boolean, Boolean> current = getValue();
        setValue(new Pair<>(current.first, value));
    }

}
