package cz.cuni.mff.mm_benchcat;

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

    // TODO: morphism cardinality

    //region Graph
    public static final BuilderObjex
        person = builder.objex("person"),
        personId = builder.objex("id"),
        personFirstName = builder.objex("first name"),
        personLastName = builder.objex("last name"),
        personGender = builder.objex("gender"),
        personBirthday = builder.objex("birthday"),
        personEmail = builder.objex("email"),
        personLocation = builder.objex("location"),

        post = builder.objex("post"),
        postId = builder.objex("id"),
        postCreationDate = builder.objex("creation date"),
        postLocation = builder.objex("location"),
        postContent = builder.objex("content"),
        postLength = builder.objex("length"),

        tag = builder.objex("tag"),
        tagId = builder.objex("id"),
        tagName = builder.objex("name");

    public static final BuilderMorphism
        personHasId = builder.morphism(person, personId),
        personHasFirstName = builder.morphism(person, personFirstName),
        personHasLastName = builder.morphism(person, personLastName),
        personHasGender = builder.morphism(person, personGender),
        personHasBirthday = builder.morphism(person, personBirthday),
        personHasEmail = builder.morphism(person, personEmail),
        personHasLocation = builder.morphism(person, personLocation),

        postHasId = builder.morphism(post, postId),
        postHasCreationDate = builder.morphism(post, postCreationDate),
        postHasLocation = builder.morphism(post, postLocation),
        postHasContent = builder.morphism(post, postContent),
        postHasLength = builder.morphism(post, postLength),

        tagHasId = builder.morphism(tag, tagId),
        tagHasName = builder.morphism(tag, tagName);

    static  {
        builder
            .ids(person, personHasId)
            .ids(post, postHasId)
            .ids(tag, tagHasId);
    }
    //endregion

    public static final BuilderMorphism
            HasCreated = builder.tags(Tag.role).morphism(person, post),
            hasInterest = builder.tags(Tag.role).morphism(person, tag),
            HasTag = builder.tags(Tag.role).morphism(post, tag);
}
