package com.clarence.ToolHelper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.Calendar;
import java.util.UUID;

public class Cooldown {
    private Calendar calendar = Calendar.getInstance();
    private Cache<UUID, Long> cooldown = CacheBuilder.newBuilder().expireAfterWrite(5, 0).build();
}
