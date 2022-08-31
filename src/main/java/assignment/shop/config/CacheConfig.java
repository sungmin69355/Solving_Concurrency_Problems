package assignment.shop.config;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
@EnableCaching
public class CacheConfig {
    /**
     * 60초마다 캐시 삭제
     * Disk, offheap 사용 x
     * EhCache 설정
     */
    @Bean
    public EhCacheManagerFactoryBean cacheManagerFactoryBean() {
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setShared(true);
        return ehCacheManagerFactoryBean;
    }

    @Bean
    public EhCacheCacheManager ehCacheCacheManager() {
        CacheConfiguration ItemsCacheConfiguration = new CacheConfiguration()
                .name("getItems")
                .maxEntriesLocalHeap(1000)
                .eternal(false)
                .timeToIdleSeconds(0)
                .timeToLiveSeconds(60)
                .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LRU);

        Cache ItemsCache = new Cache(ItemsCacheConfiguration);

        if (!Objects.requireNonNull(cacheManagerFactoryBean().getObject()).cacheExists("getItems")) {
            Objects.requireNonNull(cacheManagerFactoryBean().getObject()).addCache(ItemsCache);
        }

        return new EhCacheCacheManager(Objects.requireNonNull(cacheManagerFactoryBean().getObject()));
    }
}
