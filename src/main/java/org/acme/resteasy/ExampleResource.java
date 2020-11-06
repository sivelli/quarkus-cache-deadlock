package org.acme.resteasy;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import io.quarkus.cache.CacheResult;

@ApplicationScoped
class ExampleCache {
    @CacheResult(cacheName = "test")
    public Integer getLength(String param) {
        return param.length();
    }
    
}

@Path("/resteasy/hello")
public class ExampleResource {
    @Inject
    ExampleCache cache;

    AtomicInteger counter = new AtomicInteger(); 

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@QueryParam("name") String name) {
        System.out.println("request received " + counter.incrementAndGet());
        return "hello " + name + " Length:" + cache.getLength(Objects.toString(name, ""));
    }
}
