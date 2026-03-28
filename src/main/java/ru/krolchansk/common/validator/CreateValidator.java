package ru.krolchansk.common.validator;

public interface CreateValidator<T> {

    void validate(T dto);
}
