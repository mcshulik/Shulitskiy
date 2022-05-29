package logic;

import letscode.Laba1.entity.SolutionParameters;
import letscode.Laba1.logger.MyLogger;
import org.apache.logging.log4j.Level;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CacheService {
    private static final HashMap<SolutionParameters, Double> cache = new HashMap<>();

    public boolean isContain(SolutionParameters key) {
        return cache.containsKey(key);
    }

    public void addToMap(SolutionParameters key, Double percent) {
        cache.put(key, percent);
        MyLogger.log(Level.INFO, "Value " + key + "@" + percent + " added to cache!");
    }

    public Double getParameters(SolutionParameters key) {
        return cache.get(key);
    }
    public HashMap<SolutionParameters, Double> getCache() {
        return this.cache;
    }
}