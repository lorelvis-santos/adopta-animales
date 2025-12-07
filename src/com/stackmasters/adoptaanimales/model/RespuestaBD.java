package com.stackmasters.adoptaanimales.model;

/**
 *
 * @author Bianca Parra
 */
public class RespuestaBD {
    private boolean ok;
    private int id;

    public RespuestaBD() {
    }

    public RespuestaBD(boolean ok, int id) {
        this.ok = ok;
        this.id = id;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
