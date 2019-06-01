//
// Created by 54543 on 2019/6/1.
//

#include "Property.h"

#include <ctime>

using namespace std;


const string &Property::getName() const {
    return name;
}

void Property::setName(const string &name) {
    Property::name = name;
}

const string &Property::getValue() const {
    return value;
}

void Property::setValue(const string &value) {
    Property::value = value;
}

string Property::toString() {
    string t = "{\"uuid\":" + getUuid() + ",\"name\":" + Property::name + ",\"value\"," + Property::value + "}";
    return t;
}

Property::Property(const string &name, const string &value) : name(name), value(value) {
    time_t now = time(0);
    setUuid(ctime(&now));
}

Property::~Property() {

}
