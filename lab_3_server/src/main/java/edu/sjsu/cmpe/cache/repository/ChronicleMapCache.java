package edu.sjsu.cmpe.cache.repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.ArrayList;
import java.util.List;

import net.openhft.chronicle.map.ChronicleMap;
import edu.sjsu.cmpe.cache.domain.Entry;

public class ChronicleMapCache implements CacheInterface {
    private final ChronicleMap<Long, Entry> cmc;

    public ChronicleMapCache(ChronicleMap<Long, Entry> values) {
        cmc = values;
    }

    @Override
    public Entry save(Entry latEntry) {
        checkNotNull(latEntry, "Latest Entry Not be NULL");
        cmc.putIfAbsent(latEntry.getKey(), latEntry);

        return latEntry;
    }

    @Override
    public Entry get(Long key) {
        checkArgument(key > 0, "Key was %s . Expected to be more than zero", key);
        return cmc.get(key);
    }

    @Override
    public List<Entry> getAll() {
        return new ArrayList<Entry>(cmc.values());
    }
}
