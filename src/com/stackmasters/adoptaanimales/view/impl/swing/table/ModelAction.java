package com.stackmasters.adoptaanimales.view.impl.swing.table;

public class ModelAction<T> {

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public EventAction getEvent() {
        return event;
    }

    public void setEvent(EventAction event) {
        this.event = event;
    }

    public ModelAction (T item, EventAction event) {
        this.item = item;
        this.event = event;
    }

    public ModelAction() {
    }

    private T item;
    private EventAction event;
}