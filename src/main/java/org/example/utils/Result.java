package org.example.utils;

public class Result {
    private final double acc;
    private String rest;

    public Result(final double accParam, final String restParam) {
        this.acc = accParam;
        this.rest = restParam;
    }

    public final double getAcc() {
        return acc;
    }

    public final String getRest() {
        return rest;
    }

    public final void setRest(final String restParam) {
        this.rest = restParam;
    }
}
