package com.stackmasters.adoptaanimales.view.impl.swing.table;

import com.stackmasters.adoptaanimales.view.impl.model.ModelMascota;

public class ModelAction {

    public ModelMascota getStudent() {
        return student;
    }

    public void setStudent(ModelMascota student) {
        this.student = student;
    }

    public EventAction getEvent() {
        return event;
    }

    public void setEvent(EventAction event) {
        this.event = event;
    }

    public ModelAction(ModelMascota student, EventAction event) {
        this.student = student;
        this.event = event;
    }

    public ModelAction() {
    }

    private ModelMascota student;
    private EventAction event;
}
