//
//  priorityq.h
//  StackA
//
//  Created by zheka on 09.04.17.
//  Copyright © 2017 dev-blogs. All rights reserved.
//
//  We can't use template of inheritence
//  This issue is described here http://stackoverflow.com/questions/14534316/use-of-undeclared-identifier-with-templates-and-inheritance-followup
//

#ifndef priorityq_h
#define priorityq_h

#include "queue.h"
#include <iostream>

using namespace std;

template <class T>
class PriorityQ : public Queue<T>
{
private:
    T * a;
    int front;
    int rear;
    int size;
    int nItems;
public:
    PriorityQ(int);
    void insert(T);
    T remove();
    T peek();
    bool isFull();
    bool isEmpty();
    int getSize();
    ~PriorityQ();
};

#endif /* priorityq_h */
