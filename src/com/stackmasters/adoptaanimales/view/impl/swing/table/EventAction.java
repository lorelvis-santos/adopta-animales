package com.stackmasters.adoptaanimales.view.impl.swing.table;

import com.stackmasters.adoptaanimales.view.impl.model.ModelMascota;

public interface EventAction {

    public void delete(ModelMascota student);

    public void update(ModelMascota student);
}
