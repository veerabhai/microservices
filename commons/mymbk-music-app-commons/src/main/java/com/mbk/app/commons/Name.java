/*
 * Copyright (c) 2022 REPLACE_CUSTOMER_NAME. All rights reserved.
 *
 * This file is part of MBK App COPY.
 *
 * MBK App COPY project and associated code cannot be copied
 * and/or distributed without a written permission of REPLACE_CUSTOMER_NAME,
 * and/or its subsidiaries.
 */
package com.mbk.app.commons;

/**
 * Enum that contains the field / parameter names used across the application (either in log statements, exceptions,
 * etc.).
 * <p>
 * Instead of hardcoding the field / parameter names across the application, they are collated in this single place
 * which makes it usable across the application.
 *
 * @author Editor
 */
public enum Name {
    ACCESS_TOKEN("accessToken"),
    API_KEY("apiKey"),
    AUTHORITIES("authorities"),
    AT("@"),
    AUTHORITY("authority"),
    BEARER("Bearer"),
    BLACK_LIST_DOMAIN("BlackListDomain"),
    DESCRIPTION("description"),
    DOMAIN_TYPE("domainType"),
    EMAIL("email"),
    EMAIL_TEMPLATE_ID("emailTemplateId"),
    ERROR_CODE("errorCode"),
    ERROR_MESSAGE("errorMessage"),
    EXAMPLE("example"),
    ID("id"),
    IDS("ids"),
    FILE_NAME("fileName"),
    FILE_TYPE("fileType"),
    FILE_PATH("filePath"),
    FULL_NAME("fullName"),
    HTTP("http://"),
    HTTPS("https"),
    HTTPS_FORWARD_SLASHES("https://"),
    LOCALHOST("localhost"),
    OK("OK"),
    ORIGIN("Origin"),
    DATABASE_INSTANCE_ID("instanceId"),
    NOTIFICATION_TENANT_ID("tenantId"),
    PLATFORM_SUBDOMAIN_NAME("platformSubdomainName"),
    PROFILE_PIC("profilePic"),
    RESET_PASSWORD("Reset Password"),
    RESET_PASSWORD_LINK("resetPasswordLink"),
    SECRET("secret"),
    STATUS("status"),
    SUPER_ADMIN("superAdmin"),
    SUPER_ADMIN_NAME("superAdminName"),
    MASTER("master"),
    TENANT_ADMINISTRATOR("tenantAdministrator"),
    TENANT_ADMINISTRATOR_ID("tenantAdministratorId"),
    TENANT_DATABASE_CONFIGURATION_ID("tenantDatabaseConfigurationId"),
    TENANT_ID("tenantId"),
    TENANT_IDS("tenantIds"),
    TENANT_NAME("tenantName"),
    TENANT_SIGN_UP_LINK("tenantSignUpLink"),
    TENANT_ADMIN_NAME("adminName"),
    TENANT_SUBDOMAIN("subdomain"),
    HOST("host"),
    FIRST_NAME("firstName"),
    LAST_NAME("lastName"),
    PHONE("phone"),
    CATEGORY("category"),
    DATABASE_ENGINE("databaseEngine"),
    PORT("port"),
    SCHEMA_NAME("schemaName"),
    PASSWORD("password"),

    NAME("name"),
    PAYLOAD("payload"),
    PERMISSION("permission"),
    PRINCIPAL_ID("principalId"),
    REASON("reason"),
    ROLE("role"),
    ROLES("roles"),
    USERNAME("username"),
    USER_ID("userId"),
    LINK_ACTIVATION("activationLink"),
    USER_DISPLAY_NAME("userDisplayName"),
    UNDEFINED("undefined"),
    USER_PRINCIPAL("userPrincipal"),

    ACTIVE("active"),
    ACTOR("actor"),
    ACTOR_ID("actorId"),
    ACTOR_TYPE("actorType"),
    ACTIVATION_CODE("activationCode"),
    ACTIVATION_CODE_EXPIRY_TIMESTAMP("activationCodeExpiryTimestamp"),
    AGENT_TYPE("agentType"),
    AGENT_ARTIFACT_NAME("agentArtifactName"),
    AGENT_DOWNLOAD_LINK("agentDownloadLink"),
    ALERT_IDS("AlertIds"),
    ALERT_POLICY("alertPolicy"),
    ALERT_POLICY_ID("alertPolicyId"),
    ALERT_POLICY_IDS("alertPolicyIds"),
    ALL("All"),

    APM_SERVER_URL("apmServerUrl"),
    APPLICATION_ID("applicationId"),
    ASC("ASC"),
    AUTOMATED_MONITORING("automatedMonitoring"),
    AUTO_RESPONSE("autoResponse"),
    AUTH_MFA_REQUIRED("mfa_required"),
    AUTH_MFA_VERIFICATION_CODE("verification_code"),
    AUTOMATED_MONITORING_THRESHOLD_ID("automatedMonitoringThresholdId"),
    BODY("body"),

    CODE("code"),
    COMPOSITE_KEY("compositeKey"),
    CONFIDENCE_LEVEL("confidenceLevel"),
    CREATION_TIMESTAMP("creationTimestamp"),
    CURRENT_SCHEMA("currentSchema"),
    CURRENT_TIMESTAMP("currentTimestamp"),

    DATABASE_ACCESS_REQUESTS("databaseAccessRequests"),

    DATASOURCE("dataSource"),
    DATASOURCE_NAME("dataSourceName"),
    DATABASE_ROLE_NAME("databaseRoleName"),
    DATASOURCE_ID("dataSourceId"),
    DATASOURCES_BY_ROLES("dataSourcesByRoles"),
    DATA_FLOW_GROUP_ID("data_flow_group_id"),
    DB_ROLE_NAME("role_name"),
    DELTA_TIME("deltaTime"),
    DESC("DESC"),
    DIRECT_DB_ACCESS("directDBAccess"),

    ENTERPRISE_ACTOR_ID("enterprise_actor_id"),
    EXPIRATION_TIMESTAMP("expirationTimestamp"),
    FIFO("fifo"),

    FIRST_ACCESSED_TIME("firstAccessedTime"),

    FILTER_TYPE("filterType"),
    GRANT_TYPE_PASSWORD("password"),
    GRANT_TYPE_MFA("mfa"),

    INTERNAL_NAME("internalName"),
    IS_ADMIN_TEMPLATE("isAdminTemplate"),
    IS_DEFAULT("isdefault"),
    IS_DEFAULT_EMAIL_TEMPLATE("isDefaultEmailTemplate"),
    JSESSIONID("JSESSIONID"),
    JWK_KEY_ID("kid"),

    LAST_ACCESSED_TIME("lastAccessedTime"),

    LINK_EMAIL_VERIFICATION("emailVerificationLink"),
    LINK_ALERT_POLICY_NOTIFICATION("alertPolicyNotificationLink"),
    LINK_LOGIN("loginLink"),

    LOCKED("locked"),

    MAIL_SMTP_AUTH("mailsmtpauth"),
    MESSAGE("message"),
    MESSAGE_GROUP_ID("message-group-id"),
    MESSAGE_DEDUPLICATION_ID("message-deduplication-id"),
    MINIMUM_TRACES("minimumTraces"),
    MINIMUM_NO_OF_DAYS("minimumNoOfDays"),
    MULTIPLEXER_URL("multiplexerUrl"),
    NA("N/A"),

    NAMESPACE_U("u"),
    NOTIFICATION_ID("notificationId"),

    NOTIFICATION_TENANT_NAME("tenantName"),
    NOTIFICATION_TENANT_DOMAIN_NAME("tenantDomainName"),
    NOTIFICATION_ALERT_ID("alertId"),
    NOTIFICATION_ALERT_GENERATED_ON("generatedOn"),
    NOTIFICATION_ALERT_LINK("alertLink"),
    NOTIFICATION_ALERT_POLICY_NAME("policyName"),
    NOTIFICATION_ALERT_POLICY_RULE("policyRule"),
    NOTIFICATION_ALERT_SEVERITY("severity"),
    NOTIFICATION_DATA_SOURCE_HOST("dataSourceHost"),
    NOTIFICATION_DATA_SOURCE_ID("dataSourceId"),
    NOTIFICATION_DATA_SOURCE_LINK("dataSourceLink"),
    NOTIFICATION_DATA_SOURCE_NAME("dataSourceName"),
    NOTIFICATION_DATA_SOURCE_TYPE("dataSourceType"),
    NOTIFICATION_SERVICE_NAME("serviceName"),
    PAGE("page"),

    PERMISSIONS("permissions"),
    PLACEHOLDER_VALUE("placeholder_value"),

    PP("pp"),
    PROTOCOL("protocol"),
    QR_CODE("qrCode"),
    RESOLUTION_TYPE("resolutionType"),
    RESPONSE_ACTION("responseAction"),

    ROLE_NAME("roleName"),
    ROLE_SUPER("role_super"),
    ROLE_CREATE_DB("role_create_db"),
    ROLE_CREATE_ROLE("role_create_role"),
    ROLE_REPLICATION("role_replication"),
    ROLE_CAN_LOGIN("role_can_login"),
    ROLE_BY_PASS_RLS("role_by_pass"),

    SCHEMA_PUBLIC("public"),

    SECRET_TOKEN("secretToken"),
    SEVERITY("severity"),
    SERVICE_ID("serviceId"),
    SIZE("size"),
    SUB_DOMAIN_NAME("subDomainName"),
    SUBJECT("subject"),

    SYSTEM_USER("SYSTEM"),
    SYSTEM_POLICY("systemPolicy"),
    TAG_TENANT("Tenant"),
    TAG_TENANT_NAME("TenantName"),
    TAG_TENANT_SUBDOMAIN("TenantSubdomain"),
    TAG_INSTANCE_ID("InstanceId"),
    TEMPLATES("templates"),
    TEMPLATE_TYPE("templateType"),
    TENANT_DB("tenantdb"),
    TOKEN_ACTIVE("active"),
    TOKEN_EXPIRATION("exp"),
    TOKEN_MFA("mfa_token"),
    TOKEN_SCOPE("scope"),
    TOKEN_SUBJECT("sub"),
    TOKEN("token"),
    TRUE("true"),
    TYPE("type"),
    TYPE_CODE("typeCode"),
    VALUE("value"),

    PLATFORM_DB_USERNAME("postgres"),

    WWW("www");

    /** Internal name of the field. */
    private final String key;

    /**
     * Constructor.
     *
     * @param key
     *         Internal name of the field.
     */
    Name(final String key) {
        this.key = key;
    }

    /**
     * Returns the internal name of the field.
     *
     * @return Internal name of the field.
     */
    public String key() {
        return key;
    }
}