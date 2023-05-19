/**
 * Classe POJO errori di validazione
 * @author Tornaghi Omar
 * @version 1.0
 */
package com.mondorevive.TRESPOT.exceptions.errors;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Getter
@Setter
class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}