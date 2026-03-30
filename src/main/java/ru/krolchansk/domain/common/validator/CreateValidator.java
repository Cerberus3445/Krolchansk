package ru.krolchansk.domain.common.validator;

public interface CreateValidator<T> {

    void validate(T dto);
}
