package com.study.flashcardaibackend.model.common;

import java.util.ArrayList;


public class CommonList<T> extends ArrayList<T> {
    public boolean isExisted(T item) {
        return this.stream().anyMatch(i -> i.equals(item));
    }

    public void addIfNotExisted(T newItem) {
        if (this.isExisted(newItem)) ;
        this.add(newItem);
    }

    public void addIfNotExisted(T newItem, RuntimeException exp) {
        if (this.isExisted(newItem)) throw exp;
        this.add(newItem);
    }
}