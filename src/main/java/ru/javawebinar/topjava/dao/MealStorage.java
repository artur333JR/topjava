package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealStorage {
    List<Meal> getAll();
//    Meal getById(long Id);
}
