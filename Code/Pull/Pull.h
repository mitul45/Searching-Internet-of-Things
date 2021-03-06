#ifndef PULL_H
#define PULL_H
 
enum {
  AM_PULLMSG = 7,
};

typedef nx_struct PullMsg
{
  nx_uint16_t requiredid;					// node id to be serached
  nx_uint16_t mediatorid;					// node id of under which this node is found.
  nx_uint16_t flag;						// 0 - for pull request, 1- for pull reply.
} PullMsg;

#endif
