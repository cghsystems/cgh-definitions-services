package net.cghsystems.definitions.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

@EqualsAndHashCode
@TupleConstructor
class Definition {
    String id, name, definition, description
    Long definitionCategoryId
}