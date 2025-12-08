package com.stackmasters.adoptaanimales.view.impl.swing.table;

import com.stackmasters.adoptaanimales.view.impl.model.ModelMascota;

public interface EventAction <T> {

    public void delete(T item);

    public void update(T item);
}
