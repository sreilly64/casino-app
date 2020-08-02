package io.zipcoder.casino.games;

import java.util.HashMap;
import java.util.Map;
public enum Dice {
    ONE(1, "\u2680"), TWO(2, "\u2681"), THREE(3, "\u2682"),
    FOUR(4, "\u2683"), FIVE(5, "\u2684"), SIX(6, "\u2685");

    public String unicode;
    public Integer value;
    private static Map<Integer, Dice> map = new HashMap<>();

    Dice(Integer v, String u) {
        this.value = v;
        this.unicode = u;

    }

    public String getUnicode() {
        return unicode;
    }

    static {
        for (Dice die : Dice.values()) {
            map.put(die.value, die);
        }
    }

    public static Dice valueOf(Integer value) {
        return map.get(value);
    }

    public int getValue() {
        return value;
    }

    public static String printDice(int die){
        return Dice.valueOf(die).getUnicode();
    }

    public String toString() {
        return getUnicode();
    }
}
