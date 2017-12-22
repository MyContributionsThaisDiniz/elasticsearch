/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.ml.job.process.autodetect.params;

import org.elasticsearch.Build;
import org.elasticsearch.common.Nullable;
import org.elasticsearch.xpack.ml.calendars.SpecialEvent;
import org.elasticsearch.xpack.ml.job.config.MlFilter;
import org.elasticsearch.xpack.ml.job.process.autodetect.state.DataCounts;
import org.elasticsearch.xpack.ml.job.process.autodetect.state.ModelSizeStats;
import org.elasticsearch.xpack.ml.job.process.autodetect.state.ModelSnapshot;
import org.elasticsearch.xpack.ml.job.process.autodetect.state.Quantiles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class AutodetectParams {

    private final DataCounts dataCounts;
    private final ModelSizeStats modelSizeStats;
    @Nullable
    private final ModelSnapshot modelSnapshot;
    @Nullable
    private final Quantiles quantiles;
    private final Set<MlFilter> filters;
    private final List<SpecialEvent> specialEvents;


    private AutodetectParams(DataCounts dataCounts, ModelSizeStats modelSizeStats,
                             @Nullable ModelSnapshot modelSnapshot,
                             @Nullable Quantiles quantiles, Set<MlFilter> filters,
                             List<SpecialEvent> specialEvents) {
        this.dataCounts = Objects.requireNonNull(dataCounts);
        this.modelSizeStats = Objects.requireNonNull(modelSizeStats);
        this.modelSnapshot = modelSnapshot;
        this.quantiles = quantiles;
        this.filters = filters;
        this.specialEvents = specialEvents;
    }

    public DataCounts dataCounts() {
        return dataCounts;
    }

    public ModelSizeStats modelSizeStats() {
        return modelSizeStats;
    }

    @Nullable
    public ModelSnapshot modelSnapshot() {
        return modelSnapshot;
    }

    @Nullable
    public Quantiles quantiles() {
        return quantiles;
    }

    public Set<MlFilter> filters() {
        return filters;
    }

    public List<SpecialEvent> specialEvents() {
        return specialEvents;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof AutodetectParams == false) {
            return false;
        }

        AutodetectParams that = (AutodetectParams) other;

        return Objects.equals(this.dataCounts, that.dataCounts)
                && Objects.equals(this.modelSizeStats, that.modelSizeStats)
                && Objects.equals(this.modelSnapshot, that.modelSnapshot)
                && Objects.equals(this.quantiles, that.quantiles)
                && Objects.equals(this.filters, that.filters)
                && Objects.equals(this.specialEvents, that.specialEvents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataCounts, modelSizeStats, modelSnapshot, quantiles, filters, specialEvents);
    }

    public static class Builder {

        private DataCounts dataCounts;
        private ModelSizeStats modelSizeStats;
        private ModelSnapshot modelSnapshot;
        private Quantiles quantiles;
        private Set<MlFilter> filters;
        private List<SpecialEvent> specialEvents;

        public Builder(String jobId) {
            dataCounts = new DataCounts(jobId);
            modelSizeStats = new ModelSizeStats.Builder(jobId).build();
            filters = new HashSet<>();
            specialEvents = new ArrayList<>();
        }

        public Builder setDataCounts(DataCounts dataCounts) {
            this.dataCounts = dataCounts;
            return this;
        }

        public DataCounts getDataCounts() {
            return dataCounts;
        }

        public Builder setModelSizeStats(ModelSizeStats modelSizeStats) {
            this.modelSizeStats = modelSizeStats;
            return this;
        }

        public Builder setModelSnapshot(ModelSnapshot modelSnapshot) {
            this.modelSnapshot = modelSnapshot;
            return this;
        }

        public Builder setQuantiles(Quantiles quantiles) {
            this.quantiles = quantiles;
            return this;
        }

        public Builder addSpecialEvent(SpecialEvent specialEvent) {
            specialEvents.add(specialEvent);
            return this;
        }
        public Builder setSpecialEvents(List<SpecialEvent> specialEvents) {
            this.specialEvents = specialEvents;
            return this;
        }

        public Builder addFilter(MlFilter filter) {
            filters.add(filter);
            return this;
        }

        public Builder setFilters(Set<MlFilter> filters) {
            filters = Objects.requireNonNull(filters);
            return this;
        }

        public AutodetectParams build() {
            return new AutodetectParams(dataCounts, modelSizeStats, modelSnapshot, quantiles,
                    filters, specialEvents);
        }
    }
}
