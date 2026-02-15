package cz.cuni.mff.mm_benchcat.wrappers;

import cz.matfyz.core.datasource.Datasource;
import cz.matfyz.core.schema.SchemaCategory;
import cz.matfyz.core.utils.Config;
import cz.matfyz.wrappermongodb.MongoDBControlWrapper;
import cz.matfyz.wrappermongodb.MongoDBProvider;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MongoDB {
    public MongoDB(Config config) { this.config = config; }

    private final Config config;
    private MongoDBProvider provider;

    private MongoDBProvider getProvider(@Nullable String database) {
        if (provider == null) {
            provider = new MongoDBProvider(new MongoDBProvider.MongoDBSettings(
                    config.get("host"),
                    config.get("port"),
                    config.get("authDatabase"),
                    database != null ? database : config.get("database"),
                    config.get("username"),
                    config.get("password"),
                    true,
                    true,
                    false
            ));
        }

        return provider;
    }

    public DatasourceWrapper<MongoDBControlWrapper> createWrapper(String identifier, SchemaCategory schema) {
        return createWrapper(identifier, schema, null);
    }

    public DatasourceWrapper<MongoDBControlWrapper> createWrapper(String identifier, SchemaCategory schema, @Nullable String database) {
        final var wrapper = new MongoDBControlWrapper(getProvider(database), identifier);
        return new DatasourceWrapper<>(Datasource.DatasourceType.postgresql, identifier, wrapper, schema);
    }
}
