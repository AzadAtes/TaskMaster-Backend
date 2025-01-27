1. Write tests for authentication (token expiration, etc.)
2. validate user registration
3. find a solution to not create a refresh token for each login
4. refreshToken housekeeping

    wird logger immer neu instanziiert?
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);
    wo jwt token speichern? cookie? brauch ich csrf protection?
    HTTPS only!