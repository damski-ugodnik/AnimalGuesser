package org.example.model;

import org.example.utils.LanguageRules;

import java.time.LocalTime;

public enum PeriodOfDay {
    MORNING(5, 12),
    AFTERNOON(12, 18),
    EVENING(18, 24),
    NIGHT(0, 5);

    private final int startHour;
    private final int finishHour;
    private final String greeting;

    PeriodOfDay(int startHour, int finishHour) {
        this.startHour = startHour;
        this.finishHour = finishHour;
        this.greeting = LanguageRules.getMessage("greeting." + this.name().toLowerCase());
    }

    public String getGreeting() {
        return greeting;
    }

    public static PeriodOfDay defineCurrentPeriod() {
        LocalTime now = LocalTime.now();
        for (PeriodOfDay value : values()) {
            if (now.getHour() >= value.startHour && now.getHour() <= value.finishHour) {
                return value;
            }
        }
        throw new RuntimeException("Couldn't define period");
    }
}
