//
// Created by 54543 on 2019/6/1.
//

#ifndef GRAPHDB_IDOBJECT_H
#define GRAPHDB_IDOBJECT_H

#include <string>

using std::string;

class IdObject {
private:
    string uuid;

public:
    const string &getUuid() const;

    void setUuid(const string &uuid);
};


#endif //GRAPHDB_IDOBJECT_H
