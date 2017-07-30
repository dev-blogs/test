//
//  queuex.cpp
//  StackA
//
//  Created by zheka on 08.04.17.
//  Copyright © 2017 dev-blogs. All rights reserved.
//

#include "queuex.h"

template <class T> QueueX<T>::QueueX(int size)
{
    this->size = size;
    a = new T[size];
    front = 0;
    rear = -1;
    nItems = 0;
}

template <class T> void QueueX<T>::insert(T value)
{
    if (isFull())
    {
        cout << "Error inserting. Array is full" << endl;
        return;
    }
    if (rear == getSize() - 1)
    {
        rear = -1;
    }
    ++nItems;
    a[++rear] = value;
}

template <class T> T QueueX<T>::remove()
{
    if (isEmpty())
    {
        cout << "Error getting. Array is empty" << endl;
        return -1;
    }
    T result = a[front++];
    if (front == getSize())
    {
        front = 0;
    }
    --nItems;
    return result;
}

template <class T> T QueueX<T>::peek()
{
    return a[rear];
}

template <class T> bool QueueX<T>::isFull()
{
    return nItems == getSize();
}

template <class T> bool QueueX<T>::isEmpty()
{
    return nItems == 0;
}

template <class T> int QueueX<T>::getSize()
{
    return size;
}

template <class T> QueueX<T>::~QueueX()
{
    delete [] a;
}

template class QueueX<int>;
