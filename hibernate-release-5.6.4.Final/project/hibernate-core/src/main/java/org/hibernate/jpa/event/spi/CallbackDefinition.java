/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or http://www.gnu.org/licenses/lgpl-2.1.html
 */
package org.hibernate.jpa.event.spi;

import java.io.Serializable;

import org.hibernate.resource.beans.spi.ManagedBeanRegistry;

public interface CallbackDefinition extends Serializable {

	Callback createCallback(ManagedBeanRegistry beanRegistry);

}
