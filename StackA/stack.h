//
//  stack.h
//  StackA
//
//  Created by zheka on 01.04.17.
//  Copyright © 2017 dev-blogs. All rights reserved.
//

#ifndef stack_h
#define stack_h

template <class T>
class Stack
{
public:
    virtual void push(T) = 0;
    virtual T pop() = 0;
    virtual T peek() = 0;
    virtual bool isEmpty() = 0;
    virtual bool isFull() = 0;
};

#endif /* stack_h */
