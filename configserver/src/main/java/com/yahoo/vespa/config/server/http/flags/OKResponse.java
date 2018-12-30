// Copyright 2018 Yahoo Holdings. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.vespa.config.server.http.flags;

import com.yahoo.container.jdisc.EmptyResponse;
import com.yahoo.jdisc.Response;

/**
 * @author hakonhall
 */
public class OKResponse extends EmptyResponse {
    public OKResponse() {
        super(Response.Status.OK);
    }

    @Override
    public String getContentType() {
        return "application/json";
    }
}
