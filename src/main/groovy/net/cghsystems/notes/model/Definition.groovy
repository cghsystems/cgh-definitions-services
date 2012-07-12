package net.cghsystems.notes.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

@ToString
@EqualsAndHashCode
@TupleConstructor
class Definition {
    String id, name, definition, description
    Long definitionCategoryId
}