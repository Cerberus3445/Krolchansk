package ru.krolchansk.domain.common.validator;

public interface UpdateValidator<T, ID> {

    void validate(ID id, T dto);
}
