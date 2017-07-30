//
//  queuex.h
//  StackA
//
//  Created by zheka on 08.04.17.
//  Copyright © 2017 dev-blogs. All rights reserved.
//

#ifndef queuex_h
#define queuex_h

#include "queue.h"
#include <iostream>

using namespace std;

template <class T>
class QueueX : public Queue<T>
{
private:
    T * a;
    int front;
    int rear;
    int size;
    int nItems;
public:
    QueueX(int);
    void insert(T);
    T remove();
    T peek();
    bool isFull();
    bool isEmpty();
    int getSize();
    ~QueueX();
};

#endif /* queuex_h */
