package com.rambler.edifier.core.listener;

import com.rambler.edifier.result.Request;

public interface SpiderListener {

    public void onSuccess(Request request);

    /**
     * @deprecated Use {@link #onError(Request, Exception)} instead.
     */
    @Deprecated
    public void onError(Request request);

    default void onError(Request request, Exception e) {
        this.onError(request);
    }

}

