CREATE TABLE IF NOT EXISTS OAUTH_CLIENT_DETAILS (
    CLIENT_ID               VARCHAR(256)    PRIMARY KEY,
    RESOURCE_IDS            VARCHAR(256),
    CLIENT_SECRET           VARCHAR(256),
    SCOPE                   VARCHAR(256),
    AUTHORIZED_GRANT_TYPES  VARCHAR(256),
    WEB_SERVER_REDIRECT_URI VARCHAR(256),
    AUTHORITIES             VARCHAR(256),
    ACCESS_TOKEN_VALIDITY   INTEGER,
    REFRESH_TOKEN_VALIDITY  INTEGER,
    ADDITIONAL_INFORMATION  VARCHAR(4096),
    AUTOAPPROVE             VARCHAR(256)
);