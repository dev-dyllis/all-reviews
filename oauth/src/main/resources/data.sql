INSERT INTO OAUTH_CLIENT_DETAILS(
      CLIENT_ID
    , CLIENT_SECRET
    , RESOURCE_IDS
    , SCOPE
    , AUTHORIZED_GRANT_TYPES
    , WEB_SERVER_REDIRECT_URI
    , AUTHORITIES
    , ACCESS_TOKEN_VALIDITY
    , REFRESH_TOKEN_VALIDITY
    , ADDITIONAL_INFORMATION
    , AUTOAPPROVE
) VALUES(
      'all_reviews'
    , '{noop}all_reviews_secret'
    , null
    , 'read,write'
    , 'authorization_code,password,client_credentials,implicit,refresh_token'
    , null
    , 'ROLE_USER'
    , 36000
    , 2592000
    , null
    , null
);