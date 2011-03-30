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

// $Id: POP3Folder.java,v 1.7 2011-01-04 13:18:59 guich Exp $

package totalcross.net.mail;

import totalcross.io.IOException;
import totalcross.net.SocketTimeoutException;
import totalcross.sys.Convert;
import totalcross.sys.InvalidNumberException;
import totalcross.sys.Vm;
import totalcross.util.Hashtable;
import totalcross.util.Vector;

/**
 * A POP3 Folder (can only be "INBOX").
 * 
 * @since TotalCross 1.13
 */
public class POP3Folder extends Folder
{
   protected POP3Folder(Store store)
   {
      super(store);
   }

   public void open() throws MessagingException
   {
      if (messageCount != -1) // already open
         return;

      try
      {
         store.connection.writeBytes("STAT "+Convert.CRLF);
         String stat = store.connection.readLine();
         messageCount = Convert.toInt(stat.substring(4, stat.indexOf(' ', 4)));
         messagesByUidl = new Hashtable(messageCount);
         messagesByNumber = new Hashtable(messageCount);
         expungedMessages = new Hashtable(messageCount / 10);
      }
      catch (InvalidNumberException e)
      {
         throw new MessagingException(e.getMessage());
      }
      catch (IOException e)
      {
         throw new MessagingException(e.getMessage());
      }
   }

   public Message getMessage(int msgNumber) throws MessagingException
   {
      if (messageCount == -1) // not open
         return null;

      Message msgRet = (Message) messagesByNumber.get(msgNumber);
      if (msgRet != null)
         return msgRet;

      try
      {
         String reply;
         String uidl;
         store.connection.writeBytes("UIDL " + msgNumber + Convert.CRLF);
         reply = store.connection.readLine();
         if (reply.startsWith("-ERR"))
            return null;

         uidl = reply.substring(reply.lastIndexOf(' ') + 1);
         msgRet = (Message) messagesByUidl.get(uidl);
         if (msgRet == null)
         {
            store.connection.writeBytes("LIST " + msgNumber + Convert.CRLF);
            reply = store.connection.readLine();
            int msgSize = Convert.toInt(reply.substring(reply.lastIndexOf(' ') + 1)) + 3;
            int top = msgSize < 4000 ? msgSize : 0;
            store.connection.writeBytes("TOP " + msgNumber + " " + top + Convert.CRLF);
            reply = store.connection.readLine();

            int bytesRead;
            int totalRead = 0;
            try
            {
               do
               {
                  if (totalRead == HEADER_BUFFER_SIZE)
                  {
                     HEADER_BUFFER_SIZE *= 1.1;
                     byte[] newBuf = new byte[HEADER_BUFFER_SIZE];
                     Vm.arrayCopy(msgHeaderBuffer, 0, newBuf, 0, totalRead);
                     msgHeaderBuffer = newBuf;
                     Vm.debug("INCREASED BUF SIZE: " + HEADER_BUFFER_SIZE);
                  }
                  bytesRead = store.connection.readBytes(msgHeaderBuffer, totalRead, HEADER_BUFFER_SIZE - totalRead);
                  if (bytesRead > 0)
                     totalRead += bytesRead;
               } while (!new String(msgHeaderBuffer, totalRead - 5, 5).equals("\r\n.\r\n"));
            }
            catch (SocketTimeoutException e)
            {
               if (!new String(msgHeaderBuffer, totalRead - 5, 5).equals("\r\n.\r\n"))
                  throw new MessagingException(e.getMessage());
            }
            msgRet = new Message(this, msgNumber, uidl, msgSize, totalRead);
            String s = new String(msgHeaderBuffer, 0, totalRead - 5);

            if (top > 0)
            {
               int msgIndex = s.indexOf("\r\n\r\n");
               msgRet.parseHeader(s.substring(0, msgIndex));
               msgRet.parseContent(s.substring(msgIndex + 4)); // +4 to skip double CRLF
            }
            else
               msgRet.parseHeader(s);
            messagesByUidl.put(uidl, msgRet);
            messagesByNumber.put(msgNumber, msgRet);
         }
      }
      catch (InvalidNumberException e)
      {
         throw new MessagingException(e.getMessage());
      }
      catch (AddressException e)
      {
         throw new MessagingException(e.getMessage());
      }
      catch (IOException e)
      {
         throw new MessagingException(e.getMessage());
      }
      return msgRet;
   }

   public Message getMessage(String uidl) throws MessagingException
   {
      if (messageCount == -1) // not open
         return null;
      return (Message) messagesByUidl.get(uidl);
   }

   public void reset() throws MessagingException
   {
      try
      {
         String reply;
         store.connection.writeBytes("RSET "+Convert.CRLF);
         reply = store.connection.readLine();
         if (reply.startsWith("+OK"))
         {
            Vector keys = expungedMessages.getKeys();
            for (int i = keys.size() - 1; i >= 0; i--)
            {
               Message msg = (Message) expungedMessages.remove(keys.items[i]);
               msg.expunged = false;
               messagesByUidl.put(msg.uidl, msg);
               deletedMessageCount = 0;
            }
         }
      }
      catch (IOException e)
      {
         throw new MessagingException(e.getMessage());
      }
   }

   void deleteMessage(Message message) throws MessagingException
   {
      try
      {
         String reply;
         store.connection.writeBytes("DELE " + message.msgNumber + Convert.CRLF);
         reply = store.connection.readLine();
         if (reply.startsWith("+OK"))
         {
            message.expunged = true;
            messagesByUidl.remove(message.uidl);
            expungedMessages.put(message.uidl, message);
            deletedMessageCount++;
         }
      }
      catch (IOException e)
      {
         throw new MessagingException(e.getMessage());
      }
   }
}
