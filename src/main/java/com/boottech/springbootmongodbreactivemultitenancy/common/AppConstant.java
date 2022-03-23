package com.boottech.springbootmongodbreactivemultitenancy.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstant {
    public static final String TENANT_HEADER_KEY = "X-Tenant";
    public static final String TENANT_ID = "TenantID";
}