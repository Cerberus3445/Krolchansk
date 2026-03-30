package ru.krolchansk.common.validator;

public interface UpdateValidator<T, ID> {

    void validate(ID id, T dto);
}
