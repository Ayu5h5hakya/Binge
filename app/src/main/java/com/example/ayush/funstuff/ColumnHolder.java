package com.example.ayush.funstuff;

/**
 * Created by ayush on 3/9/16.
 */
public class ColumnHolder<T> {
        private T value;

        ColumnHolder(T value) {
            setValue(value);
        }

        T getValue() {
            return value;
        }

        void setValue(T value) {
            this.value = value;
        }
}
