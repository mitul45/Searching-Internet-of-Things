#include "Push.h"
#include <Timer.h>
 
module PushC
{
   uses interface Boot;
   uses interface Timer<TMilli> as Timer0;
   uses interface Packet;
   uses interface AMPacket;
   uses interface AMSend;
   uses interface SplitControl as AMControl;
   uses interface Leds;
}
 
implementation
{
   bool busy = FALSE,flag = TRUE;
   message_t pkt;
 
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
    	PushMsg* pushpkt = (PushMsg*)(call Packet.getPayload(&pkt, sizeof(PushMsg)));
    	pushpkt->nodeid = TOS_NODE_ID;
    	if (call AMSend.send(AM_BROADCAST_ADDR, &pkt, sizeof(PushMsg)) == SUCCESS)
    	{
      		busy = TRUE;
        }
        if(flag)
        {
        	call Leds.set(7);
        	flag = !flag;
        }
        else
        {
        	call Leds.set(0);
        	flag = !flag;
        }
     }	   
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
