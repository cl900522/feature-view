#include "Person.h"
#include <stdio.h>

Person::Person() {
}

Person::~Person() {
}

bool Person::init() {
    name = 'd';
    return true;
}

bool Person::close() {
    return true;
}
