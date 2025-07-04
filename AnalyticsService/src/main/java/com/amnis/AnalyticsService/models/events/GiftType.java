package com.amnis.AnalyticsService.models.events;

import com.fasterxml.jackson.annotation.JsonValue;



public enum GiftType {
    DIAMOND("diamond"),
    ROSE("rose"),
    GOLD_STAR("gold_star"),
    FIRE("fire"),
    CROWN("crown"),
    UNICORN("unicorn"),
    SUPER_CHAT("super_chat"),
    HEART("heart");

    private final String value;

    GiftType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
