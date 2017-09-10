/*
 * ShareLibTest.cpp
 *
 *  Created on: 2017年2月11日
 *      Author: chenmx
 */

#include <iostream>
#include "SharedLib.h"
using namespace std;

void runSharedLib() {
    SharedLib lib;
    lib.speak("Hello Sharead fun");
}

int main() {
    runSharedLib();
}

