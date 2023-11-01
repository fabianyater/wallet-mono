package com.wallet.mono.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalInfo {

    private int incomeDefaultCategories;
    private int expenseDefaultCategories;
    private int incomeUserCategories;
    private int expenseUserCategories;
    private int totalIncome;
    private int totalExpense;

}
