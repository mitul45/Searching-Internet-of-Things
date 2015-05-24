#include <Timer.h>
#include "Mediator.h"
 
module MediatorC
{
   uses interface Boot;
   uses interface Timer<TMilli> as Timer0;
   uses interface Packet;
   uses interface AMPacket;
   uses interface AMSend as aggregationSender;
   uses interface AMSend as pullSender;
   uses interface SplitControl as AMControl;
   uses interface Receive as pushReceiver;
   uses interface Receive as pullReceiver;
   uses interface Receive as aggregationReceiver;
   uses interface Leds;
}
 
implementation
{
   bool busy = FALSE;
   message_t pkt,ppkt,apkt;
   uint16_t counter = 0,i = 0,source,j = 0;
   uint16_t array[10] = {0};
   PullMsg* rcvdmsg;
   
   event void Boot.booted()
   {
     call AMControl.start();							// start the radio
   }
 
   event void AMControl.startDone(error_t err)
   {
     if (err == SUCCESS)
     {
       call Timer0.startPeriodic(TIMER_PERIOD_MILLI);				// if radio turned on successfully, start timer for leds.
     }
     else
     {
       call AMControl.start();							// else retry
     }
   }

   event void Timer0.fired()
   {
     if (!busy)
     {
    	AggregationMsg* mpkt = (AggregationMsg*)(call Packet.getPayload(&pkt, sizeof(AggregationMsg)));
    	mpkt->nodeid = TOS_NODE_ID;
    	mpkt->size = counter;
    	i = 0;
    	while (i < counter)
    	{					// prepare the packet and send it to basestation, for using data aggregation algorithm we have to broadcast this one.
    		mpkt->nodes[i] = array[i];
    		i++;
    	}
    	counter = 0;								// reset counter.
    	if (call aggregationSender.send(AM_BROADCAST_ADDR, &pkt, sizeof(AggregationMsg)) == SUCCESS)
    	{
      		busy = TRUE;
        }
     }	   
   }

   event void AMControl.stopDone(error_t err) 
   {}
 
   event void aggregationSender.sendDone(message_t* msg, error_t error)
   {
     if (error == SUCCESS) {							// packet sent successfully
       busy = FALSE;								// packet sent, so set flag to false
     }
   }

   event void pullSender.sendDone(message_t* msg, error_t error)
   {
     if (error == SUCCESS) {							// packet sent successfully
       busy = FALSE;								// packet sent, so set flag to false
     }
   }
   
   event message_t* pushReceiver.receive(message_t* msg, void* payload, uint8_t len)	// make an array of nodes which falls under the range of this mediator and pass it to base station at every second or so.
   {
   	if(counter<10)
   	{
        	PushMsg* tagpkt = (PushMsg*)payload;
		array[counter++] = tagpkt->nodeid;					// add nodeids to array.
	}
	return msg; 
   }
   event message_t* pullReceiver.receive(message_t* msg, void* payload, uint8_t len)	// make an array of nodes which falls under the range of this mediator and pass it to base station at every second or so.
   {
        source = call AMPacket.source(msg);
     	if(source>10)
		call Leds.led2Toggle();

     	rcvdmsg = (PullMsg*)payload;
     	if(((rcvdmsg -> flag) && (TOS_NODE_ID < source))||((!rcvdmsg->flag)&&(TOS_NODE_ID > source)))
     //if     (it is a reply AND faraway node wants to pass it to BS OR reply by pull tag) OR (it is user request AND it is passing from BS) just broadcast it.	
     	{
     		PullMsg* pmsg = (PullMsg*)(call Packet.getPayload(&ppkt, sizeof(PullMsg)));
     		pmsg->requiredid = rcvdmsg->requiredid;							// copy payload
     		pmsg->flag = rcvdmsg->flag;
     		pmsg->mediatorid = rcvdmsg->mediatorid;
     		if(!busy)							// if radio not busy.
     		{
     			if(call pullSender.send(AM_BROADCAST_ADDR, &ppkt, sizeof(PullMsg)) == SUCCESS)	// forwrd it.
     			{
     				busy = TRUE;
     				//call Leds.set(3);
     			}
     		}
     	}
	return msg;
   }

   event message_t* aggregationReceiver.receive(message_t* msg, void* payload, uint8_t len)	// make an array of nodes which falls under the range of this mediator and pass it to base station at every second or so.
   {
     source = call AMPacket.source(msg);
     
     if(TOS_NODE_ID < source)					// as node ids keeps increasing going farther away from BS so we will forward this packet.
     {
     	AggregationMsg* amsg = (AggregationMsg*)(call Packet.getPayload(&apkt, sizeof(AggregationMsg)));
 	AggregationMsg* rmsg = (AggregationMsg*)payload;						// get data to be forwarded
     	amsg->nodeid = rmsg->nodeid;											// build new packet
     	amsg->size = rmsg->size;
     	j = 0;
     	while (j < rmsg->size)
    	{					// prepare the packet and send it to basestation, for using data aggregation algorithm we have to broadcast this one.
    		amsg->nodes[j] = rmsg->nodes[j];
    		j++;
    	}
     	if(!busy)											// check if radio transiver is busy or not transmitting somthing else.
     	{
     		if (call aggregationSender.send(AM_BROADCAST_ADDR, &apkt, sizeof(AggregationMsg)) == SUCCESS)		// transmit it!
    		{
      			busy = TRUE;
        	}
     	}
     }
     return msg;
   }
 }
