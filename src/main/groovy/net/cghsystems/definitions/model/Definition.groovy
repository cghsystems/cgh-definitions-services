package net.cghsystems.definitions.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor

/**
 * The domain class that contain all of the properties required for a definition.
 * 
 * @author chris
 *
 */
@EqualsAndHashCode
@TupleConstructor
class Definition {
    String id, name, definition, description
    Long definitionCategoryId
}