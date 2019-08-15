package ru.byprogminer.Lab2_Web.model;

import java.time.ZonedDateTime;

public class MainModelImpl implements MainModel {

    private boolean doFrontendTimeUpdate = true;
    private final long startTime;

    public MainModelImpl(Long startTime) {
        if (startTime == null) {
            startTime = System.nanoTime();
        }

        this.startTime = startTime;
    }

    @Override
    public ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now();
    }

    @Override
    public double getElapsedTime() {
        return ((double) (System.nanoTime() - startTime)) / 10000000;
    }

    @Override
    public boolean doFrontendTimeUpdate() {
        return doFrontendTimeUpdate;
    }

    public void setDoFrontendTimeUpdate(boolean doFrontendTimeUpdate) {
        this.doFrontendTimeUpdate = doFrontendTimeUpdate;
    }
}
