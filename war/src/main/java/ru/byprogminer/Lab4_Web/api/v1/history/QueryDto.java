package ru.byprogminer.Lab4_Web.api.v1.history;

public class QueryDto {

    public final String x, y, r;
    public final boolean result;

    public QueryDto(String x, String y, String r, boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.result = result;
    }
}
