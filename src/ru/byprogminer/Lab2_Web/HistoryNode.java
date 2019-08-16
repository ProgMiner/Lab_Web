package ru.byprogminer.Lab2_Web;

public class HistoryNode {

    public final double x;
    public final double y;
    public final double r;
    public final boolean result;

    public final HistoryNode next;

    public HistoryNode(double x, double y, double r, boolean result, HistoryNode next) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
        this.next = next;
    }
}
