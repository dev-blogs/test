//
//  stackx.cpp
//  StackA
//
//  Created by zheka on 01.04.17.
//  Copyright © 2017 dev-blogs. All rights reserved.
//

#include "stackx.h"

template<class T> StackX<T>::StackX(int size)
{
    this->size = size;
    a = new T[size];
}

template<class T> void StackX<T>::push(T value)
{
    if (isFull())
    {
        cout << "Can't insert. Stack is full" << endl;
        return;
    }
    a[++index] = value;
}

template<class T> T StackX<T>::pop()
{
    if (isEmpty())
    {
        cout << "Stack is empty" << endl;
        return -1;
    }
    return a[index--];
}

template<class T> T StackX<T>::peek()
{
    return a[index];
}

template<class T> bool StackX<T>::isEmpty()
{
    return index == -1;
}

template<class T> bool StackX<T>::isFull()
{
    return index == size - 1;
}

template<class T> StackX<T>::~StackX()
{
    delete [] a;
}

template class StackX<char>;
