package cz.cuni.mff.mm_benchcat.wrappers;

import cz.matfyz.core.datasource.Datasource;
import cz.matfyz.core.schema.SchemaCategory;
import cz.matfyz.core.utils.Config;
import cz.matfyz.wrapperpostgresql.PostgreSQLControlWrapper;
import cz.matfyz.wrapperpostgresql.PostgreSQLProvider;
import org.checkerframework.checker.nullness.qual.Nullable;

public class PostgreSQL {
    public PostgreSQL(Config config) { this.config = config; }

    private final Config config;
    private PostgreSQLProvider provider;

    private PostgreSQLProvider getProvider(@Nullable String database) {
        if (provider == null) {
            provider = new PostgreSQLProvider(new PostgreSQLProvider.PostgreSQLSettings(
                    config.get("host"),
                    config.get("port"),
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

    public DatasourceWrapper<PostgreSQLControlWrapper> createWrapper(String identifier, SchemaCategory schema) {
        return createWrapper(identifier, schema, null);
    }

    public DatasourceWrapper<PostgreSQLControlWrapper> createWrapper(String identifier, SchemaCategory schema, @Nullable String database) {
        final var wrapper = new PostgreSQLControlWrapper(getProvider(database), identifier);
        return new DatasourceWrapper<>(Datasource.DatasourceType.postgresql, identifier, wrapper, schema);
    }
}
