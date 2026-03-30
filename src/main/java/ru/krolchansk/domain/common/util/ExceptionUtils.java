package ru.krolchansk.domain.common.util;

import ru.krolchansk.domain.common.exception.AlreadyExistsException;
import ru.krolchansk.domain.common.exception.NotFoundException;

public class ExceptionUtils {

    public static NotFoundException notFound(String message, Object param){
         throw new NotFoundException(message, param);
    }

    public static AlreadyExistsException alreadyExists(String message, Object param){
        throw new AlreadyExistsException(message, param);
    }
}
