package cz.cuni.mff.mm_benchcat;

import cz.matfyz.abstractwrappers.AbstractControlWrapper;
import cz.matfyz.core.datasource.Datasource;
import cz.matfyz.core.mapping.Mapping;

import java.util.ArrayList;
import java.util.List;

public class DatasourceWrapper<TWrapper extends AbstractControlWrapper> {
    public final Datasource datasource;
    public final TWrapper wrapper;
    public final List<Mapping> mappings;

    public DatasourceWrapper(TWrapper wrapper, Datasource.DatasourceType type, String identifier) {
        this.datasource = new Datasource(type, identifier);
        this.wrapper = wrapper;
        this.mappings = new ArrayList<>();
    }

    public void addMappings(DatasourceMapping... mappings) {
        for (var mapping : mappings)
            this.mappings.add(mapping.mapping());
    }
}
