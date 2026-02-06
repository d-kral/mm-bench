package cz.cuni.mff.mm_benchcat;

import cz.matfyz.core.datasource.Datasource;
import cz.matfyz.core.utils.Config;
import cz.matfyz.wrapperneo4j.Neo4jControlWrapper;
import cz.matfyz.wrapperneo4j.Neo4jProvider;
import org.checkerframework.checker.nullness.qual.Nullable;

public class Neo4j {
    public Neo4j(Config config) {
        this.config = config;
    }

    private final Config config;
    private Neo4jProvider provider;

    private Neo4jProvider getProvider(String database) {
        if (provider == null) {
            provider = new Neo4jProvider(new Neo4jProvider.Neo4jSettings(
                    config.get("host"),
                    config.get("port"),
                    database,
                    config.get("username"),
                    config.get("password"),
                    true,
                    true,
                    false
            ));
        }

        return provider;
    }

    public DatasourceWrapper<Neo4jControlWrapper> createWrapper(String identifier) {
        return createWrapper(identifier, null);
    }

    public DatasourceWrapper<Neo4jControlWrapper> createWrapper(String identifier, @Nullable String database) {
        if (database == null) database = "neo4j";
        final var wrapper = new Neo4jControlWrapper(getProvider(database), identifier);
        return new DatasourceWrapper<>(wrapper, Datasource.DatasourceType.neo4j, identifier);
    }
}
