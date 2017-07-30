//
//  priorityq.cpp
//  StackA
//
//  Created by zheka on 09.04.17.
//  Copyright © 2017 dev-blogs. All rights reserved.
//

#include "priorityq.h"

template <class T> PriorityQ<T>::PriorityQ(int size)
{
    this->size = size;
    a = new T[size];
    front = -1;
    rear = 0;
    nItems = 0;
}

template <class T> void PriorityQ<T>::insert(T value)
{
    if (isFull())
    {
        cout << "Error inserting. Array is full" << endl;
        return;
    }
    int i = ++front;
    while (i > 0 && a[i - 1] < value)
    {
        a[i] = a[i - 1];
        i--;
    }
    a[i] = value;
}

template <class T> T PriorityQ<T>::remove()
{
    if (isEmpty())
    {
        cout << "Error getting. Array is empty" << endl;
        return -1;
    }
    return a[front--];
}

template <class T> T PriorityQ<T>::peek()
{
    return a[front];
}

template <class T> bool PriorityQ<T>::isFull()
{
    return front == size - 1;
}

template <class T> bool PriorityQ<T>::isEmpty()
{
    return front == -1;
}

template <class T> int PriorityQ<T>::getSize()
{
    return size;
}

template <class T> PriorityQ<T>::~PriorityQ()
{
    delete [] a;
}

template class PriorityQ<int>;
