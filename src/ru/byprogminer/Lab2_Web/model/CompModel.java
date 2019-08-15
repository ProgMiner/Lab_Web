package ru.byprogminer.Lab2_Web.model;

public interface CompModel {

    boolean isResultAvailable();

    Number getX();
    Number getY();
    Number getR();

    boolean getResult(double x, double y, double r);
}
