//
// Created by 54543 on 2019/6/1.
//

#ifndef GRAPHDB_PROPERTY_H
#define GRAPHDB_PROPERTY_H

#include <string>
#include "IdObject.h"

using  std::string;

class Property : public IdObject {
public:
    Property();
    Property(const string &name, const string &value);

private:
    string name;
    string value;
public:

    const string &getName() const;

    void setName(const string &name);

    const string &getValue() const;

    void setValue(const string &value);

    string toString();

    virtual ~Property();
};


#endif //GRAPHDB_PROPERTY_H
