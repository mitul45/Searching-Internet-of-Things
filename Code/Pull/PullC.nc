#include "Pull.h"
#include <Timer.h>
 
module PullC
{
   uses interface Boot;
   uses interface Packet;
   uses interface AMPacket;
   uses interface AMSend;
   uses interface SplitControl as AMControl;
   uses interface Receive;
   uses interface Leds;
}
 
implementation
{
   bool busy = FALSE;
   message_t pkt;
   uint16_t source;
   PullMsg* rcvdpkt;
   PullMsg* reply; 
   event void Boot.booted()
   {
     call AMControl.start();							// start the radio
   }
 
   event void AMControl.startDone(error_t err)
   {
     if (err != SUCCESS)
       call AMControl.start();							// else retry
   }

   // called whenever this node gets some packet.
   event message_t* Receive.receive(message_t* msg, void* payload, uint8_t len)	// msg -> contains whole packet which contains header, payload and else.
   										// while payload points to payload that is our structure stored in that
   										// packet. And len defines length of this structure which will be compared
   										// with the size of our structure.
   {
     source = call AMPacket.source(msg);				// call on msg and not on payload as it contains header which has all this info.
     
     if (source>0 && source <11)			// source ids 1-10 indicates mediators in our system.
     {
        
        rcvdpkt = (PullMsg*)payload;					// to access data by structure members.
	call Leds.led2Toggle();
	if((rcvdpkt->requiredid == TOS_NODE_ID) && (rcvdpkt->flag == 0))
	{
	     	//call Leds.led2Toggle();
		reply = (PullMsg*)(call Packet.getPayload(&pkt, sizeof(PullMsg)));	// we have to create another packet space as using 'msg' can cause problem like it might be overwritten before actually send happens as Receive will store data in that same buffer. So create a new space which has to be transmitted	
		reply->requiredid = TOS_NODE_ID;
		reply->mediatorid = source;
		reply->flag = 1;
		if(!busy)
		{
			if (call AMSend.send(source, &pkt, sizeof(PullMsg)) == SUCCESS)
    			{
      				busy = TRUE;
       				call Leds.set(7);
        		}
        	}
	}
     }
     
     return msg;
   }

   event void AMControl.stopDone(error_t err) 
   {}
 
   event void AMSend.sendDone(message_t* msg, error_t error)
   {
     if (&pkt == msg) {
       busy = FALSE;								// packet sent, so set flag to false
     }
   }
 }
