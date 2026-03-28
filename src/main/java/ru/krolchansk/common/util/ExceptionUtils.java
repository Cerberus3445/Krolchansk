package ru.krolchansk.common.util;

import ru.krolchansk.common.exception.AlreadyExistsException;
import ru.krolchansk.common.exception.NotFoundException;
import ru.krolchansk.common.exception.ValidationException;

public class ExceptionUtils {

    public static NotFoundException notFound(String message, Object param){
         throw new NotFoundException(message, param);
    }

    public static AlreadyExistsException alreadyExists(String message, Object param){
        throw new AlreadyExistsException(message, param);
    }
}
