
#ifndef MODEL_PERSON_H_
#define MODEL_PERSON_H_

class Person {
public:
	Person();
	virtual ~Person();

	bool init();
	bool close();

public:
	char name = 'a';
	int age = 0;
};

#endif /* MODEL_PERSON_H_ */