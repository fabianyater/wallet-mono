package com.wallet.mono.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultCategories {
    public enum CategoryType {
        INCOME, EXPENSE
    }

    public static final Map<String, CategoryType> DEFAULT_CATEGORIES_MAP = new HashMap<>();

    static {
        DEFAULT_CATEGORIES_MAP.put("Comida", CategoryType.EXPENSE);
        DEFAULT_CATEGORIES_MAP.put("Utilidades", CategoryType.EXPENSE);
        DEFAULT_CATEGORIES_MAP.put("Ropa", CategoryType.EXPENSE);
        DEFAULT_CATEGORIES_MAP.put("Educación", CategoryType.EXPENSE);
        DEFAULT_CATEGORIES_MAP.put("Entretenimiento", CategoryType.EXPENSE);
        DEFAULT_CATEGORIES_MAP.put("Aptitud", CategoryType.EXPENSE);
        DEFAULT_CATEGORIES_MAP.put("Regalo", CategoryType.EXPENSE);
        DEFAULT_CATEGORIES_MAP.put("Belleza", CategoryType.EXPENSE);
        DEFAULT_CATEGORIES_MAP.put("Casa", CategoryType.EXPENSE);
        DEFAULT_CATEGORIES_MAP.put("Mascota", CategoryType.EXPENSE);
        DEFAULT_CATEGORIES_MAP.put("Compras", CategoryType.EXPENSE);
        DEFAULT_CATEGORIES_MAP.put("Transporte", CategoryType.EXPENSE);
        DEFAULT_CATEGORIES_MAP.put("Viajar", CategoryType.EXPENSE);
        DEFAULT_CATEGORIES_MAP.put("Otros", CategoryType.EXPENSE);

        DEFAULT_CATEGORIES_MAP.put("Reembolsos", CategoryType.INCOME);
        DEFAULT_CATEGORIES_MAP.put("Premios", CategoryType.INCOME);
        DEFAULT_CATEGORIES_MAP.put("Subsidios", CategoryType.INCOME);
        DEFAULT_CATEGORIES_MAP.put("Dividendos", CategoryType.INCOME);
        DEFAULT_CATEGORIES_MAP.put("Inversiones", CategoryType.INCOME);
        DEFAULT_CATEGORIES_MAP.put("Lotería", CategoryType.INCOME);
        DEFAULT_CATEGORIES_MAP.put("Salario", CategoryType.INCOME);
        DEFAULT_CATEGORIES_MAP.put("Cupones", CategoryType.INCOME);
    }
}
