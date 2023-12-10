package com.app.urbangym.entitys;

import java.util.List;

public class EjercicioWrapper {
    private List<Exercise> ejercicios;

    public EjercicioWrapper() {
        // Constructor por defecto
    }

    public List<Exercise> getEjercicios() {
        return ejercicios;
    }

    public void setEjercicios(List<Exercise> ejercicios) {
        this.ejercicios = ejercicios;
    }
}
