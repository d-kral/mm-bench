package cz.cuni.mff.mm_benchcat.wrappers;

import cz.matfyz.abstractwrappers.AbstractControlWrapper;
import cz.matfyz.core.datasource.Datasource;
import cz.matfyz.core.identifiers.Key;
import cz.matfyz.core.identifiers.Signature;
import cz.matfyz.core.mapping.AccessPathBuilder;
import cz.matfyz.core.mapping.ComplexProperty;
import cz.matfyz.core.mapping.Mapping;
import cz.matfyz.core.schema.SchemaCategory;
import cz.matfyz.core.utils.Accessor;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatasourceWrapper<TWrapper extends AbstractControlWrapper> {
    public interface AccessPathCreator {
        ComplexProperty create(AccessPathBuilder builder);
    }

    public interface PrimaryKeyCreator {
        Collection<Signature> create(SchemaCategory schema, Key rootKey);
    }

    public static class DatasourceMapping {
        Key rootKey;
        String kindName;
        ComplexProperty accessPath;
        @Nullable PrimaryKeyCreator keyCreator;

        public DatasourceMapping(Accessor<Key> rootKey, String kindName, AccessPathCreator pathCreator) {
            this(rootKey, kindName, pathCreator, null);
        }

        public DatasourceMapping(Accessor<Key> rootKey, String kindName, AccessPathCreator pathCreator, @Nullable PrimaryKeyCreator keyCreator) {
            this.rootKey = rootKey.access();
            this.kindName = kindName;
            this.accessPath = pathCreator.create(new AccessPathBuilder());
            this.keyCreator = keyCreator;
        }
    }

    public final Datasource datasource;
    public final TWrapper wrapper;
    public final List<Mapping> mappings;
    public final SchemaCategory schema;

    public DatasourceWrapper(Datasource.DatasourceType type, String identifier, TWrapper wrapper, SchemaCategory schema) {
        this.datasource = new Datasource(type, identifier);
        this.wrapper = wrapper;
        this.mappings = new ArrayList<>();
        this.schema = schema;
    }

    public void addMappings(DatasourceMapping... mappings) {
        for (var m : mappings) {
            this.mappings.add(m.keyCreator != null
                    ? new Mapping(datasource, m.kindName, schema, m.rootKey, m.accessPath, m.keyCreator.create(schema, m.rootKey))
                    : Mapping.create(datasource, m.kindName, schema, m.rootKey, m.accessPath));
        }
    }
}
