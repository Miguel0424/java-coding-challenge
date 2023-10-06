package app.service.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public abstract class BaseException extends Exception {
    private final String title;
    private final String message;
}
