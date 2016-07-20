//============================================================================
// Name        : test1.cpp
// Author      : 
// Version     :
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
// page 184
//
// Стек
// Это значит, что когда вы­полнение программы входит в блок кода,
// его переменные последовательно добавля­ются к стеку в памяти
// и затем освобождаются в обратном порядке, когда выполнение покидает данный блок
//============================================================================

#include <iostream>
#include <cstring>

using namespace std;

char * getname();

int main1()
{
	char * name;
	name = getname();
	cout << name << " at " << (int *) name << endl;
	delete [] name;
	name = getname();
	cout << name << " at " << (int *) name << endl;
	delete [] name;
	return 0;
}

char * getname()
{
	char temp[80];
	cout << "Enter last name: ";
	cin >> temp;
	char * ps = new char[strlen(temp) + 1];
	strcpy(ps, temp);
	return ps;
}
