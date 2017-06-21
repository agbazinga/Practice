package com.example.user.mysettingspreferencefragment;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by User on 4/25/2016.
 */
public class TestListenerController {

    private static CopyOnWriteArrayList<TestListener> mListeners = new CopyOnWriteArrayList<>();

    public static void addToListener(TestListener testListener) {
        if (!mListeners.contains(testListener))
            mListeners.add(testListener);
    }

    public static void removeFromListener(TestListener testListener) {
        if (mListeners.contains(testListener))
            mListeners.remove(testListener);
    }

    public static void notifyListener() {
        if (!mListeners.isEmpty()) {
            for (TestListener testListener : mListeners) {
                testListener.onChange();
            }
        }
    }

}
