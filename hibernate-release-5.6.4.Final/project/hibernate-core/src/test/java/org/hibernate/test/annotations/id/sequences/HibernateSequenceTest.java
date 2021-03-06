/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.test.annotations.id.sequences;

import org.junit.Assert;
import org.junit.Test;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.model.relational.SqlStringGenerationContext;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.mapping.Table;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.test.annotations.id.sequences.entities.HibernateSequenceEntity;
import org.hibernate.testing.RequiresDialect;
import org.hibernate.testing.TestForIssue;
import org.hibernate.testing.junit4.BaseCoreFunctionalTestCase;

/**
 * @author Lukasz Antoniak (lukasz dot antoniak at gmail dot com)
 */
@TestForIssue(jiraKey = "HHH-6068")
@RequiresDialect( value = H2Dialect.class)
public class HibernateSequenceTest extends BaseCoreFunctionalTestCase {
	private static final String SCHEMA_NAME = "OTHER_SCHEMA";

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class[] {
				HibernateSequenceEntity.class
		};
	}

	@Override
	protected void configure(Configuration cfg) {
		super.configure( cfg );
		cfg.addResource( "org/hibernate/test/annotations/id/sequences/orm.xml" );
	}

	@Override
	protected String createSecondSchema() {
		return SCHEMA_NAME;
	}

	@Test
	public void testHibernateSequenceSchema() {
		EntityPersister persister = sessionFactory().getEntityPersister( HibernateSequenceEntity.class.getName() );
		IdentifierGenerator generator = persister.getIdentifierGenerator();
		Assert.assertTrue( SequenceStyleGenerator.class.isInstance( generator ) );
		SequenceStyleGenerator seqGenerator = (SequenceStyleGenerator) generator;
		SqlStringGenerationContext sqlStringGenerationContext = sessionFactory().getSqlStringGenerationContext();
		Assert.assertEquals(
				SCHEMA_NAME + "." + SequenceStyleGenerator.DEF_SEQUENCE_NAME,
				sqlStringGenerationContext.format( seqGenerator.getDatabaseStructure().getPhysicalName() )
		);
	}

	@Test
	public void testHibernateSequenceNextVal() {
		Session session = openSession();
		Transaction txn = session.beginTransaction();
		HibernateSequenceEntity entity = new HibernateSequenceEntity();
		entity.setText( "sample text" );
		session.save( entity );
		txn.commit();
		session.close();
		Assert.assertNotNull( entity.getId() );
	}
}
