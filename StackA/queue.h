//
//  queue.h
//  StackA
//
//  Created by zheka on 08.04.17.
//  Copyright © 2017 dev-blogs. All rights reserved.
//

#ifndef queue_h
#define queue_h

template <class T>
class Queue
{
    virtual void insert(T) = 0;
    virtual T remove() = 0;
    virtual T peek() = 0;
    virtual bool isFull() = 0;
    virtual bool isEmpty() = 0;
};

#endif /* queue_h */
