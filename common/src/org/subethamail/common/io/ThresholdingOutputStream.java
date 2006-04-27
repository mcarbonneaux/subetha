/*
 * $Id: Converter.java 169 2006-04-24 08:01:03Z lhoriman $
 * $URL: http://subetha.tigris.org/svn/subetha/trunk/common/src/org/subethamail/common/Converter.java $
 */
package org.subethamail.common.io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This is an OutputStream wrapper which takes notice when a
 * threshold (number of bytes) is about to be written.  This can
 * be used to limit output data, swap writers, etc.
 *  
 * @author Jeff Schnitzer
 */
abstract public class ThresholdingOutputStream extends OutputStream
{
	/** */
	protected OutputStream output;
	
	/** When to trigger */
	int threshold;
	
	/** Number of bytes written so far */
	int written = 0;
	
	/**
	 */
	public ThresholdingOutputStream(OutputStream base, int thresholdBytes)
	{
		this.output = base;
		this.threshold = thresholdBytes;
	}

	/* (non-Javadoc)
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException
	{
		this.output.close();
	}


	/* (non-Javadoc)
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush() throws IOException
	{
		this.output.flush();
	}


	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte[] b, int off, int len) throws IOException
	{
		this.checkThreshold(len);
		
		this.output.write(b, off, len);
		
		this.written += len;
	}


	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(byte[])
	 */
	@Override
	public void write(byte[] b) throws IOException
	{
		this.checkThreshold(b.length);
		
		this.output.write(b);
		
		this.written += b.length;
	}


	/* (non-Javadoc)
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException
	{
		this.checkThreshold(1);
		
		this.output.write(b);
		
		this.written++;
	}

	/**
	 * Checks whether reading count bytes would cross the limit.
	 */
	protected void checkThreshold(int count) throws IOException
	{
		int predicted = this.written + count; 
		if (predicted > this.threshold)
			this.thresholdReached(this.written, predicted);
	}
	
	/**
	 * @return the current threshold value.
	 */
	public int getThreshold()
	{
		return this.threshold;
	}
	
	/**
	 * Called when the threshold is about to be exceeded.  This isn't
	 * exact; it's called whenever a write would occur that would
	 * cross the amount.
	 * 
	 * @param current is the current number of bytes that have been written
	 * @param predicted is the total number after the write completes
	 */
	abstract protected void thresholdReached(int current, int predicted) throws IOException;
}