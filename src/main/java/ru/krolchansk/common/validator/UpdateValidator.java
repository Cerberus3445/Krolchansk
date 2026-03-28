package ru.krolchansk.common.validator;

public interface UpdateValidator<T> {

    void validate(T dto);
}
