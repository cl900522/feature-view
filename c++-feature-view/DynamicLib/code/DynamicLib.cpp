/*
 * DynamicLib.cpp
 *
 *  Created on: 2017年2月12日
 *      Author: chenmx
 */

#include "DynamicLib.h"

extern "C" {
    int plus(int a, int b) {
        return a + b;
    }

    int minus(int a, int b) {
        return a - b;
    }
}
