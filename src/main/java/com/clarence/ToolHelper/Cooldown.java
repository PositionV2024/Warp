package com.clarence.ToolHelper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Cooldown {
    private static final Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(Configuration.Configuration.getLong("Cooldown (reload the server to apply charges)"), TimeUnit.SECONDS).build();
    public static Cache<UUID, Long> getCooldown() {
        return cooldown;
    }
}
