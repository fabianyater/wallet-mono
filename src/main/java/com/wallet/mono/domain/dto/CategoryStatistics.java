package com.wallet.mono.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryStatistics {
    private List<CategorySummary> categorySummary;
    private double total;
}
