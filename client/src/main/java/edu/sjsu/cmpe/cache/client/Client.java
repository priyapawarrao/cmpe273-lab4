package edu.sjsu.cmpe.cache.client;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("Starting Cache Client...");
        CacheServiceInterface cache1 = new DistributedCacheService(
                "http://localhost:3000");
        CacheServiceInterface cache2 = new DistributedCacheService(
                "http://localhost:3001");
        CacheServiceInterface cache3 = new DistributedCacheService(
                "http://localhost:3002");
        
       int ret1 = cache1.put(1, "a");
       int ret2 = cache2.put(1, "a");
       int ret3 = cache3.put(1, "a");
        
       System.out.println("Response codes from PUT: cache1 : "+ ret1+ " cache2: "+ ret2 + " cache3: "+ ret3);
        
       if(ret1==0 && ret2 == 0 && ret3 == 200)
    	   cache3.delete(1);// we can insert data once again to all servers 
       else if(ret1==0 && ret2 == 200 && ret3 == 0)
    	   cache2.delete(1);
       else if(ret1==200 && ret2 == 0 && ret3 == 0)
    	   cache1.delete(1);
    }

}
