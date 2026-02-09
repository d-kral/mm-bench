package cz.cuni.mff.mm_benchcat;

import cz.matfyz.core.identifiers.Signature;
import cz.matfyz.core.schema.SchemaBuilder;
import cz.matfyz.core.schema.SchemaBuilder.BuilderMorphism;
import cz.matfyz.core.schema.SchemaBuilder.BuilderObjex;
import cz.matfyz.core.schema.SchemaCategory;
import cz.matfyz.core.schema.SchemaMorphism.Tag;
import cz.matfyz.core.metadata.MetadataCategory;

public class Schema {
    private Schema() {}

    public static SchemaCategory newSchema() {
        return builder.build();
    }

    public static MetadataCategory newMetadata(SchemaCategory schema) {
        return builder.buildMetadata(schema);
    }

    private static final SchemaBuilder builder = new SchemaBuilder();

    //region Graph
    public static final BuilderObjex
            person = builder.objex("person"),
            personId = builder.objex("id"),
            personFirstName = builder.objex("firstName"),
            personLastName = builder.objex("lastName"),
            personGender = builder.objex("gender"),
            personBirthday = builder.objex("birthday"),
            personEmail = builder.objex("email"),
            personLocation = builder.objex("location"),

            post = builder.objex("post"),
            postId = builder.objex("id"),
            postCreationDate = builder.objex("creationDate"),
            postLocation = builder.objex("location"),
            postContent = builder.objex("content"),
            postLength = builder.objex("length"),

            tag = builder.objex("tag"),
            tagId = builder.objex("id"),
            tagName = builder.objex("name"),

            person_hasCreated_post = builder.objex("hasCreated"),
            person_hasInterest_tag = builder.objex("hasInterest"),
            post_hasTag_tag = builder.objex("hasTag");

    public static final BuilderMorphism
            person_personId = builder.morphism(person, personId),
            person_personFirstName = builder.morphism(person, personFirstName),
            person_personLastName = builder.morphism(person, personLastName),
            person_personGender = builder.morphism(person, personGender),
            person_personBirthday = builder.morphism(person, personBirthday),
            person_personEmail = builder.morphism(person, personEmail),
            person_personLocation = builder.morphism(person, personLocation),

            post_postId = builder.morphism(post, postId),
            post_postCreationDate = builder.morphism(post, postCreationDate),
            post_postLocation = builder.morphism(post, postLocation),
            post_postContent = builder.morphism(post, postContent),
            post_postLength = builder.morphism(post, postLength),

            tag_tagId = builder.morphism(tag, tagId),
            tag_tagName = builder.morphism(tag, tagName),

            hasCreated_person = builder.tags(Tag.role).morphism(person_hasCreated_post, person),
            hasCreated_post = builder.tags(Tag.role).morphism(person_hasCreated_post, post),
            hasInterest_person = builder.tags(Tag.role).morphism(person_hasInterest_tag, person),
            hasInterest_tag = builder.tags(Tag.role).morphism(person_hasInterest_tag, tag),
            hasTag_post = builder.tags(Tag.role).morphism(post_hasTag_tag, post),
            hasTag_tag = builder.tags(Tag.role).morphism(post_hasTag_tag, tag);

    public static final Signature
            hasCreated_personId = builder.concatenate(hasCreated_person, person_personId),
            hasCreated_postId = builder.concatenate(hasCreated_post, post_postId),
            hasInterest_personId = builder.concatenate(hasInterest_person, person_personId),
            hasInterest_tagId = builder.concatenate(hasInterest_tag, tag_tagId),
            hasTag_postId = builder.concatenate(hasTag_post, post_postId),
            hasTag_tagId = builder.concatenate(hasTag_tag, tag_tagId);

    static  {
        builder
                .ids(person, person_personId)
                .ids(post, post_postId)
                .ids(tag, tag_tagId)

                .ids(person_hasCreated_post, hasCreated_personId, hasCreated_postId)
                .ids(person_hasInterest_tag, hasInterest_personId, hasInterest_tagId)
                .ids(post_hasTag_tag, hasTag_postId, hasTag_tagId);
    }
    //endregion
}
