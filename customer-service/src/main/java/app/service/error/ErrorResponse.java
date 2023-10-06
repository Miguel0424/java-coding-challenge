package app.service.error;

import lombok.Value;

@Value
class ErrorResponse {
    String title;
    String message;
}
