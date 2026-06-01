package vn.huy.quanlydaotao.ui.video;

import android.content.Context;
import android.net.Uri;
import com.google.android.exoplayer2.database.StandaloneDatabaseProvider;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.FileDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheDataSink;
import com.google.android.exoplayer2.upstream.cache.CacheDataSource;
import com.google.android.exoplayer2.upstream.cache.CacheWriter;
import com.google.android.exoplayer2.upstream.cache.ContentMetadata;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DownloadCacheManager {

    private static DownloadCacheManager instance;
    private SimpleCache simpleCache;
    private CacheDataSource.Factory cacheDataSourceFactory;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);
    private final Map<String, Future<?>> activeDownloads = new HashMap<>();

    private DownloadCacheManager(Context context) {
        if (simpleCache == null) {
            File cacheDir = new File(context.getCacheDir(), "video_lessons_cache");
            LeastRecentlyUsedCacheEvictor evictor = new LeastRecentlyUsedCacheEvictor(500 * 1024 * 1024);
            StandaloneDatabaseProvider databaseProvider = new StandaloneDatabaseProvider(context);
            simpleCache = new SimpleCache(cacheDir, evictor, databaseProvider);
        }

        DefaultHttpDataSource.Factory httpFactory = new DefaultHttpDataSource.Factory()
                .setAllowCrossProtocolRedirects(true);

        cacheDataSourceFactory = new CacheDataSource.Factory()
                .setCache(simpleCache)
                .setUpstreamDataSourceFactory(httpFactory)
                .setCacheReadDataSourceFactory(new FileDataSource.Factory())
                .setCacheWriteDataSinkFactory(new CacheDataSink.Factory().setCache(simpleCache))
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR);
    }

    public static synchronized DownloadCacheManager getInstance(Context context) {
        if (instance == null) {
            instance = new DownloadCacheManager(context.getApplicationContext());
        }
        return instance;
    }

    public CacheDataSource.Factory getCacheDataSourceFactory() {
        return cacheDataSourceFactory;
    }

    public synchronized void eagerCacheVideo(String videoUrl) {
        if (videoUrl == null || videoUrl.isEmpty()) return;
        if (activeDownloads.containsKey(videoUrl)) return;

        Future<?> future = executorService.submit(() -> {
            try {
                DataSpec dataSpec = new DataSpec(Uri.parse(videoUrl));
                CacheWriter cacheWriter = new CacheWriter(
                        cacheDataSourceFactory.createDataSource(),
                        dataSpec,
                        null,
                        null
                );
                cacheWriter.cache();
                synchronized (DownloadCacheManager.this) {
                    activeDownloads.remove(videoUrl);
                }
            } catch (Exception e) {
                synchronized (DownloadCacheManager.this) {
                    activeDownloads.remove(videoUrl);
                }
            }
        });

        activeDownloads.put(videoUrl, future);
    }

    public synchronized void cancelDownload(String videoUrl) {
        if (videoUrl != null && activeDownloads.containsKey(videoUrl)) {
            Future<?> future = activeDownloads.get(videoUrl);
            if (future != null) {
                future.cancel(true);
            }
            activeDownloads.remove(videoUrl);
        }
    }

    public long getCachedBytes(String videoUrl) {
        if (videoUrl == null || videoUrl.isEmpty()) return 0;
        return simpleCache.getCachedBytes(videoUrl, 0, Long.MAX_VALUE);
    }

    public int getCachePercentage(String videoUrl) {
        if (videoUrl == null || videoUrl.isEmpty()) return 0;
        long cachedBytes = simpleCache.getCachedBytes(videoUrl, 0, Long.MAX_VALUE);
        long totalBytes = simpleCache.getContentMetadata(videoUrl).get(ContentMetadata.KEY_CONTENT_LENGTH, -1);
        if (totalBytes <= 0) return 0;
        return (int) ((cachedBytes * 100) / totalBytes);
    }

    public boolean isVideoFullyCached(String videoUrl) {
        if (videoUrl == null || videoUrl.isEmpty()) return false;
        long cachedBytes = simpleCache.getCachedBytes(videoUrl, 0, Long.MAX_VALUE);
        long totalBytes = simpleCache.getContentMetadata(videoUrl).get(ContentMetadata.KEY_CONTENT_LENGTH, -1);
        return totalBytes > 0 && cachedBytes >= totalBytes;
    }

    public synchronized void clearCache() {
        try {
            for (String key : simpleCache.getKeys()) {
                simpleCache.removeResource(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}