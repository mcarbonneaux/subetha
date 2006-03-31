/*
 * $Id: Transmute.java 105 2006-02-27 10:06:27Z jeff $
 * $URL: https://svn.infohazard.org/blorn/trunk/core/src/com/blorn/core/util/Transmute.java $
 */

package org.subethamail.core.plugin.i.helper;

import javax.annotation.EJB;

import org.subethamail.core.plugin.i.Filter;
import org.subethamail.core.plugin.i.FilterRegistry;

/**
 * Base implementation of a filter that registers itself upon deployment.
 * Extend this class to automatically have your filter register itself. 
 * 
 * @author Jeff Schnitzer
 */
abstract public class AbstractFilter implements Filter, Lifecycle
{
	/**
	 * This will automatically be injected by JBoss.
	 */
	@EJB FilterRegistry registry;

	/**
	 * @see Lifecycle#start()
	 */
	public void start() throws Exception
	{
		this.registry.register(this);
	}
	
	/**
	 * @see Lifecycle#stop()
	 */
	public void stop()
	{
		this.registry.deregister(this);
	}
}