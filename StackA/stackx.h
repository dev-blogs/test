//
//  stackx.h
//  StackA
//
//  Created by zheka on 01.04.17.
//  Copyright © 2017 dev-blogs. All rights reserved.
//

#ifndef stackx_h
#define stackx_h

#include <iostream>
#include "stack.h"

using namespace std;

template <class T>
class StackX : public Stack<T>
{
private:
    T * a;
    int size;
    int index = -1;
public:
    StackX(int);
    void push(T);
    T pop();
    T peek();
    bool isEmpty();
    bool isFull();
    ~StackX();
};

#endif /* stackx_h */
