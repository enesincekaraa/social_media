package com.enesincekara.constants;

public class RabbitConstants {

    // Projenin ana Exchange yapısı
    public static final String EXCHANGE_USER = "exchange-user";

    // Şifre güncellemeleri için kuyruk ve anahtar
    // (User-Service'den Auth-Service'e asenkron geçiş için)
    public static final String QUEUE_UPDATE_PASSWORD = "queue-update-password";
    public static final String ROUTING_KEY_UPDATE_PASSWORD = "routing-key-update-password";

    public static final String QUEUE_AUTH_SOFT_DELETE = "queue-auth-soft-delete";
    public static final String ROUTING_KEY_AUTH_SOFT_DELETE = "routing-key-auth-soft-delete";


    public static final String EXCHANGE_AUTH = "exchange-auth";
    public static final String ROUTING_KEY_CREATE_PROFILE  ="routing-key-create-profile";
    public static final String QUEUE_CREATE_PROFILE = "queue-create-profile";

    public static final String ROUTING_KEY_LOGIN_EVENT = "routing-key-login-event";
    public static final String QUEUE_LOGIN_EVENT = "queue-login-event";


    public static final String ROUTING_KEY_UPDATE_AUTH = "routing-key-update-auth";
    public static final String QUEUE_UPDATE_AUTH = "queue-update-auth";


    public static final String EXCHANGE_COMPLAINT = "exchange-complaint";

    public static final String QUEUE_CREATE_COMPLAINT = "queue-create-complaint";
    public static final String ROUTING_KEY_CREATE_COMPLAINT = "routing-key-create-complaint";


    public static final String QUEUE_NOTIFICATION = "queue-notification";
    public static final String ROUTING_KEY_NOTIFICATION = "routing-key-notification";

}
