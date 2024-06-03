package org.telran.codecrustpizza.exception;

public enum ExceptionMessage {

    NO_SUCH_ID("There is no %s with id: %d"),
    NO_SUCH_EMAIL("There is no %s with email: %s"),
    NO_SUCH_INGREDIENT("There is no such ingredient with title %s"),
    NO_SUCH_CATEGORY("There is no %s with category: %s"),
    NO_SUCH_ITEM_IN_CART("There is no such item in cart"),
    EMAIL_EXIST("%s with such email: %s already exist"),
    CATEGORY_EXIST("such category already exist in this %s"),
    CATEGORY_NOT_EXIST("such category not exist in this %s"),
    ENTITY_NOT_EXIST("This %s doesn't exist"),
    ENTITY_EXIST("This %s already exist"),
    ENTITY_IS_NULL("This %s is null");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getCustomMessage() {
        return message;
    }

    public String getCustomMessage(Long id) {
        return String.format(message, id);
    }

    public String getCustomMessage(String entity) {
        return String.format(message, entity);
    }

    public String getCustomMessage(String entity, String param) {
        return String.format(message, entity, param);
    }

    public String getCustomMessage(String entity, Long id) {
        return String.format(message, entity, id);
    }
}