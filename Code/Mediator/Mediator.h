#ifndef MEDIATOR_H
#define MEDIATOR_H
 
enum {
  AM_AGGREGATIONMSG = 6,
  AM_PUSHMSG = 8,
  AM_PULLMSG = 7,
  TIMER_PERIOD_MILLI = 3001
};

typedef nx_struct AggregationMsg
{
  nx_uint16_t nodeid;
  nx_uint16_t size;
  nx_uint16_t nodes[10];
} AggregationMsg;
 
typedef nx_struct PushMsg
{
  nx_uint16_t nodeid;								// pushing its own node id.
}PushMsg;


typedef nx_struct PullMsg
{
  nx_uint16_t requiredid;					// node id to be serached
  nx_uint16_t mediatorid;					// node id of under which this node is found.
  nx_uint16_t flag;						// 0 - for pull request, 1- for pull reply.
} PullMsg;

#endif