//
//  main.cpp
//  StackA
//
//  Created by zheka on 01.04.17.
//  Copyright © 2017 dev-blogs. All rights reserved.
//

#include <iostream>
#include "stackx.h"
#include "queuex.h"
#include "priorityq.h"
#include <stdio.h>
#include <string>
#include "intopost.h"

using namespace std;

char * doRev(char * line);
int check(char * line);

int main()
{
    char ex[] = "2*(3-4+5)";
    size_t len = strlen(ex);
    char * expression = new char[len + 1];
    strcpy(expression, ex);
    InToPost intopost(expression);
    
    intopost.doTrans();
    
    intopost.display();
    
    return 0;
}

int main11()
{
    PriorityQ<int> queue(5);
    
    queue.insert(320);
    queue.insert(109);
    queue.insert(43);
    queue.insert(841);
    queue.insert(632);
    
    queue.insert(500);
    
    cout << queue.remove() << endl;
    cout << queue.remove() << endl;
    cout << queue.remove() << endl;
    cout << queue.remove() << endl;
    cout << queue.remove() << endl;
    cout << queue.remove() << endl;
    
    return 0;
}

int main3()
{
    QueueX<int> queue(5);
    
    queue.insert(1);
    queue.insert(2);
    queue.insert(3);
    queue.insert(4);
    queue.insert(5);
    queue.insert(6);
    
    queue.remove();
    queue.remove();
    
    queue.insert(6);
    queue.insert(7);
    queue.insert(8);
    //queue.insert(9);
    
    while (!queue.isEmpty())
    {
        cout << queue.remove() << endl;
    }
    
    return 0;
}

int main2()
{
    //char str[] = "a{b(c[d]e)f}";
    char str[] = "a{b(c]d}e";
    
    //line = doRev(str);
    //cout << line << endl;
    
    return check(str);
}

int check(char * input)
{
    int i = 0;
    int len = static_cast<int>(strlen(input));
    StackX<char> stack(len + 1);
    while (input[i] != '\0')
    {
        char ch = input[i];
        if (ch == '[' || ch == '(' || ch == '{')
        {
            stack.push(ch);
        }
        else if (ch == ']' || ch == ')' || ch == '}')
        {
            char chx = stack.pop();
            if ((ch == ']' && chx != '[') || (ch == ')' && chx != '(') || (ch == '}' && chx != '{'))
            {
                cout << "Error. Symbol \"" << ch << "\" at " << i << endl;
            }
        }
        i++;
    }
    return 0;
}

char * doRev(char * line) {
    size_t size = strlen(line);
    
    char * res = new char[size + 1];
    
    StackX<char> stack(10);
    
    for (int i = 0; i < size; i++)
    {
        stack.push(line[i]);
    }
    
    int i = 0;
    while (!stack.isEmpty())
    {
        *(res + i++) = stack.pop();
    }
    
    return res;
}

/*int main1()
{
    StackX<long> stack(10);
    
    if (stack.isEmpty()) cout << "Empty" << endl;
    if (stack.isFull()) cout << "Full" << endl;
    
    for (int i = 1; i < 20; i++)
    {
        stack.push(i);
    }
    
    if (stack.isEmpty()) cout << "Empty" << endl;
    if (stack.isFull()) cout << "Full" << endl;
    
    cout << endl;
    
    while (!stack.isEmpty())
    {
        cout << stack.pop() << endl;
    }
    
    cout << endl;
    
    if (stack.isEmpty()) cout << "Empty" << endl;
    if (stack.isFull()) cout << "Full" << endl;
    
    return 0;
}*/
