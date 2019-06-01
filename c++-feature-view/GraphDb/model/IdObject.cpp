//
// Created by 54543 on 2019/6/1.
//

#include "IdObject.h"

const string &IdObject::getUuid() const {
    return uuid;
}

void IdObject::setUuid(const string &uuid) {
    IdObject::uuid = uuid;
}
