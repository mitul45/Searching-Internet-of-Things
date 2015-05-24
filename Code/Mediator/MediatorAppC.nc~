#include <Timer.h>
#include "Mediator.h"
 
configuration MediatorAppC
{

}

implementation
{
   components MainC;
   components MediatorC as App;
   components new TimerMilliC() as Timer0;
   components ActiveMessageC;
   components new AMSenderC(AM_AGGREGATIONMSG) as aggregationSender;
   components new AMReceiverC(AM_AGGREGATIONMSG) as aggregationReceiver;
   components new AMSenderC(AM_PULLMSG) as pullSender;
   components new AMReceiverC(AM_PULLMSG) as pullReceiver;
   components new AMReceiverC(AM_PUSHMSG) as pushReceiver;
   components LedsC;

   App.pushReceiver   -> pushReceiver;
   App.pullReceiver   -> pullReceiver;
   App.aggregationReceiver -> aggregationReceiver;
   App.pullSender -> pullSender;
   App.aggregationSender -> aggregationSender;
   App.Boot      -> MainC;
   App.Timer0    -> Timer0;
   App.Packet    -> pullSender;
   App.AMPacket  -> pullSender;
   App.AMControl -> ActiveMessageC;
   App.Leds      -> LedsC;
}
