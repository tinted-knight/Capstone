package com.t_knight.and.capstone.model.helpers;

import android.support.transition.TransitionSet;

public class DetailTransition extends TransitionSet {

    public DetailTransition() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new android.support.transition.ChangeBounds())
                .addTransition(new android.support.transition.ChangeTransform())
                .addTransition(new android.support.transition.ChangeImageTransform());
    }

}
