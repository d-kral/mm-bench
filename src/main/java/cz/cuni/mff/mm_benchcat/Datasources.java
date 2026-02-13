package cz.cuni.mff.mm_benchcat;

import cz.cuni.mff.mm_benchcat.wrappers.DatasourceWrapper;
import cz.cuni.mff.mm_benchcat.wrappers.Neo4j;

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
                    b.simple(Schema.personId.label(), Schema.person_personId),
                    b.simple(Schema.personFirstName.label(), Schema.person_personFirstName),
                    b.simple(Schema.personLastName.label(), Schema.person_personLastName),
                    b.simple(Schema.personGender.label(), Schema.person_personGender),
                    b.simple(Schema.personBirthday.label(), Schema.person_personBirthday),
                    b.simple(Schema.personEmail.label(), Schema.person_personEmail),
                    b.simple(Schema.personLocation.label(), Schema.person_personLocation)
            )
    );
    private static final DatasourceWrapper.DatasourceMapping post = new DatasourceWrapper.DatasourceMapping(
            Schema.post, postKind,
            b -> b.root(
                    b.simple(Schema.postId.label(), Schema.post_postId),
                    b.simple(Schema.postCreationDate.label(), Schema.post_postCreationDate),
                    b.simple(Schema.postLocation.label(), Schema.post_postLocation),
                    b.simple(Schema.postContent.label(), Schema.post_postContent),
                    b.simple(Schema.postLength.label(), Schema.post_postLength)
            )
    );
    private static final DatasourceWrapper.DatasourceMapping tag = new DatasourceWrapper.DatasourceMapping(
            Schema.tag, tagKind,
            b -> b.root(
                    b.simple(Schema.tagId.label(), Schema.tag_tagId),
                    b.simple(Schema.tagName.label(), Schema.tag_tagName)
            )
    );
    //endregion

    //region relationships mappings
    private static final String hasCreatedKind = "HAS_CREATED";
    private static final String hasInterestKind = "HAS_INTEREST";
    private static final String hasTagKind = "HAS_TAG";

    private static final DatasourceWrapper.DatasourceMapping hasCreated = new DatasourceWrapper.DatasourceMapping(
            Schema.person_hasCreated_post, hasCreatedKind,
            b -> b.root(
                    b.complex(Neo4jNames.from(personKind), Schema.hasCreated_person,
                            b.simple(Schema.personId.label(), Schema.person_personId)),
                    b.complex(Neo4jNames.to(postKind), Schema.hasCreated_post,
                            b.simple(Schema.postId.label(), Schema.post_postId))
            )
    );
    private static final DatasourceWrapper.DatasourceMapping hasInterest = new DatasourceWrapper.DatasourceMapping(
            Schema.person_hasInterest_tag, hasInterestKind,
            b -> b.root(
                    b.complex(Neo4jNames.from(personKind), Schema.hasInterest_person,
                            b.simple(Schema.personId.label(), Schema.person_personId)),
                    b.complex(Neo4jNames.to(tagKind), Schema.hasInterest_tag,
                            b.simple(Schema.tagId.label(), Schema.tag_tagId))
            )
    );
    private static final DatasourceWrapper.DatasourceMapping hasTag = new DatasourceWrapper.DatasourceMapping(
            Schema.post_hasTag_tag, hasTagKind,
            b -> b.root(
                    b.complex(Neo4jNames.from(postKind), Schema.hasTag_post,
                            b.simple(Schema.postId.label(), Schema.post_postId)),
                    b.complex(Neo4jNames.to(tagKind), Schema.hasTag_tag,
                            b.simple(Schema.tagId.label(), Schema.tag_tagId))
            )
    );
    //endregion

    //endregion
}
