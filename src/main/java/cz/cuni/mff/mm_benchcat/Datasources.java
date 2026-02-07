package cz.cuni.mff.mm_benchcat;

import cz.matfyz.core.identifiers.Signature;
import cz.matfyz.core.schema.SchemaCategory;
import cz.matfyz.core.utils.Config;
import cz.matfyz.wrapperneo4j.Neo4jControlWrapper;
import cz.matfyz.wrapperneo4j.Neo4jControlWrapper.Neo4jNames;

import java.util.List;

public class Datasources {
    public final SchemaCategory schema = Schema.newSchema();
    public final List<DatasourceWrapper<?>> datasources = List.of(
            neo4j()
    );

    //region Graph
    private DatasourceWrapper<Neo4jControlWrapper> neo4j;

    public DatasourceWrapper<Neo4jControlWrapper> neo4j() {
        if (neo4j == null) {
            neo4j = new Neo4j(new Config("neo4j")).createWrapper("neo4j", schema);
            neo4j.addMappings(person, post, tag, hasCreated, hasInterest, hasTag);
        }

        return neo4j;
    }

    //region nodes mappings
    private static final String personKind = "Person";
    private static final String postKind = "Post";
    private static final String tagKind = "Tag";

    private static final DatasourceWrapper.DatasourceMapping person = new DatasourceWrapper.DatasourceMapping(
            Schema.person, personKind,
            b -> b.root(
                    b.simple(Schema.personId.label(), Schema.personHasId),
                    b.simple(Schema.personFirstName.label(), Schema.personHasFirstName),
                    b.simple(Schema.personLastName.label(), Schema.personHasLastName),
                    b.simple(Schema.personGender.label(), Schema.personHasGender),
                    b.simple(Schema.personBirthday.label(), Schema.personHasBirthday),
                    b.simple(Schema.personEmail.label(), Schema.personHasEmail),
                    b.simple(Schema.personLocation.label(), Schema.personHasLocation)
            )
    );
    private static final DatasourceWrapper.DatasourceMapping post = new DatasourceWrapper.DatasourceMapping(
            Schema.post, postKind,
            b -> b.root(
                    b.simple(Schema.postId.label(), Schema.postHasId),
                    b.simple(Schema.postCreationDate.label(), Schema.postHasCreationDate),
                    b.simple(Schema.postLocation.label(), Schema.postHasLocation),
                    b.simple(Schema.postContent.label(), Schema.postHasContent),
                    b.simple(Schema.postLength.label(), Schema.postHasLength)
            )
    );
    private static final DatasourceWrapper.DatasourceMapping tag = new DatasourceWrapper.DatasourceMapping(
            Schema.tag, tagKind,
            b -> b.root(
                    b.simple(Schema.tagId.label(), Schema.tagHasId),
                    b.simple(Schema.tagName.label(), Schema.tagHasName)
            )
    );
    //endregion

    //region relationships mappings
    private static final String hasCreatedKind = "HAS_CREATED";
    private static final String hasInterestKind = "HAS_INTEREST";
    private static final String hasTagKind = "HAS_TAG";

    private static final DatasourceWrapper.DatasourceMapping hasCreated = new DatasourceWrapper.DatasourceMapping(
            Schema.person, hasCreatedKind,
            b -> b.root(
                    b.complex(Neo4jNames.from(personKind), Signature.empty(),
                            b.simple(Schema.personId.label(), Schema.personHasId)),
                    b.complex(Neo4jNames.to(postKind), Schema.hasCreated,
                            b.simple(Schema.postId.label(), Schema.postHasId))
            )
    );
    private static final DatasourceWrapper.DatasourceMapping hasInterest = new DatasourceWrapper.DatasourceMapping(
            Schema.person, hasInterestKind,
            b -> b.root(
                    b.complex(Neo4jNames.from(personKind), Signature.empty(),
                            b.simple(Schema.personId.label(), Schema.personHasId)),
                    b.complex(Neo4jNames.to(tagKind), Schema.hasInterest,
                            b.simple(Schema.tagId.label(), Schema.tagHasId))
            )
    );
    private static final DatasourceWrapper.DatasourceMapping hasTag = new DatasourceWrapper.DatasourceMapping(
            Schema.post, hasTagKind,
            b -> b.root(
                    b.complex(Neo4jNames.from(postKind), Signature.empty(),
                            b.simple(Schema.postId.label(), Schema.postHasId)),
                    b.complex(Neo4jNames.to(tagKind), Schema.hasTag,
                            b.simple(Schema.tagId.label(), Schema.tagHasId))
            )
    );
    //endregion

    //endregion
}
