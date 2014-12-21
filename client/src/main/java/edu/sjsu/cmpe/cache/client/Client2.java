package edu.sjsu.cmpe.cache.client;

public class Client2 {

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
        
       System.out.println("Response codes for PUT: cache1 : "+ ret1+ " cache2: "+ ret2 + " cache3: "+ ret3);
       System.out.println(" Step 1: Sleep for 30 seconds: Stop the server");
       Thread.sleep(1000 * 30);
       
       ret1 = cache1.put(1, "b");
       ret2 = cache2.put(1, "b");
       ret3 = cache3.put(1, "b");
      
       System.out.println("Response codes for PUT: cache1 : "+ ret1+ " cache2: "+ ret2 + " cache3: "+ ret3);
       System.out.println("Step 2: Sleep for 30 seconds: Start the server");
       Thread.sleep(1000 * 30);
       
       String ret_get1 = cache1.get(1);
       String ret_get2 = cache2.get(1);
       String ret_get3 = cache3.get(1);
       
       System.out.println("Response codes for GET : cache1 : "+ ret_get1+ " cache2: "+ ret_get2 + " cache3: "+ ret_get3);
       
       if(ret_get1 != null && ret_get2 != null)
       {
	       if(ret_get1.equals("b") && ret_get2.equals("b") && null == ret_get3)
	        {
	          cache3.put(1, "b");
	          ret_get3 = cache3.get(1);
	        }
       }
       else if(ret_get1 != null && ret_get3 != null)
       {
    	   if(ret_get1.equals("b") && null == ret_get2 && ret_get3.equals("b"))
    	   	{
    		   cache2.put(1, "b");
    		   ret_get2 = cache2.get(1);
    	   	}
       }
       else if(ret_get2 != null && ret_get3 != null)
       {
			if(null == ret_get1  && ret_get2.equals("b") && ret_get3.equals("b"))
			  {
				cache1.put(1, "b");
				ret_get1 = cache1.get(1);
			  }
       }
       
       System.out.println("Response codes for GET : cache1 : "+ ret_get1+ " cache2: "+ ret_get2 + " cache3: "+ ret_get3);
       
    }

}
