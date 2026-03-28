package ru.krolchansk.web.advice;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.krolchansk.common.exception.NotFoundException;

@ControllerAdvice
@RequiredArgsConstructor
public class AdviceController {

    private final MessageSource messageSource;

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFound(NotFoundException e,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {

        String message = messageSource.getMessage(
                e.getMessageKey(),
                e.getArgs(),
                LocaleContextHolder.getLocale()
        );

        redirectAttributes.addFlashAttribute("notFoundError", message);

        // Берем URL предыдущей страницы из заголовков
        String referer = request.getHeader("Referer");

        return "redirect:" + (referer != null ? referer : "/admin");
    }
}
