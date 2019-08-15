package ru.byprogminer.Lab2_Web.model;

import java.time.ZonedDateTime;

public interface MainModel {

    ZonedDateTime getCurrentTime();
    double getElapsedTime();

    boolean doFrontendTimeUpdate();
}
