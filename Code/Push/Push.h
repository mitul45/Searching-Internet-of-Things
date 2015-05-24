#ifndef PUSH_H
#define PUSH_H
 
enum {
  AM_PUSHMSG = 8,								// AM type which will be same for sender and receiver.
  TIMER_PERIOD_MILLI = 3000							// broadcast data every at every 3 seconds
};

typedef nx_struct PushMsg
{
  nx_uint16_t nodeid;								// pushing its own node id.
}PushMsg;
 
#endif
