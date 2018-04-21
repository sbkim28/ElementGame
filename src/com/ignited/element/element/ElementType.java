package com.ignited.element.element;

public enum ElementType {
    ALKALI_METAL("Alkali metal"), ALKALINE_EARTH_METAL("Alkaline earth metal"),
    LANTHANIDE("Lanthanide"), ACTINIDE("Actinide"), TRANSITION_METAL("Transition metal"),
    POST_TRANSITION_METAL("Post-transition metal"),
    METALLOID("Metalloid"), NONMETAL("Nonmetal"), NOBLE_GASES("NOBLE_GASES"), UNKNOWN("Unknown");

    private String name;

    ElementType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
