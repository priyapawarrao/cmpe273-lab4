package edu.sjsu.cmpe.cache.client;

import java.util.concurrent.ExecutionException;

/**
 * Cache Service Interface
 * 
 */
public interface CacheServiceInterface {
    public String get(long key) throws InterruptedException, ExecutionException;

    public int put(long key, String value) throws InterruptedException, ExecutionException;

    public void delete(long key);
}