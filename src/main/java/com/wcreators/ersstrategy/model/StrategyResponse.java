package com.wcreators.ersstrategy.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

@Builder
@With
@Data
public class StrategyResponse {
    private boolean isAction;
    private String data;
}
