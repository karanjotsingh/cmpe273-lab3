package edu.sjsu.cmpe.cache.lab3client;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

import com.google.common.hash.HashFunction;

public class ConsistentHash<T> {
    private final HashFunction hashFunc;
    private final SortedMap<Long, T> sMap = new TreeMap<Long, T>();

    public ConsistentHash(HashFunction hashFunction, Collection<T> nodes) {
        this.hashFunc = hashFunction;

        for (T node : nodes) {
            addNode(node);
        }
    }

    public void addNode(T node) {
        sMap.put(hashFunc.hashString(node.toString(), Charset.defaultCharset()).asLong(), node);        
    }

    public void removeNode(T node) {
    	sMap.remove(hashFunc.hashString(node.toString(), Charset.defaultCharset()).asLong());        
    }

    public T getNode(Object key) {
        if (sMap.isEmpty()) {
            return null;
        }
        long hash = hashFunc.hashString(key.toString(), Charset.defaultCharset()).asLong();
        if (!sMap.containsKey(hash)) {
            SortedMap<Long, T> sMap2 = sMap.tailMap(hash);
            hash = sMap2.isEmpty() ? sMap.firstKey() : sMap2.firstKey();
        }
        return sMap.get(hash);
    }
}