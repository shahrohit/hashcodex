package com.shahrohit.hashcodex.utils;

/**
 * Constant values used throughout the application.
 */
public final class Constants {
    private Constants() {}

    /**
     * Constants related to Authorization, Cookies, Tokens, etc.
     */
    public static final class Auth {
        private Auth() {}

        public static final String HEADER_CONTENT_TYPE = "Content-Type";
        public static final String HEADER_AUTHORIZATION = "Authorization";
        public static final String HEADER_SET_COOKIE = "Set-Cookie";
        public static final String HEADER_USER_AGENT = "User-Agent";
        public static final String BEARER_TOKEN_PREFIX = "Bearer ";
        public static final String COOKIE_REFRESH_TOKEN = "rt";
        public static final String JWT_CLAIM_ROLE = "role";
        public static final String JWT_CLAIM_SESSION = "jti";
        public static final String JWT_CLAIM_TYPE = "type";
    }

    public static final class FIELD {
        private FIELD() {}
        public static final String ID = "id";
        public static final String UPDATED_AT = "updatedAt";
    }

    public static final class RABBITMQ {
        private RABBITMQ() {}

        // Exchanges
        public static final String REQ_EXCHANGE = "hashcodex.req.exchange";
        public static final String RES_EXCHANGE = "hashcodex.res.exchange";

        // Queues
        public static final String REQ_QUEUE = "hashcodex.req.queue";
        public static final String RES_QUEUE = "hashcodex.res.queue";

        // Routing keys
        public static final String REQ_ROUTING_KEY = "hashcodex.req";
        public static final String RES_ROUTING_KEY = "hashcodex.res";
    }
}