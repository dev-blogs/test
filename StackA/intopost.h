//
//  intopost.h
//  StackA
//
//  Created by zheka on 16.04.17.
//  Copyright © 2017 dev-blogs. All rights reserved.
//

#ifndef intopost_h
#define intopost_h

#include "stackx.h"

class InToPost
{
private:
    char * expression;
    char * output;
    size_t len;
    StackX<char> stack;
    int index;
private:
    void handleOperator(char);
    void handleBrackets();
public:
    InToPost(char *);
    void doTrans();
    void display();
    ~InToPost();
};

#endif /* intopost_h */
