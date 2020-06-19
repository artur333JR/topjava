package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.dao.MealStorage;
import ru.javawebinar.topjava.dao.MockMealStorage;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final MealStorage mealStorage = new MockMealStorage();

    public static void main(String[] args) {

        List<MealTo> mealsTo = getToFilteredByDate(mealStorage.getAll(), LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println();

        List<MealTo> mealsTo1 = getAllTo(mealStorage.getAll(), 2000);
        mealsTo1.forEach(System.out::println);

    }

    public static List<MealTo> getToFilteredByDate(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return getAllTo(meals, caloriesPerDay)
                .stream()
                .filter(mealTo -> TimeUtil.isBetweenHalfOpen(mealTo.getTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    public static List<MealTo> getAllTo(List<Meal> meals, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());

    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
