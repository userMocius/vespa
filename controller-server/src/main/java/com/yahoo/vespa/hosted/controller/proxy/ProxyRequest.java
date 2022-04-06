// Copyright Yahoo. Licensed under the terms of the Apache 2.0 license. See LICENSE in the project root.
package com.yahoo.vespa.hosted.controller.proxy;

import com.yahoo.container.jdisc.HttpRequest;

import com.yahoo.restapi.HttpURL;
import com.yahoo.restapi.HttpURL.Path;
import com.yahoo.restapi.HttpURL.Query;
import com.yahoo.text.Text;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.yahoo.jdisc.http.HttpRequest.Method;

/**
 * Keeping information about the calls that are being proxied.
 * A request is of form /zone/v2/[environment]/[region]/[config-server-path]
 *
 * @author Haakon Dybdahl
 */
public class ProxyRequest {

    private final Method method;
    private final HttpURL requestUri;
    private final Map<String, List<String>> headers;
    private final InputStream requestData;

    private final List<URI> targets;
    private final Path targetPath;

    ProxyRequest(Method method, URI uri, Map<String, List<String>> headers, InputStream body, List<URI> targets, Path path) {
        this.requestUri = HttpURL.from(uri);
        if (     requestUri.path().segments().size() < path.segments().size()
            || ! requestUri.path().tail(path.segments().size()).equals(path)) {
            throw new IllegalArgumentException(Text.format("Request %s does not end with proxy %s", requestUri.path(), path));
        }
        if (targets.isEmpty()) {
            throw new IllegalArgumentException("targets must be non-empty");
        }
        this.method = Objects.requireNonNull(method);
        this.headers = Objects.requireNonNull(headers);
        this.requestData = body;
        this.targets = List.copyOf(targets);
        this.targetPath = path;
    }


    public Method getMethod() {
        return method;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public InputStream getData() {
        return requestData;
    }

    public List<URI> getTargets() {
        return targets;
    }

    public URI createConfigServerRequestUri(URI baseURI) {
        return HttpURL.from(baseURI).withPath(targetPath).withQuery(requestUri.query()).asURI();
    }

    public URI getControllerPrefixUri() {
        Path prefixPath = requestUri.path().cut(targetPath.segments().size()).withTrailingSlash();
        return requestUri.withPath(prefixPath).withQuery(Query.empty()).asURI();
    }

    @Override
    public String toString() {
        return "[targets: " + targets + " request: " + targetPath + "]";
    }

    /** Create a proxy request that repeatedly tries a single target */
    public static ProxyRequest tryOne(URI target, Path path, HttpRequest request) {
        return new ProxyRequest(request.getMethod(), request.getUri(), request.getJDiscRequest().headers(),
                                request.getData(), List.of(target), path);
    }

}
