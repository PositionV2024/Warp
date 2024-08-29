package com.clarence.ToolHelper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Cooldown {
    private static final long duration = 5;
    private static final Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(duration, TimeUnit.SECONDS).build();
    public static Cache<UUID, Long> getCooldown() {
        return cooldown;
    }
}
