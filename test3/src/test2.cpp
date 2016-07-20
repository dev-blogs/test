/*
 * test2.cpp
 *
 *  Created on: 21 июля 2016
 *      Author: zheka
 */

#include <iostream>

struct antarctica_years_end
{
	int year;
};

int main()
{
	antarctica_years_end s01, s02, s03;
	s01.year = 1998;
	antarctica_years_end * pa = &s02;
	pa->year = 1999;
	const antarctica_years_end * arp[3] = { &s01, pa, &s03 };

	std::cout << "address of arp[0] is " << arp[0] << std::endl;
	std::cout << "address of arp[1] is " << arp[1] << std::endl;
	std::cout << "address of arp is " << arp << std::endl; // адрес первого элемента массива
	std::cout << "address of (arp + 1) is " << (arp + 1) << std::endl; // перескакиваем на второй элемент
	std::cout << "address of &arp is " << &arp << std::endl; // адресс всего массива
	std::cout << "address of (&arp + 1) is " << (&arp + 1) << std::endl; // перескакиваем через весь массив

	std::cout << "Size of whole array is " << sizeof arp << std::endl;
	std::cout << "Size of one element of array is " << sizeof arp[0] << std::endl;

	std::cout << arp[1]->year << std::endl;

	const antarctica_years_end ** ppa = arp;
	auto ppb = arp;

	std::cout << (*ppa)->year << std::endl;
	std::cout << (*(*ppa)).year << std::endl;

	std::cout << (*(ppb + 1))->year << std::endl;
	std::cout << (*(*(ppb + 1))).year << std::endl;
	return 0;
}
