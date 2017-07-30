//
//  intopost.cpp
//  StackA
//
//  Created by zheka on 16.04.17.
//  Copyright © 2017 dev-blogs. All rights reserved.
//

#include "intopost.h"
#include <stdio.h>
#include <iostream>

using namespace std;

InToPost::InToPost(char * line) : stack(10)
{
    expression = line;
    len = strlen(expression);
    output = new char[len + 1];
    index = 0;
}

void InToPost::doTrans()
{
    char ch;
    for (int i = 0; i < len; i++)
    {
        ch = expression[i];
        switch (ch)
        {
            case '+' :
            case '-' :
            case '*' :
            case '/' : handleOperator(ch); break;
            case '(' : stack.push(ch); break;
            case ')' : handleBrackets(); break;
            default : output[index++] = ch;
        }
    }
    while (!stack.isEmpty())
    {
        output[index++] = stack.pop();
    }
}

void InToPost::handleOperator(char opThis)
{
    if (stack.isEmpty())
    {
        stack.push(opThis);
        return;
    }
    int priorityOpThis = 0;
    switch (opThis)
    {
        case '+' :
        case '-' : priorityOpThis = 1; break;
        case '*' :
        case '/' : priorityOpThis = 2; break;
    }
    
    char opTop = stack.pop();
    int priorityOpTop = 0;
    switch (opTop)
    {
        case '(' : break;
        case '+' :
        case '-' : priorityOpTop = 1; break;
        case '*' :
        case '/' : priorityOpTop = 2; break;
    }
    if (priorityOpThis <= priorityOpTop)
    {
        output[index++] = opTop;
    }
    else
    {
        stack.push(opTop);
    }
    stack.push(opThis);

}

void InToPost::handleBrackets()
{
    char ch;
    while (!stack.isEmpty())
    {
        ch = stack.pop();
        if (ch != '(')
        {
            output[index++] = ch;
        }
        else
        {
            return;
        }
    }
}

void InToPost::display()
{
    for (int i = 0; i < strlen(output); i++)
    {
        cout << output[i];
    }
    cout << endl;
}

InToPost::~InToPost()
{
    delete [] expression;
    delete [] output;
}
