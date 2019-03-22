// Copyright 2019 Oath Inc. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.vespa.hosted.controller.role;

import com.yahoo.config.provision.ApplicationName;
import com.yahoo.config.provision.SystemName;
import com.yahoo.config.provision.TenantName;

import java.util.Objects;
import java.util.Optional;

/**
 * The context in which a role is valid.
 *
 * @author mpolden
 */
public class Context {

    private final Optional<TenantName> tenant;
    private final Optional<ApplicationName> application;
    private final SystemName system;

    private Context(Optional<TenantName> tenant, Optional<ApplicationName> application, SystemName system) {
        this.tenant = Objects.requireNonNull(tenant, "tenant must be non-null");
        this.application = Objects.requireNonNull(application, "application must be non-null");
        this.system = Objects.requireNonNull(system, "system must be non-null");
    }

    /** A specific tenant this is valid for, if any */
    public Optional<TenantName> tenant() {
        return tenant;
    }

    /** A specific application this is valid for, if any */
    public Optional<ApplicationName> application() {
        return application;
    }

    /** System in which this is valid */
    public SystemName system() {
        return system;
    }

    /** Returns an empty context in given system */
    public static Context empty(SystemName system) {
        return new Context(Optional.empty(), Optional.empty(), system);
    }

    /** Returns context for a tenant and an optional application in system */
    public static Context of(TenantName tenant, Optional<ApplicationName> application, SystemName system) {
        return new Context(Optional.of(tenant), application, system);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Context context = (Context) o;
        return tenant.equals(context.tenant) &&
               application.equals(context.application) &&
               system == context.system;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tenant, application, system);
    }

    @Override
    public String toString() {
        return "tenant " + tenant.map(TenantName::value).orElse("[none]") + ", application " +
               application.map(ApplicationName::value).orElse("[none]") + ", system " + system;
    }

}
