package app.service.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
@Getter
public abstract class BaseException extends Throwable {
    private final String title;
    private final String message;
}
