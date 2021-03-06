/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.tool.schema.spi;


import org.hibernate.boot.Metadata;
import org.hibernate.boot.model.relational.Exportable;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;

/**
 * Defines a contract for exporting of database objects (tables, sequences, etc) for use in SQL {@code CREATE} and
 * {@code DROP} scripts.
 * <p/>
 * This is an ORM-centric contract
 *
 * @author Steve Ebersole
 */
public interface Exporter<T extends Exportable> {
	String[] NO_COMMANDS = new String[0];

	/**
	 * Get the commands needed for creation.
	 *
	 * @return The commands needed for creation scripting.
	 * @deprecated Will be removed in favor of the variant accepting {@link SqlStringGenerationContext}
	 * @see #getSqlCreateStrings(Exportable, Metadata, SqlStringGenerationContext)
	 */
	@Deprecated
	default String[] getSqlCreateStrings(T exportable, Metadata metadata) {
		throw new IllegalStateException("getSqlCreateStrings() was not implemented!");
	}

	/**
	 * Get the commands needed for creation.
	 *
	 * @return The commands needed for creation scripting.
	 */
	default String[] getSqlCreateStrings(T exportable, Metadata metadata, SqlStringGenerationContext context) {
		return getSqlCreateStrings( exportable, metadata );
	}

	/**
	 * Get the commands needed for dropping.
	 *
	 * @return The commands needed for drop scripting.
	 * @deprecated Will be removed in favor of the variant accepting {@link SqlStringGenerationContext}
	 * @see #getSqlDropStrings(Exportable, Metadata, SqlStringGenerationContext)
	 */
	@Deprecated
	default String[] getSqlDropStrings(T exportable, Metadata metadata) {
		throw new IllegalStateException("getSqlDropStrings() was not implemented!");
	}

	/**
	 * Get the commands needed for dropping.
	 *
	 * @return The commands needed for drop scripting.
	 */
	default String[] getSqlDropStrings(T exportable, Metadata metadata, SqlStringGenerationContext context) {
		return getSqlDropStrings( exportable, metadata );
	}
}
