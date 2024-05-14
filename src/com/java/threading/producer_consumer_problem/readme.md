
## Producer-Consumer Problem

The Producer-Consumer Problem (sometimes called the Bounded-Buffer Problem) is a classic example of a multi-threaded synchronization problem.

The problem describes two threads, the Producer and the Consumer, and they are sharing a common, fixed-size buffer that is used as a queue.

The Producer produces an item, puts that item into the buffer, and keeps repeating this process.
On the other hand, the Consumer is consuming the item from the shared buffer, one item at a time.