package com.wd.api.service;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

import static java.util.concurrent.TimeUnit.MINUTES;

@Service
public class LoginAttemptService {
    public static final int MAXIMUM_NUMBER_OF_ATTEMPTS= 5;
    public static final int ATTEMPT_INCREMENT= 1;
    private LoadingCache<String,Integer> loadingCache;

   public LoginAttemptService(){
       super();
       loadingCache = CacheBuilder.newBuilder().expireAfterWrite(15,MINUTES).maximumSize(100).build(new CacheLoader<String, Integer>() {
                   @Override
                   public Integer load(String s) throws Exception {
                      return 0;
                   }
               });
   }

    public void evicUserFromLogingAttemptCache(String username){
       loadingCache.invalidate(username);
    }
    public void addUserToLogingAttempt(String username)  {
       int attempts = 0;
        try {
            attempts =ATTEMPT_INCREMENT + loadingCache.get(username);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        loadingCache.put(username,attempts);
    }

    public boolean hasExceededMaxAttempts(String username)  {
        try {
            return loadingCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPTS;
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
