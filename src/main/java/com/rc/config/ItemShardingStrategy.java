package com.rc.config;

import io.shardingsphere.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

public class ItemShardingStrategy implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> preciseShardingValue) {

        Long suffix = preciseShardingValue.getValue();
        if (suffix == null) {
            suffix = 0L;
        }

        suffix = suffix % 4;

        final String targetTable = "sh_item_" + suffix;

        return targetTable;
    }
}
