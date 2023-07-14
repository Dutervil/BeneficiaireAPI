package com.wd.api.constant;

public class Authority {
    public static final String[] USER_AUTHORITIES = { "user:read","beneficiare:read" };
    public static final String[] HR_AUTHORITIES = { "user:read", "user:update" ,"beneficiare:read", "beneficiare:update" };
    public static final String[] MANAGER_AUTHORITIES = { "user:read", "user:update","beneficiare:read", "beneficiare:update" };
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update","beneficiare:create","beneficiare:read", "beneficiare:update" };
    public static final String[] SUPER_ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update", "user:delete","beneficiare:create","beneficiare:read", "beneficiare:update", "beneficiare:delete" };
}
