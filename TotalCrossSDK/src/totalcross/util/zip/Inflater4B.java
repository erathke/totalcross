/*********************************************************************************
 *  TotalCross Software Development Kit                                          *
 *  Copyright (C) 2000-2011 SuperWaba Ltda.                                      *
 *  All Rights Reserved                                                          *
 *                                                                               *
 *  This library and virtual machine is distributed in the hope that it will     *
 *  be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of    *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.                         *
 *                                                                               *
 *  This file is covered by the GNU LESSER GENERAL PUBLIC LICENSE VERSION 3.0    *
 *  A copy of this license is located in file license.txt at the root of this    *
 *  SDK or can be downloaded here:                                               *
 *  http://www.gnu.org/licenses/lgpl-3.0.txt                                     *
 *                                                                               *
 *********************************************************************************/

// $Id: Inflater4B.java,v 1.4 2011-01-04 13:19:09 guich Exp $

/* Inflater.java - Decompress a data stream
   Copyright (C) 1999, 2000, 2001, 2003  Free Software Foundation, Inc.

This file is part of GNU Classpath.

GNU Classpath is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.
 
GNU Classpath is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with GNU Classpath; see the file COPYING.  If not, write to the
Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
02111-1307 USA.

Linking this library statically or dynamically with other modules is
making a combined work based on this library.  Thus, the terms and
conditions of the GNU General Public License cover the whole
combination.

As a special exception, the copyright holders of this library give you
permission to link this library with independent modules to produce an
executable, regardless of the license terms of these independent
modules, and to copy and distribute the resulting executable under
terms of your choice, provided that you also meet, for each linked
independent module, the terms and conditions of the license of that
module.  An independent module is a module which is not derived from
or based on this library.  If you modify this library, you may extend
this exception to your version of the library, but you are not
obligated to do so.  If you do not wish to do so, delete this
exception statement from your version. */

package totalcross.util.zip;

import totalcross.sys.Convert;

/* Written using on-line Java Platform 1.2 API Specification
 * and JCL book.
 * Believed complete and correct.
 */

/**
 * Inflater is used to decompress data that has been compressed according to the "deflate" standard described in
 * rfc1950.
 * 
 * The usage is as following. First you have to set some input with <code>setInput()</code>, then inflate() it. If
 * inflate doesn't inflate any bytes there may be three reasons:
 * <ul>
 * <li>needsInput() returns true because the input buffer is empty. You have to provide more input with
 * <code>setInput()</code>. NOTE: needsInput() also returns true when, the stream is finished.</li>
 * <li>needsDictionary() returns true, you have to provide a preset dictionary with <code>setDictionary()</code>.</li>
 * <li>finished() returns true, the inflater has finished.</li>
 * </ul>
 * Once the first output byte is produced, a dictionary will not be needed at a later stage.
 * 
 * Changes for TotalCross:<br>
 * Integer.toHexString replaced by Convert.toString(value, 16).<br>
 * DataFormatException replaced by ZipException.<br>
 * 
 * @author John Leuner, Jochen Hoenicke
 * @author Tom Tromey
 * @date May 17, 1999
 * @since JDK 1.1
 */
public class Inflater4B
{
   /* Copy lengths for literal codes 257..285 */
   private static final int CPLENS[] =
         {
         3, 4, 5, 6, 7, 8, 9, 10, 11, 13, 15, 17, 19, 23, 27, 31,
         35, 43, 51, 59, 67, 83, 99, 115, 131, 163, 195, 227, 258
         };

   /* Extra bits for literal codes 257..285 */
   private static final int CPLEXT[] =
         {
         0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2,
         3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5, 5, 0
         };

   /* Copy offsets for distance codes 0..29 */
   private static final int CPDIST[] = {
         1, 2, 3, 4, 5, 7, 9, 13, 17, 25, 33, 49, 65, 97, 129, 193,
         257, 385, 513, 769, 1025, 1537, 2049, 3073, 4097, 6145,
         8193, 12289, 16385, 24577
         };

   /* Extra bits for distance codes */
   private static final int CPDEXT[] = {
         0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6,
         7, 7, 8, 8, 9, 9, 10, 10, 11, 11,
         12, 12, 13, 13
         };

   /* This are the state in which the inflater can be.  */
   private static final int DECODE_HEADER = 0;
   private static final int DECODE_DICT = 1;
   private static final int DECODE_BLOCKS = 2;
   private static final int DECODE_STORED_LEN1 = 3;
   private static final int DECODE_STORED_LEN2 = 4;
   private static final int DECODE_STORED = 5;
   private static final int DECODE_DYN_HEADER = 6;
   private static final int DECODE_HUFFMAN = 7;
   private static final int DECODE_HUFFMAN_LENBITS = 8;
   private static final int DECODE_HUFFMAN_DIST = 9;
   private static final int DECODE_HUFFMAN_DISTBITS = 10;
   private static final int DECODE_CHKSUM = 11;
   private static final int FINISHED = 12;

   /** This variable contains the current state. */
   private int mode;

   /**
    * The adler checksum of the dictionary or of the decompressed stream, as it is written in the header resp. footer of
    * the compressed stream. <br>
    * 
    * Only valid if mode is DECODE_DICT or DECODE_CHKSUM.
    */
   private int readAdler;
   /**
    * The number of bits needed to complete the current state. This is valid, if mode is DECODE_DICT, DECODE_CHKSUM,
    * DECODE_HUFFMAN_LENBITS or DECODE_HUFFMAN_DISTBITS.
    */
   private int neededBits;
   private int repLength, repDist;
   private int uncomprLen;
   /**
    * True, if the last block flag was set in the last block of the inflated stream. This means that the stream ends
    * after the current block.
    */
   private boolean isLastBlock;

   /**
    * The total number of inflated bytes.
    */
   private int totalOut;
   /**
    * The total number of bytes set with setInput(). This is not the value returned by getTotalIn(), since this also
    * includes the unprocessed input.
    */
   private int totalIn;
   /**
    * This variable stores the nowrap flag that was given to the constructor. True means, that the inflated stream
    * doesn't contain a header nor the checksum in the footer.
    */
   private boolean nowrap;

   private StreamManipulator4B input;
   private OutputWindow4B outputWindow;
   private InflaterDynHeader4B dynHeader;
   private InflaterHuffmanTree4B litlenTree, distTree;
   private Adler32 adler;

   /**
    * Creates a new inflater.
    */
   public Inflater4B()
   {
      this(false);
   }

   /**
    * Creates a new inflater.
    * 
    * @param nowrap
    *           true if no header and checksum field appears in the stream. This is used for GZIPed input. For
    *           compatibility with Sun JDK you should provide one byte of input more than needed in this case.
    */
   public Inflater4B(boolean nowrap)
   {
      this.nowrap = nowrap;
      this.adler = new Adler32();
      input = new StreamManipulator4B();
      outputWindow = new OutputWindow4B();
      mode = nowrap ? DECODE_BLOCKS : DECODE_HEADER;
   }

   /**
    * Finalizes this object.
    */
   protected void finalize()
   {
      /* Exists only for compatibility */
   }

   /**
    * Frees all objects allocated by the inflater. There's no reason to call this, since you can just rely on garbage
    * collection (even for the Sun implementation). Exists only for compatibility with Sun's JDK, where the compressor
    * allocates native memory. If you call any method (even reset) afterwards the behaviour is <i>undefined</i>.
    * 
    * @deprecated Just clear all references to inflater instead.
    */
   public void end()
   {
      outputWindow = null;
      input = null;
      dynHeader = null;
      litlenTree = null;
      distTree = null;
      adler = null;
   }

   /**
    * Returns true, if the inflater has finished. This means, that no input is needed and no output can be produced.
    */
   public boolean finished()
   {
      return mode == FINISHED && outputWindow.getAvailable() == 0;
   }

   /**
    * Gets the adler checksum. This is either the checksum of all uncompressed bytes returned by inflate(), or if
    * needsDictionary() returns true (and thus no output was yet produced) this is the adler checksum of the expected
    * dictionary.
    * 
    * @returns the adler checksum.
    */
   public int getAdler()
   {
      return needsDictionary() ? readAdler : (int) adler.getValue();
   }

   /**
    * Gets the number of unprocessed input. Useful, if the end of the stream is reached and you want to further process
    * the bytes after the deflate stream.
    * 
    * @return the number of bytes of the input which were not processed.
    */
   public int getRemaining()
   {
      return input.getAvailableBytes();
   }

   /**
    * Gets the total number of processed compressed input bytes.
    * 
    * @return the total number of bytes of processed input bytes.
    */
   public int getTotalIn()
   {
      return totalIn - getRemaining();
   }

   /**
    * Gets the total number of output bytes returned by inflate().
    * 
    * @return the total number of output bytes.
    */
   public int getTotalOut()
   {
      return totalOut;
   }

   /**
    * Inflates the compressed stream to the output buffer. If this returns 0, you should check, whether
    * needsDictionary(), needsInput() or finished() returns true, to determine why no further output is produced.
    * 
    * @param buf
    *           the output buffer.
    * @return the number of bytes written to the buffer, 0 if no further output can be produced.
    * @exception ZipException
    *               if deflated stream is invalid.
    * @exception IllegalArgumentException
    *               if buf has length 0.
    */
   public int inflate(byte[] buf) throws ZipException
   {
      return inflate(buf, 0, buf.length);
   }

   /**
    * Inflates the compressed stream to the output buffer. If this returns 0, you should check, whether
    * needsDictionary(), needsInput() or finished() returns true, to determine why no further output is produced.
    * 
    * @param buf
    *           the output buffer.
    * @param off
    *           the offset into buffer where the output should start.
    * @param len
    *           the maximum length of the output.
    * @return the number of bytes written to the buffer, 0 if no further output can be produced.
    * @exception ZipException
    *               if deflated stream is invalid.
    * @exception IndexOutOfBoundsException
    *               if the off and/or len are wrong.
    */
   public int inflate(byte[] buf, int off, int len) throws ZipException
   {
      /* Special case: len may be zero */
      if (len == 0)
         return 0;
      /* Check for correct buff, off, len triple */
      if (0 > off || off > off + len || off + len > buf.length)
         throw new ArrayIndexOutOfBoundsException();
      int count = 0;
      int more;
      do
      {
         if (mode != DECODE_CHKSUM)
         {
            /* Don't give away any output, if we are waiting for the
             * checksum in the input stream.
             *
             * With this trick we have always:
             *   needsInput() and not finished() 
             *   implies more output can be produced.  
             */
            more = outputWindow.copyOutput(buf, off, len);
            adler.update(buf, off, more);
            off += more;
            count += more;
            totalOut += more;
            len -= more;
            if (len == 0)
               return count;
         }
      } while (decode() || (outputWindow.getAvailable() > 0
            && mode != DECODE_CHKSUM));
      return count;
   }

   /**
    * Returns true, if a preset dictionary is needed to inflate the input.
    */
   public boolean needsDictionary()
   {
      return mode == DECODE_DICT && neededBits == 0;
   }

   /**
    * Returns true, if the input buffer is empty. You should then call setInput(). <br>
    * 
    * <em>NOTE</em>: This method also returns true when the stream is finished.
    */
   public boolean needsInput()
   {
      return input.needsInput();
   }

   /**
    * Resets the inflater so that a new stream can be decompressed. All pending input and output will be discarded.
    */
   public void reset()
   {
      mode = nowrap ? DECODE_BLOCKS : DECODE_HEADER;
      totalIn = totalOut = 0;
      input.reset();
      outputWindow.reset();
      dynHeader = null;
      litlenTree = null;
      distTree = null;
      isLastBlock = false;
      adler.reset();
   }

   /**
    * Sets the preset dictionary. This should only be called, if needsDictionary() returns true and it should set the
    * same dictionary, that was used for deflating. The getAdler() function returns the checksum of the dictionary
    * needed.
    * 
    * @param buffer
    *           the dictionary.
    * @exception IllegalStateException
    *               if no dictionary is needed.
    * @exception IllegalArgumentException
    *               if the dictionary checksum is wrong.
    */
   public void setDictionary(byte[] buffer)
   {
      setDictionary(buffer, 0, buffer.length);
   }

   /**
    * Sets the preset dictionary. This should only be called, if needsDictionary() returns true and it should set the
    * same dictionary, that was used for deflating. The getAdler() function returns the checksum of the dictionary
    * needed.
    * 
    * @param buffer
    *           the dictionary.
    * @param off
    *           the offset into buffer where the dictionary starts.
    * @param len
    *           the length of the dictionary.
    * @exception IllegalStateException
    *               if no dictionary is needed.
    * @exception IllegalArgumentException
    *               if the dictionary checksum is wrong.
    * @exception IndexOutOfBoundsException
    *               if the off and/or len are wrong.
    */
   public void setDictionary(byte[] buffer, int off, int len)
   {
      if (!needsDictionary())
         throw new IllegalStateException();

      adler.update(buffer, off, len);
      if ((int) adler.getValue() != readAdler)
         throw new IllegalArgumentException("Wrong adler checksum");
      adler.reset();
      outputWindow.copyDict(buffer, off, len);
      mode = DECODE_BLOCKS;
   }

   /**
    * Sets the input. This should only be called, if needsInput() returns true.
    * 
    * @param buf
    *           the input.
    * @exception IllegalStateException
    *               if no input is needed.
    */
   public void setInput(byte[] buf)
   {
      setInput(buf, 0, buf.length);
   }

   /**
    * Sets the input. This should only be called, if needsInput() returns true.
    * 
    * @param buf
    *           the input.
    * @param off
    *           the offset into buffer where the input starts.
    * @param len
    *           the length of the input.
    * @exception IllegalStateException
    *               if no input is needed.
    * @exception IndexOutOfBoundsException
    *               if the off and/or len are wrong.
    */
   public void setInput(byte[] buf, int off, int len)
   {
      input.setInput(buf, off, len);
      totalIn += len;
   }

   /**
    * Decodes the deflate header.
    * 
    * @return false if more input is needed.
    * @exception ZipException
    *               if header is invalid.
    */
   private boolean decodeHeader() throws ZipException
   {
      int header = input.peekBits(16);
      if (header < 0)
         return false;
      input.dropBits(16);

      /* The header is written in "wrong" byte order */
      header = ((header << 8) | (header >> 8)) & 0xffff;
      if (header % 31 != 0)
         throw new ZipException("Header checksum illegal");

      if ((header & 0x0f00) != (Deflater4B.DEFLATED << 8))
         throw new ZipException("Compression Method unknown");

      /* Maximum size of the backwards window in bits. 
       * We currently ignore this, but we could use it to make the
       * inflater window more space efficient. On the other hand the
       * full window (15 bits) is needed most times, anyway.
       int max_wbits = ((header & 0x7000) >> 12) + 8;
       */

      if ((header & 0x0020) == 0) // Dictionary flag?
      {
         mode = DECODE_BLOCKS;
      }
      else
      {
         mode = DECODE_DICT;
         neededBits = 32;
      }
      return true;
   }

   /**
    * Decodes the dictionary checksum after the deflate header.
    * 
    * @return false if more input is needed.
    */
   private boolean decodeDict()
   {
      while (neededBits > 0)
      {
         int dictByte = input.peekBits(8);
         if (dictByte < 0)
            return false;
         input.dropBits(8);
         readAdler = (readAdler << 8) | dictByte;
         neededBits -= 8;
      }
      return false;
   }

   /**
    * Decodes the huffman encoded symbols in the input stream.
    * 
    * @return false if more input is needed, true if output window is full or the current block ends.
    * @exception ZipException
    *               if deflated stream is invalid.
    */
   private boolean decodeHuffman() throws ZipException
   {
      int free = outputWindow.getFreeSpace();
      while (free >= 258)
      {
         int symbol;
         switch (mode)
         {
            case DECODE_HUFFMAN:
               /* This is the inner loop so it is optimized a bit */
               while (((symbol = litlenTree.getSymbol(input)) & ~0xff) == 0)
               {
                  outputWindow.write(symbol);
                  if (--free < 258)
                     return true;
               }
               if (symbol < 257)
               {
                  if (symbol < 0)
                     return false;
                  else
                  {
                     /* symbol == 256: end of block */
                     distTree = null;
                     litlenTree = null;
                     mode = DECODE_BLOCKS;
                     return true;
                  }
               }

               try
               {
                  repLength = CPLENS[symbol - 257];
                  neededBits = CPLEXT[symbol - 257];
               }
               catch (ArrayIndexOutOfBoundsException ex)
               {
                  throw new ZipException("Illegal rep length code");
               }
               /* fall through */
            case DECODE_HUFFMAN_LENBITS:
               if (neededBits > 0)
               {
                  mode = DECODE_HUFFMAN_LENBITS;
                  int i = input.peekBits(neededBits);
                  if (i < 0)
                     return false;
                  input.dropBits(neededBits);
                  repLength += i;
               }
               mode = DECODE_HUFFMAN_DIST;
               /* fall through */
            case DECODE_HUFFMAN_DIST:
               symbol = distTree.getSymbol(input);
               if (symbol < 0)
                  return false;
               try
               {
                  repDist = CPDIST[symbol];
                  neededBits = CPDEXT[symbol];
               }
               catch (ArrayIndexOutOfBoundsException ex)
               {
                  throw new ZipException("Illegal rep dist code");
               }
               /* fall through */
            case DECODE_HUFFMAN_DISTBITS:
               if (neededBits > 0)
               {
                  mode = DECODE_HUFFMAN_DISTBITS;
                  int i = input.peekBits(neededBits);
                  if (i < 0)
                     return false;
                  input.dropBits(neededBits);
                  repDist += i;
               }
               outputWindow.repeat(repLength, repDist);
               free -= repLength;
               mode = DECODE_HUFFMAN;
            break;
            default:
               throw new IllegalStateException();
         }
      }
      return true;
   }

   /**
    * Decodes the adler checksum after the deflate stream.
    * 
    * @return false if more input is needed.
    * @exception ZipException
    *               if checksum doesn't match.
    */
   private boolean decodeChksum() throws ZipException
   {
      while (neededBits > 0)
      {
         int chkByte = input.peekBits(8);
         if (chkByte < 0)
            return false;
         input.dropBits(8);
         readAdler = (readAdler << 8) | chkByte;
         neededBits -= 8;
      }
      if ((int) adler.getValue() != readAdler)
         throw new ZipException("Adler chksum doesn't match: "
               + Convert.toString((int) adler.getValue(), 16)
               + " vs. " + Convert.toString(readAdler, 16));
      mode = FINISHED;
      return false;
   }

   /**
    * Decodes the deflated stream.
    * 
    * @return false if more input is needed, or if finished.
    * @exception ZipException
    *               if deflated stream is invalid.
    */
   private boolean decode() throws ZipException
   {
      switch (mode)
      {
         case DECODE_HEADER:
            return decodeHeader();
         case DECODE_DICT:
            return decodeDict();
         case DECODE_CHKSUM:
            return decodeChksum();

         case DECODE_BLOCKS:
            if (isLastBlock)
            {
               if (nowrap)
               {
                  mode = FINISHED;
                  return false;
               }
               else
               {
                  input.skipToByteBoundary();
                  neededBits = 32;
                  mode = DECODE_CHKSUM;
                  return true;
               }
            }

            int type = input.peekBits(3);
            if (type < 0)
               return false;
            input.dropBits(3);

            if ((type & 1) != 0)
               isLastBlock = true;
            switch (type >> 1)
            {
               case DeflaterConstants4B.STORED_BLOCK:
                  input.skipToByteBoundary();
                  mode = DECODE_STORED_LEN1;
               break;
               case DeflaterConstants4B.STATIC_TREES:
                  litlenTree = InflaterHuffmanTree4B.defLitLenTree;
                  distTree = InflaterHuffmanTree4B.defDistTree;
                  mode = DECODE_HUFFMAN;
               break;
               case DeflaterConstants4B.DYN_TREES:
                  dynHeader = new InflaterDynHeader4B();
                  mode = DECODE_DYN_HEADER;
               break;
               default:
                  throw new ZipException("Unknown block type " + type);
            }
            return true;

         case DECODE_STORED_LEN1:
         {
            if ((uncomprLen = input.peekBits(16)) < 0)
               return false;
            input.dropBits(16);
            mode = DECODE_STORED_LEN2;
         }
            /* fall through */
         case DECODE_STORED_LEN2:
         {
            int nlen = input.peekBits(16);
            if (nlen < 0)
               return false;
            input.dropBits(16);
            if (nlen != (uncomprLen ^ 0xffff))
               throw new ZipException("broken uncompressed block");
            mode = DECODE_STORED;
         }
            /* fall through */
         case DECODE_STORED:
         {
            int more = outputWindow.copyStored(input, uncomprLen);
            uncomprLen -= more;
            if (uncomprLen == 0)
            {
               mode = DECODE_BLOCKS;
               return true;
            }
            return !input.needsInput();
         }

         case DECODE_DYN_HEADER:
            if (!dynHeader.decode(input))
               return false;
            litlenTree = dynHeader.buildLitLenTree();
            distTree = dynHeader.buildDistTree();
            mode = DECODE_HUFFMAN;
            /* fall through */
         case DECODE_HUFFMAN:
         case DECODE_HUFFMAN_LENBITS:
         case DECODE_HUFFMAN_DIST:
         case DECODE_HUFFMAN_DISTBITS:
            return decodeHuffman();
         case FINISHED:
            return false;
         default:
            throw new IllegalStateException();
      }
   }
}
