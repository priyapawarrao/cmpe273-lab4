package edu.sjsu.cmpe.cache.client;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Distributed cache service
 * 
 */
public class DistributedCacheService implements CacheServiceInterface {
    private final String cacheServerUrl;
    int rpcode = -1;
    String value = null;

    public DistributedCacheService(String serverUrl) {
        this.cacheServerUrl = serverUrl;
    }

    /**
     * @see edu.sjsu.cmpe.cache.client.CacheServiceInterface#get(long)
     */
    @Override
    public String get(long key) throws InterruptedException, ExecutionException{
        Future<HttpResponse<JsonNode>> response = null;
 
            response = Unirest.get(this.cacheServerUrl + "/cache/{key}")
                    .header("accept", "application/json")
                    .routeParam("key", Long.toString(key)).asJsonAsync(new Callback<JsonNode>() {

                        public void failed(UnirestException e) {
                            value = "fail";
                        }

                        public void completed(HttpResponse<JsonNode> response) {
                             rpcode = response.getStatus();
                             value = response.getBody().getObject().getString("value");
                            
                             
                        }

                        public void cancelled() {
                            System.out.println("The request has been cancelled");
                            value = "fail";
                        }

                    });
        Thread.sleep(1000 * 3);
        return value;
    }

    /**
     * @throws ExecutionException 
     * @throws InterruptedException 
     * @see edu.sjsu.cmpe.cache.client.CacheServiceInterface#put(long,
     *      java.lang.String)
     */
    @Override
    public int put(long key, String value) throws InterruptedException, ExecutionException {
    	Future<HttpResponse<JsonNode>>response = null;
    	
        response = Unirest
                    .put(this.cacheServerUrl + "/cache/{key}/{value}")
                    .header("accept", "application/json")
                    .routeParam("key", Long.toString(key))
                    .routeParam("value", value)
                    .asJsonAsync(new Callback<JsonNode>() {

                        public void failed(UnirestException e) {
                            rpcode = 0;
                        }

                        public void completed(HttpResponse<JsonNode> response) {
                        	rpcode = response.getStatus();
                        }

                        public void cancelled() {
                            System.out.println("The request has been cancelled");
                            rpcode = 0;
                        }

                    });
           
           Thread.sleep(1000 * 2);//sleep for 2 seconds to get the response from the callback functions
           return rpcode;
  }
    
    @Override
    public void delete(long key) {
        HttpResponse<JsonNode> response = null;
        try {
            response = Unirest.delete(this.cacheServerUrl + "/cache/{key}")
            		   .header("accept", "application/json")
                       .routeParam("key", Long.toString(key))
                       .asJson();
            System.out.println("Delete successful response code: "+response.getStatus());
        } catch (UnirestException e) {
            System.err.println("Exception" + e.getMessage());
        }
    
    }
    
    
}
