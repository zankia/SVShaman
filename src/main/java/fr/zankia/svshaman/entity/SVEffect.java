package fr.zankia.svshaman.entity;

import java.util.Map;

public class SVEffect {
    private final int duration;
    private final int amplifier;
    private final long timeout;

    public SVEffect(Map<String, Integer> properties) {
        this.duration = properties.get("duration") * 20;
        this.amplifier = properties.get("amplifier") - 1;
        this.timeout = properties.get("timeout") * 20L;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public long getTimeout() {
        return timeout;
    }
}
