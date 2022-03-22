package com.boottech.springbootmongodbreactivemultitenancy.common.exception;

public class TenantDataSourceNotFoundException extends RuntimeException {

    public TenantDataSourceNotFoundException() {
    }

    public TenantDataSourceNotFoundException(String message) {
        super(message);
    }

    public TenantDataSourceNotFoundException(Throwable throwable) {
        super(throwable);
    }

    public TenantDataSourceNotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
    
}
