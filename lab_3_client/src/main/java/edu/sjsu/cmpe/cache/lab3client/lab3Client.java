package edu.sjsu.cmpe.cache.lab3client;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;

public class lab3Client 
{
	private static List<String> getNodes() 
	{
		List<String> nList = Lists.newArrayList();
		for(int i = 0 ; i < 3; i ++) {
			nList.add("http://localhost:300"+i);
		}
		return nList;
	}
	
	public static void main(String[] args) 
	{
        String lab3Server = null;
        CacheServiceInterface cacheInt = null;
        String[] data =  {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"};
        List<String> nList = getNodes();
    	      
        @SuppressWarnings({"unchecked","rawtypes"})
        ConsistentHash<String> consistentHashing = new ConsistentHash(
        		Hashing.md5(), nList);
                     
        System.out.println("Input:: Hash consistent");
        for (int i=0; i<data.length; i++) 
        {
			int key = i + 1;
			lab3Server = consistentHashing.getNode("" + key);					
			cacheInt = new DistributedCacheService(lab3Server);
	        cacheInt.put(key, data[i]);
	        System.out.println( key + "=>" + data[i] + "=>" + lab3Server);
		}
		
        System.out.println("Output:: Hash consistent");
        for (int i=0; i<data.length; i++) 
        {
			int key = i + 1;
			lab3Server = consistentHashing.getNode("" + key);					
			cacheInt = new DistributedCacheService(lab3Server);
			System.out.println( key + "=>" + data[i] + "=>" + lab3Server);
		}    			
	}
}