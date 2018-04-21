package com.ignited.element.element;

import com.google.gson.*;
import com.ignited.element.data.DataManager;

import java.io.*;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

public class Element {



    private final int number;
    private final String name;
    private final String symbol;
    private final ElementType elementType;
    private final int period;
    private final int group;

    public Element(int number, String name, String symbol, int period, int group, ElementType elementType) {
        this.name = name;
        this.symbol = symbol;
        this.number = number;
        this.elementType = elementType;
        this.period = period;
        this.group = group;
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public int getPeriod() {
        return period;
    }

    public int getGroup() {
        return group;
    }

    @Override
    public String toString() {
        return "Element{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", elementType=" + elementType +
                ", period=" + period +
                ", group=" + group +
                '}';
    }
}
